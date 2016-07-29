package com.github.yangxy81118.loghunter.distribute.bean;

import java.io.Serializable;

/**
 * 应用与loghunter-admin进行行为交互Bean
 * 
 * @author yangxy8
 *
 */
public class LogConfigAction implements Serializable {

	private static final long serialVersionUID = 7535854628954649987L;

	private Integer actionCode;

	/**
	 * 结果返回码
	 */
	private Integer responseCode;

	private String responseMsg;

	private LoggerApplication loggerApplication;

	public LogConfigAction() {
	}

	public LogConfigAction(Integer actionCode,
			LoggerApplication loggerApplication) {
		this.actionCode = actionCode;
		this.loggerApplication = loggerApplication;
	}

	public Integer getActionCode() {
		return actionCode;
	}

	public void setActionCode(Integer actionCode) {
		this.actionCode = actionCode;
	}

	public LoggerApplication getLoggerApplication() {
		return loggerApplication;
	}

	public void setLoggerApplication(LoggerApplication loggerApplication) {
		this.loggerApplication = loggerApplication;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	@Override
	public String toString() {
		return "LogConfigAction [actionCode=" + actionCode + ", responseCode="
				+ responseCode + ", responseMsg=" + responseMsg
				+ ", loggerApplication=" + loggerApplication + "]";
	}

}
