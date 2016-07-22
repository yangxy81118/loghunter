package mine.xmz.loghunter.core.bean;

import java.io.Serializable;

/**
 * 应用与loghunter-admin进行行为交互Bean
 * 
 * @author yangxy8
 *
 */
public class LogConfigAction implements Serializable {

	private static final long serialVersionUID = 7535854628954649987L;

	public static final int ACTION_LOG_CONFIG_EDIT = 1000;

	private Integer actionCode;

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

	@Override
	public String toString() {
		return "LogConfigAction [actionCode=" + actionCode
				+ ", loggerApplication=" + loggerApplication + "]";
	}

}
