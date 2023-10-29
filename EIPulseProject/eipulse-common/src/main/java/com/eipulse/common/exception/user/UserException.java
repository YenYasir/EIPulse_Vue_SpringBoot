package com.eipulse.common.exception.user;

import com.eipulse.common.exception.BaseException;

/**
 * 員工資訊異常類
 */
public class UserException extends BaseException {
	private static final long serialVersionUID = 1L;

	public UserException(String code, Object[] args) {
		super("user", code, args, null);
	}
}
