package com.github.yangxy81118.loghunter.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.github.yangxy81118.loghunter.distribute.bean.LoggerApplication;

/**
 * 注册来的子应用容器
 * 
 * @author yangxy8
 *
 */
public class LoggerAppContainer {
	
	private static ConcurrentMap<String, LoggerApplication> applicationMap = new ConcurrentHashMap<>();
	
	/**
	 * 注册新应用
	 * 
	 * @param application
	 * @throws IllegalClientAppNameException 
	 */
	public static synchronized void addApplication(LoggerApplication application){
		String appKey = application.getIp()+"_"+application.getName();
//		if(applicationMap.containsKey(appKey)){
//			throw new IllegalClientAppNameException(application.getIp(),application.getName());
//		}
		application.setKey(appKey);
		applicationMap.put(appKey, application);
	}
	
	public static synchronized List<LoggerApplication> pageApplication(int start, int rowCount){
		
		List<LoggerApplication> list = new ArrayList<>();
		Collection<LoggerApplication> values =  applicationMap.values();
		if(values!=null && values.size() > 0){
			for (LoggerApplication loggerApplication : values) {
				list.add(loggerApplication);
			}
		}
		
		return list;
	}
	
	
	/**
	 * 删除
	 * @param appId
	 */
	public static synchronized void removeApplication(Integer appId){
		if(!applicationMap.containsKey(appId)){
			return;
		}
		applicationMap.remove(appId);
	}
	
	
	/**
	 * 获取指定
	 * @param appId
	 * @return
	 */
	public static synchronized LoggerApplication getApplication(String appKey){
		return applicationMap.get(appKey);
	}
	
}
