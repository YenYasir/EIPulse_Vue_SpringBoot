package com.eipulse.common.exception.user;

/**
 * 驗證碼失效異常類
 * 
 * @author eipulse
 */
public class CaptchaExpireException extends UserException {
	private static final long serialVersionUID = 1L;

	public CaptchaExpireException() {
		super("user.jcaptcha.expire", null);
	}
}
