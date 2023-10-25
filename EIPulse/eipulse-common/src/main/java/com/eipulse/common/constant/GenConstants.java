package com.eipulse.common.constant;

/**
 * 程式碼生成通用常量
 * 
 * @author eipulse
 */
public class GenConstants {
	/** 單表（增刪改查） */
	public static final String TPL_CRUD = "crud";

	/** 樹表（增刪改查） */
	public static final String TPL_TREE = "tree";

	/** 主子表（增刪改查） */
	public static final String TPL_SUB = "sub";

	/** 樹編碼字段 */
	public static final String TREE_CODE = "treeCode";

	/** 樹父編碼字段 */
	public static final String TREE_PARENT_CODE = "treeParentCode";

	/** 樹名稱字段 */
	public static final String TREE_NAME = "treeName";

	/** 上級菜單ID字段 */
	public static final String PARENT_MENU_ID = "parentMenuId";

	/** 上級菜單名稱字段 */
	public static final String PARENT_MENU_NAME = "parentMenuName";

	/** 數據庫字符串類型 */
	public static final String[] COLUMNTYPE_STR = { "char", "varchar", "nvarchar", "varchar2" };

	/** 數據庫文本類型 */
	public static final String[] COLUMNTYPE_TEXT = { "tinytext", "text", "mediumtext", "longtext" };

	/** 數據庫時間類型 */
	public static final String[] COLUMNTYPE_TIME = { "datetime", "time", "date", "timestamp" };

	/** 數據庫數字類型 */
	public static final String[] COLUMNTYPE_NUMBER = { "tinyint", "smallint", "mediumint", "int", "number", "integer",
			"bit", "bigint", "float", "double", "decimal" };

	/** 頁面不需要編輯字段 */
	public static final String[] COLUMNNAME_NOT_EDIT = { "id", "create_by", "create_time", "del_flag" };

	/** 頁面不需要顯示的列表字段 */
	public static final String[] COLUMNNAME_NOT_LIST = { "id", "create_by", "create_time", "del_flag", "update_by",
			"update_time" };

	/** 頁面不需要查詢字段 */
	public static final String[] COLUMNNAME_NOT_QUERY = { "id", "create_by", "create_time", "del_flag", "update_by",
			"update_time", "remark" };

	/** Entity基類字段 */
	public static final String[] BASE_ENTITY = { "createBy", "createTime", "updateBy", "updateTime", "remark" };

	/** Tree基類字段 */
	public static final String[] TREE_ENTITY = { "parentName", "parentId", "orderNum", "ancestors", "children" };

	/** 文本框 */
	public static final String HTML_INPUT = "input";

	/** 文本域 */
	public static final String HTML_TEXTAREA = "textarea";

	/** 下拉框 */
	public static final String HTML_SELECT = "select";

	/** 單選框 */
	public static final String HTML_RADIO = "radio";

	/** 覆選框 */
	public static final String HTML_CHECKBOX = "checkbox";

	/** 日期控件 */
	public static final String HTML_DATETIME = "datetime";

	/** 圖片上傳控件 */
	public static final String HTML_IMAGE_UPLOAD = "imageUpload";

	/** 文件上傳控件 */
	public static final String HTML_FILE_UPLOAD = "fileUpload";

	/** 富文本控件 */
	public static final String HTML_EDITOR = "editor";

	/** 字串類型 */
	public static final String TYPE_STRING = "String";

	/** 整型 */
	public static final String TYPE_INTEGER = "Integer";

	/** 長整型 */
	public static final String TYPE_LONG = "Long";

	/** 浮點型 */
	public static final String TYPE_DOUBLE = "Double";

	/** 高精度計算類型 */
	public static final String TYPE_BIGDECIMAL = "BigDecimal";

	/** 時間類型 */
	public static final String TYPE_DATE = "Date";

	/** 模糊查詢 */
	public static final String QUERY_LIKE = "LIKE";

	/** 需要 */
	public static final String REQUIRE = "1";
}
