package mine.xmz.loghunter.admin.view;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mine.xmz.loghunter.admin.service.LoggerAppContainer;
import mine.xmz.loghunter.core.support.Cats;
import mine.xmz.loghunter.distribute.bean.ActionConstraints;
import mine.xmz.loghunter.distribute.bean.LogConfigAction;
import mine.xmz.loghunter.distribute.bean.LoggerApplication;
import mine.xmz.loghunter.distribute.pipe.netty.NettyPipe;
import mine.xmz.loghunter.distribute.pipe.netty.TransferSychronizeLock;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/app")
public class ApplicationController {

	@RequestMapping(value = "/list")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<LoggerApplication> appList = LoggerAppContainer.pageApplication(0, 100);
		
		JSONArray testArray = new JSONArray();
		for (LoggerApplication loggerApplication : appList) {
			JSONObject obj = new JSONObject();
			obj.put("key", loggerApplication.getKey());
			obj.put("text", loggerApplication.getIp()+":"+loggerApplication.getName());
			obj.put("state", "open");
			testArray.add(obj);
		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(testArray.toJSONString());

		return null;
	}

	@RequestMapping(value = "/info")
	public void getApplicationLogConfigSource(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String appKey)
			throws Exception {

		LoggerApplication application = LoggerAppContainer
				.getApplication(appKey);
		if (application == null) {
			response.getWriter().write("");
		} else {
			response.getWriter().write(Cats.formatXML(application.getConfigSource()));
		}
	}
	
	@RequestMapping(value = "/save")
	@ModelAttribute
	public void pushAppLogConfig(HttpServletRequest request,
			HttpServletResponse response, LogConfigForm logConfigForm){
		
		//准备参数
		LogConfigAction action = new LogConfigAction();
		action.setActionCode(ActionConstraints.ACTION_LOG_CONFIG_EDIT);
		LoggerApplication app = new LoggerApplication();
		app.setConfigSource(Cats.decodeHTML(logConfigForm.getLogConfigSource()));
		app.setKey(logConfigForm.getAppKey());
		action.setLoggerApplication(app);
		
		LoggerApplication application = LoggerAppContainer
				.getApplication(app.getKey());
		String clientAppIp = application.getIp();
		Integer clientAppPort = application.getPort();
		
		//同步等待!
		String lockName = TransferSychronizeLock.LOCK_CONFIG_EDIT+app.getKey();
		TransferSychronizeLock.lock(lockName);
		
		//传输数据
		NettyPipe nettyPipe = NettyPipe.getPIPE();
		nettyPipe.transfer(clientAppIp, clientAppPort, action);
		
		synchronized (TransferSychronizeLock.class) {
			while(TransferSychronizeLock.getLock(lockName)){
				try {
					TransferSychronizeLock.class.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			//结束了
			if(!TransferSychronizeLock.getLock(lockName)){
				refreshContainer(action);
			}
		}
		try {
			response.getWriter().write("200");
		} catch (IOException e) {
		}
		
	}

	private void refreshContainer(LogConfigAction action) {
		
	}
}
