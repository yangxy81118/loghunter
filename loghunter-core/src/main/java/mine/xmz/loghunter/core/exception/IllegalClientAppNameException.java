package mine.xmz.loghunter.core.exception;

import mine.xmz.loghunter.core.bean.ActionConstraints;

public class IllegalClientAppNameException extends LogHunterRuntimeException {

	public IllegalClientAppNameException(String appIp, String appName) {
		super(
				"appIp+appName["
						+ appIp
						+ ","
						+ appName
						+ "] does EXIST in Application Container,please change either of them at least",
				ActionConstraints.RESPONSE_APP_EXIST);

	}
}
