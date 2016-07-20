package mine.xmz.loghunter.admin.register;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import mine.xmz.loghunter.core.bean.LoggerApplication;

/**
 * 注册来的子应用容器
 * 
 * @author yangxy8
 *
 */
public class LoggerAppContainer {
	
	private static ConcurrentMap<Integer, LoggerApplication> applicationMap = new ConcurrentHashMap<>();
	
	/**
	 * 注册新应用
	 * 
	 * @param application
	 */
	public static synchronized void addApplication(LoggerApplication application){
		Integer appId = application.getId();
		if(appId==null){
			appId = applicationMap.size()+1;
			application.setId(appId);
		}
		applicationMap.put(appId, application);
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

}
