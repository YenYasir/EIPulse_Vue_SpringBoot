package com.eipulse.common.core.domain.model;

import lombok.Data;

/**
 * 員工登入物件
 */
@Data
public class LoginBody {
	/**
	 * 員工名
	 */
	private String username;

	/**
	 * 員工密碼
	 */
	private String password;

	/**
	 * 驗證碼
	 */
	private String code;

	/**
	 * 唯一標識
	 */
	private String uuid = "";

}
