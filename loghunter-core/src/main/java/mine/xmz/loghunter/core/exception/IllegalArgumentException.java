package mine.xmz.loghunter.core.exception;

import mine.xmz.loghunter.core.bean.ActionConstraints;

/**
 * 参数异常
 * 
 * @author yangxy8
 *
 */
public class IllegalArgumentException extends LogHunterRuntimeException {

	public IllegalArgumentException(String msg) {
		super(msg, ActionConstraints.RESPONSE_ILLEGAL_ARGUMENT);

	}
}
