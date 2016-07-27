package mine.xmz.loghunter.core.support;

import mine.xmz.loghunter.core.exception.IllegalArgumentException;

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
