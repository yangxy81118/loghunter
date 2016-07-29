package com.github.yangxy81118.loghunter.core.exception;


/**
 * 参数异常
 * 
 * @author yangxy8
 *
 */
public class IllegalArgumentException extends LogHunterRuntimeException {

	public IllegalArgumentException(String msg) {
		super(msg, ExceptionConstraints.ILLEGAL_ARGUMENT);

	}
}
