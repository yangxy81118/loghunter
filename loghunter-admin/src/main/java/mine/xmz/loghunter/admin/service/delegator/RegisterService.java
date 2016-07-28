package mine.xmz.loghunter.admin.service.delegator;

import mine.xmz.loghunter.admin.service.LoggerAppContainer;
import mine.xmz.loghunter.core.support.ExceptionThrow;
import mine.xmz.loghunter.distribute.bean.ActionConstraints;
import mine.xmz.loghunter.distribute.bean.LogConfigAction;
import mine.xmz.loghunter.distribute.bean.LoggerApplication;

/**
 * 客户端应用注册处理类
 * 
 * @author yangxy8
 *
 */
public class RegisterService implements ServerEndPointService {

	@Override
	public LogConfigAction execute(LogConfigAction clientAction) {

		LoggerApplication application = clientAction.getLoggerApplication();
		ExceptionThrow.argumentNull(application, "LoggerApplication");
		LoggerAppContainer.addApplication(application);
		
		clientAction.setResponseCode(ActionConstraints.RESPONSE_OK);
		return clientAction;
	}

}
