package com.github.yangxy81118.loghunter.core.editor;

import com.github.yangxy81118.loghunter.core.bean.LogConfig;
import com.github.yangxy81118.loghunter.core.bean.LogLevel;

/**
 * 配置文件编辑接口
 * @author yangxy8
 *
 */
public interface ConfigFileEditor {

	/**
	 * 修改某个特定的类的日志级别
	 * 
	 * @param classType
	 * @param level
	 */
	void changeClasslevel(String classType, LogLevel level);
	
	/**
	 * 删除某个特定类的日志规则
	 * @param classType
	 */
	void remove(String classType);
	
	/**
	 * 修改总日志级别
	 * @param level
	 */
	void changeRootLevel(LogLevel level);

	/**
	 * 读取配置
	 * @return
	 */
	LogConfig readConfig();
	
	
}
