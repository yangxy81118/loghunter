package com.github.yangxy81118.loghunter.local.monitor;

import java.io.File;

import com.github.yangxy81118.loghunter.core.conf.LogConfiguration;

/**
 * 本地模式，目前采用指定地点的配置文件监控
 * 
 * @author yangxy8
 *
 */
public class LocalConfigMonitor implements Runnable {

	private static final String NEW_CONFIG_SWAP_FILE_SUFFIX = ".swap";

	private String newConfigPath;

	private static final long MONITOR_INTERVAL = 2000l;

	public LocalConfigMonitor(String newConfigPath) {
		this.newConfigPath = newConfigPath;
	}

	@Override
	public void run() {

		// 每2秒进行一次检测
		while (true) {
			
			File configFile = LogConfiguration.getInstance().getConfigfile();
			
			
			try {
				Thread.sleep(MONITOR_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
		// 首先检查log4j的配置
		// 然后找到同级目录下的.swap文件，进行内容替换
		// 然后对log进行reload

	}

}
