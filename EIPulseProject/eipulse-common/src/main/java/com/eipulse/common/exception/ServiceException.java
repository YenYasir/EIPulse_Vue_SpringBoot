package com.eipulse.common.exception;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * service異常
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Optional<Integer> code;

	@Getter
	@Setter
	private String msg;

	public ServiceException(int code, String msg) {
		super(msg);
		this.code = Optional.of(code);
		this.msg = msg;
	}

	public ServiceException(String message) {
		super(message);
		this.code = Optional.empty();
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
