package com.github.yangxy81118.loghunter.service.delegator;

import com.github.yangxy81118.loghunter.core.support.ExceptionThrow;
import com.github.yangxy81118.loghunter.distribute.bean.ActionConstraints;
import com.github.yangxy81118.loghunter.distribute.bean.LogConfigAction;
import com.github.yangxy81118.loghunter.distribute.bean.LoggerApplication;
import com.github.yangxy81118.loghunter.service.LoggerAppContainer;

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
