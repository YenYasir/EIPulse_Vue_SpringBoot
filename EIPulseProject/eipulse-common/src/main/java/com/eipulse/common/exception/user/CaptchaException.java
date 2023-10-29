package com.eipulse.common.exception.user;

/**
 * 驗證碼錯誤異常類
 */
public class CaptchaException extends UserException {
	private static final long serialVersionUID = 1L;

	public CaptchaException() {
		super("user.jcaptcha.error", null);
	}
}
