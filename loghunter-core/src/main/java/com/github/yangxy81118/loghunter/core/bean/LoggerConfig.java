package com.github.yangxy81118.loghunter.core.bean;

import java.io.Serializable;

/**
 * <logger>节点配置
 * 
 * @author yangxy8
 *
 */
public class LoggerConfig implements Serializable {

	private static final long serialVersionUID = -1809368149267176184L;

	private LogLevel level;
	
	private String classType;
	
	private String appenderRef;

	public LogLevel getLevel() {
		return level;
	}

	public void setLevel(LogLevel level) {
		this.level = level;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getAppenderRef() {
		return appenderRef;
	}

	public void setAppenderRef(String appenderRef) {
		this.appenderRef = appenderRef;
	}

	@Override
	public String toString() {
		return "LoggerConfig [level=" + level + ", classType=" + classType
				+ ", appenderRef=" + appenderRef + "]";
	}
	
	
	
	
}
