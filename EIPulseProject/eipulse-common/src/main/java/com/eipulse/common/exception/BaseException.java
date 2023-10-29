package com.eipulse.common.exception;

import com.eipulse.common.utils.MessageUtils;
import com.eipulse.common.utils.StringUtils;

/**
 * 基礎異常
 */
public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * 所屬模組
	 */
	private String module;

	/**
	 * 錯誤碼
	 */
	private String code;

	/**
	 * 錯誤碼對應的參數
	 */
	private Object[] args;

	/**
	 * 錯誤消息
	 */
	private String defaultMessage;

	public BaseException(String module, String code, Object[] args, String defaultMessage) {
		this.module = module;
		this.code = code;
		this.args = args;
		this.defaultMessage = defaultMessage;
	}

	public BaseException(String module, String code, Object[] args) {
		this(module, code, args, null);
	}

	public BaseException(String module, String defaultMessage) {
		this(module, null, null, defaultMessage);
	}

	public BaseException(String code, Object[] args) {
		this(null, code, args, null);
	}

	public BaseException(String defaultMessage) {
		this(null, null, null, defaultMessage);
	}

	@Override
	public String getMessage() {
		String message = null;
		if (!StringUtils.isEmpty(code)) {
			message = MessageUtils.message(code, args);
		}
		if (message == null) {
			message = defaultMessage;
		}
		return message;
	}

	public String getModule() {
		return module;
	}

	public String getCode() {
		return code;
	}

	public Object[] getArgs() {
		return args;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}
}
