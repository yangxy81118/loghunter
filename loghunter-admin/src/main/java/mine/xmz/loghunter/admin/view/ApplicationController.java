package mine.xmz.loghunter.admin.view;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mine.xmz.loghunter.admin.push.LogConfigPushDelegator;
import mine.xmz.loghunter.admin.register.LoggerAppContainer;
import mine.xmz.loghunter.core.bean.LoggerApplication;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
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
			obj.put("id", loggerApplication.getId());
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
			HttpServletResponse response, @RequestParam Integer appId)
			throws Exception {

		LoggerApplication application = LoggerAppContainer
				.getApplication(appId);
		if (application == null) {
			response.getWriter().write("");
		} else {
			response.getWriter().write(formatXML(application.getConfigSource()));
		}
	}
	
	@RequestMapping(value = "/save")
	@ModelAttribute
	public void pushAppLogConfig(HttpServletRequest request,
			HttpServletResponse response, LogConfigForm logConfigForm){
		
		//先对远程log进行修改，如果成功了,再修改本地内存中的配置信息
		LogConfigPushDelegator.pushLogConfigSource(logConfigForm.getAppId(),logConfigForm.getLogConfigSource());
		
	}

	private String formatXML(String inputXML) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new StringReader(inputXML));
		String requestXML = null;
		XMLWriter writer = null;
		if (document != null) {
			try {
				StringWriter stringWriter = new StringWriter();
				OutputFormat format = new OutputFormat(" ", true);
				writer = new XMLWriter(stringWriter, format);
				writer.write(document);
				writer.flush();
				requestXML = stringWriter.getBuffer().toString();
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
					}
				}
			}
		}
		return requestXML;
	}
}
