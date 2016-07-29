package com.github.yangxy81118.loghunter.core.support;

import com.github.yangxy81118.loghunter.core.exception.IllegalArgumentException;

/**
 * 异常检查工具
 * @author yangxy8
 *
 */
public class ExceptionThrow {

	public static void argumentNull(Object obj,String objName){
		if(obj==null){
			throw new IllegalArgumentException(objName+"cannot be null");
		}
	}
	
}
