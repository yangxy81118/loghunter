package mine.xmz.loghunter.core.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author yangxy8
 *
 */
public class LogConfig implements Serializable {

	private static final long serialVersionUID = 4460410718459452931L;

	private LogLevel rootLevel;

	private List<LoggerConfig> loggers = null;

	public LogLevel getRootLevel() {
		return rootLevel;
	}

	public void setRootLevel(LogLevel rootLevel) {
		this.rootLevel = rootLevel;
	}

	public List<LoggerConfig> getLoggers() {
		return loggers;
	}

	public void setLoggers(List<LoggerConfig> loggers) {
		this.loggers = loggers;
	}

	@Override
	public String toString() {
		return "LogConfig [rootLevel=" + rootLevel + ", loggers=" + loggers
				+ "]";
	}

}
