package mine.xmz.loghunter.core.exception;

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

	private int errorCode;

	public LogHunterRuntimeException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public LogHunterRuntimeException(Throwable throwable, int errorCode) {
		super(throwable);
		this.errorCode = errorCode;
	}

	public LogHunterRuntimeException(String message, Throwable throwable,
			int errorCode) {
		super(message, throwable);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
