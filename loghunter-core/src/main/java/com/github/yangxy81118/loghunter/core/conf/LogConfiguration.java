package com.github.yangxy81118.loghunter.core.conf;

import java.io.File;

/**
 * 
 * @author yangxy8
 *
 */
public class LogConfiguration {
	
	private static final LogConfiguration configuration = new LogConfiguration();
	
	public static final LogConfiguration getInstance(){
		return configuration;
	}
 
	private String configFileLocation = "log4j2.xml";
	
	public String getConfigFileLocation() {
		return configFileLocation;
	}

	public void setConfigFileLocation(String configFileLocation) {
		this.configFileLocation = configFileLocation;
	}

	/**
	 * 获取log4j2的配置的绝对路径
	 * @return
	 */
	public String getConfigfileAbsolutelocation() {
		String path = this.getClass().getClassLoader().getResource(FILE_SEPARATOR).getPath();
		return path + configFileLocation;
	}

	/**
	 * 获取配置文件对象
	 * @return
	 */
	public File getConfigfile() {
		return new File(getConfigfileAbsolutelocation());
	}
	
	private final String FILE_SEPARATOR = System.getProperty("file.separator");
}
