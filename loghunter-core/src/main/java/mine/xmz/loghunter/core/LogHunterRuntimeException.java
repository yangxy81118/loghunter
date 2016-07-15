package mine.xmz.loghunter.core;

/**
 * 
 * @author yangxy8
 *
 */
public class LogHunterRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5274154112117563732L;

	public LogHunterRuntimeException(String message) {
		super(message);
	}

	
	public LogHunterRuntimeException(Throwable throwable) {
		super(throwable);
	}

	public LogHunterRuntimeException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
