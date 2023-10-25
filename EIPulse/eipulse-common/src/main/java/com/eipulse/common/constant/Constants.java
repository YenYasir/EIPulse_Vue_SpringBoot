package com.eipulse.common.constant;

import io.jsonwebtoken.Claims;

/**
 * 通用常量資訊
 * 
 * @author eipulse
 */
public class Constants {
	/**
	 * UTF-8編碼
	 */
	public static final String UTF8 = "UTF-8";

	/**
	 * GBK編碼
	 */
	public static final String GBK = "GBK";

	/**
	 * http請求
	 */
	public static final String HTTP = "http://";

	/**
	 * https請求
	 */
	public static final String HTTPS = "https://";

	/**
	 * 通用成功標識
	 */
	public static final String SUCCESS = "0";

	/**
	 * 通用失敗標識
	 */
	public static final String FAIL = "1";

	/**
	 * 登入成功
	 */
	public static final String LOGIN_SUCCESS = "Success";

	/**
	 * 註銷
	 */
	public static final String LOGOUT = "Logout";

	/**
	 * 註冊
	 */
	public static final String REGISTER = "Register";

	/**
	 * 登入失敗
	 */
	public static final String LOGIN_FAIL = "Error";

	/**
	 * 驗證碼 redis key
	 */
	public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

	/**
	 * 登入用戶 redis key
	 */
	public static final String LOGIN_TOKEN_KEY = "login_tokens:";

	/**
	 * 防重提交 redis key
	 */
	public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

	/**
	 * 限流 redis key
	 */
	public static final String RATE_LIMIT_KEY = "rate_limit:";

	/**
	 * 驗證碼有效期（分鐘）
	 */
	public static final Integer CAPTCHA_EXPIRATION = 2;

	/**
	 * 令牌
	 */
	public static final String TOKEN = "token";

	/**
	 * 令牌前綴
	 */
	public static final String TOKEN_PREFIX = "Bearer ";

	/**
	 * 令牌前綴
	 */
	public static final String LOGIN_USER_KEY = "login_user_key";

	/**
	 * 用戶ID
	 */
	public static final String JWT_USERID = "userid";

	/**
	 * 用戶名稱
	 */
	public static final String JWT_USERNAME = Claims.SUBJECT;

	/**
	 * 用戶頭像
	 */
	public static final String JWT_AVATAR = "avatar";

	/**
	 * 創建時間
	 */
	public static final String JWT_CREATED = "created";

	/**
	 * 用戶權限
	 */
	public static final String JWT_AUTHORITIES = "authorities";

	/**
	 * 參數管理 cache key
	 */
	public static final String SYS_CONFIG_KEY = "sys_config:";

	/**
	 * 字典管理 cache key
	 */
	public static final String SYS_DICT_KEY = "sys_dict:";

	/**
	 * 資源映射路徑 前綴
	 */
	public static final String RESOURCE_PREFIX = "/profile";

	/**
	 * RMI 遠程方法調用
	 */
	public static final String LOOKUP_RMI = "rmi://";

	/**
	 * LDAP 遠程方法調用
	 */
	public static final String LOOKUP_LDAP = "ldap://";
}
