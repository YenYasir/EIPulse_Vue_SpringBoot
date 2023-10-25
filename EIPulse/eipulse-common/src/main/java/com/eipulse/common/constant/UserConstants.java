package com.eipulse.common.constant;

/**
 * 員工常量資訊
 * 
 * @author eipulse
 */
public class UserConstants {
	/**
	 * 平台內系統員工的唯一標志
	 */
	public static final String SYS_USER = "SYS_USER";

	/** 正常狀態 */
	public static final String NORMAL = "0";

	/** 異常狀態 */
	public static final String EXCEPTION = "1";

	/** 員工封禁狀態 */
	public static final String USER_DISABLE = "1";

	/** 角色封禁狀態 */
	public static final String ROLE_DISABLE = "1";

	/** 部門正常狀態 */
	public static final String DEPT_NORMAL = "0";

	/** 部門停用狀態 */
	public static final String DEPT_DISABLE = "1";

	/** 字典正常狀態 */
	public static final String DICT_NORMAL = "0";

	/** 是否為系統默認（是） */
	public static final String YES = "Y";

	/** 是否菜單外鏈（是） */
	public static final String YES_FRAME = "0";

	/** 是否菜單外鏈（否） */
	public static final String NO_FRAME = "1";

	/** 菜單類型（目錄） */
	public static final String TYPE_DIR = "M";

	/** 菜單類型（菜單） */
	public static final String TYPE_MENU = "C";

	/** 菜單類型（按鈕） */
	public static final String TYPE_BUTTON = "F";

	/** Layout組件標識 */
	public final static String LAYOUT = "Layout";

	/** ParentView組件標識 */
	public final static String PARENT_VIEW = "ParentView";

	/** InnerLink組件標識 */
	public final static String INNER_LINK = "InnerLink";

	/** 校驗返回結果碼 */
	public final static String UNIQUE = "0";
	public final static String NOT_UNIQUE = "1";

	/**
	 * 員工名長度限制
	 */
	public static final int USERNAME_MIN_LENGTH = 2;
	public static final int USERNAME_MAX_LENGTH = 20;

	/**
	 * 密碼長度限制
	 */
	public static final int PASSWORD_MIN_LENGTH = 5;
	public static final int PASSWORD_MAX_LENGTH = 20;
}
