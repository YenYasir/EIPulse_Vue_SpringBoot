package com.eipulse.generator.util;

import java.util.Arrays;

import org.apache.commons.lang3.RegExUtils;

import com.eipulse.common.constant.GenConstants;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.generator.config.GenConfig;
import com.eipulse.generator.domain.GenTable;
import com.eipulse.generator.domain.GenTableColumn;

/**
 * 代碼生成器 工具類
 */
public class GenUtils {
	/**
	 * 初始化表資訊
	 */
	public static void initTable(GenTable genTable, String operName) {
		genTable.setClassName(convertClassName(genTable.getTableName()));
		genTable.setPackageName(GenConfig.getPackageName());
		genTable.setModuleName(getModuleName(GenConfig.getPackageName()));
		genTable.setBusinessName(getBusinessName(genTable.getTableName()));
		genTable.setFunctionName(replaceText(genTable.getTableComment()));
		genTable.setFunctionAuthor(GenConfig.getAuthor());
		genTable.setCreateBy(operName);
	}

	/**
	 * 初始化列屬性欄位
	 */
	public static void initColumnField(GenTableColumn column, GenTable table) {
		String dataType = getDbType(column.getColumnType());
		String columnName = column.getColumnName();
		column.setTableId(table.getTableId());
		column.setCreateBy(table.getCreateBy());
		// 設置java欄位名
		column.setJavaField(StringUtils.toCamelCase(columnName));

		if (arraysContains(GenConstants.COLUMNTYPE_STR, dataType)) {
			column.setJavaType(GenConstants.TYPE_STRING);
			// 字串長度超過500設置為文本域
			Integer columnLength = getColumnLength(column.getColumnType());
			String htmlType = columnLength >= 500 ? GenConstants.HTML_TEXTAREA : GenConstants.HTML_INPUT;
			column.setHtmlType(htmlType);
		} else if (arraysContains(GenConstants.COLUMNTYPE_TIME, dataType)) {
			column.setJavaType(GenConstants.TYPE_DATE);
			column.setHtmlType(GenConstants.HTML_DATETIME);
		} else if (arraysContains(GenConstants.COLUMNTYPE_NUMBER, dataType)) {
			column.setHtmlType(GenConstants.HTML_INPUT);

			// 如果是浮點型 統一用BigDecimal
			String[] str = StringUtils.split(StringUtils.substringBetween(column.getColumnType(), "(", ")"), ",");
			if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0) {
				column.setJavaType(GenConstants.TYPE_BIGDECIMAL);
			}
			// 如果是整形
			else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 10) {
				column.setJavaType(GenConstants.TYPE_INTEGER);
			}
			// 長整形
			else {
				column.setJavaType(GenConstants.TYPE_LONG);
			}
		}

		// 插入欄位（默認所有欄位都需要插入）
		column.setIsInsert(GenConstants.REQUIRE);

		// 編輯欄位
		if (!arraysContains(GenConstants.COLUMNNAME_NOT_EDIT, columnName) && !column.isPk()) {
			column.setIsEdit(GenConstants.REQUIRE);
		}
		// 列表欄位
		if (!arraysContains(GenConstants.COLUMNNAME_NOT_LIST, columnName) && !column.isPk()) {
			column.setIsList(GenConstants.REQUIRE);
		}
		// 查詢欄位
		if (!arraysContains(GenConstants.COLUMNNAME_NOT_QUERY, columnName) && !column.isPk()) {
			column.setIsQuery(GenConstants.REQUIRE);
		}

		// 查詢欄位類型
		if (StringUtils.endsWithIgnoreCase(columnName, "name")) {
			column.setQueryType(GenConstants.QUERY_LIKE);
		}
		// 狀態欄位設置單選框
		if (StringUtils.endsWithIgnoreCase(columnName, "status")) {
			column.setHtmlType(GenConstants.HTML_RADIO);
		}
		// 類型&性別欄位設置下拉框
		else if (StringUtils.endsWithIgnoreCase(columnName, "type")
				|| StringUtils.endsWithIgnoreCase(columnName, "sex")) {
			column.setHtmlType(GenConstants.HTML_SELECT);
		}
		// 檔案欄位設置上傳控制項
		else if (StringUtils.endsWithIgnoreCase(columnName, "image")) {
			column.setHtmlType(GenConstants.HTML_UPLOAD_IMAGE);
		}
		// 內容欄位設置富文本控制項
		else if (StringUtils.endsWithIgnoreCase(columnName, "content")) {
			column.setHtmlType(GenConstants.HTML_EDITOR);
		}
	}

	/**
	 * 校驗數組是否包含指定值
	 *
	 * @param arr         數組
	 * @param targetValue 值
	 * @return 是否包含
	 */
	public static boolean arraysContains(String[] arr, String targetValue) {
		return Arrays.asList(arr).contains(targetValue);
	}

	/**
	 * 獲取模組名
	 *
	 * @param packageName 包名
	 * @return 模組名
	 */
	public static String getModuleName(String packageName) {
		int lastIndex = packageName.lastIndexOf(".");
		int nameLength = packageName.length();
		String moduleName = StringUtils.substring(packageName, lastIndex + 1, nameLength);
		return moduleName;
	}

	/**
	 * 獲取業務名
	 *
	 * @param tableName 表名
	 * @return 業務名
	 */
	public static String getBusinessName(String tableName) {
		int lastIndex = tableName.lastIndexOf("_");
		int nameLength = tableName.length();
		String businessName = StringUtils.substring(tableName, lastIndex + 1, nameLength);
		return businessName;
	}

	/**
	 * 表名轉換成Java類名
	 *
	 * @param tableName 表名稱
	 * @return 類名
	 */
	public static String convertClassName(String tableName) {
		boolean autoRemovePre = GenConfig.getAutoRemovePre();
		String tablePrefix = GenConfig.getTablePrefix();
		if (autoRemovePre && StringUtils.isNotEmpty(tablePrefix)) {
			String[] searchList = StringUtils.split(tablePrefix, ",");
			tableName = replaceFirst(tableName, searchList);
		}
		return StringUtils.convertToCamelCase(tableName);
	}

	/**
	 * 批次替換前綴
	 *
	 * @param replacementm 替換值
	 * @param searchList   替換列表
	 * @return
	 */
	public static String replaceFirst(String replacementm, String[] searchList) {
		String text = replacementm;
		for (String searchString : searchList) {
			if (replacementm.startsWith(searchString)) {
				text = replacementm.replaceFirst(searchString, "");
				break;
			}
		}
		return text;
	}

	/**
	 * 關鍵字替換
	 *
	 * @param text 需要被替換的名字
	 * @return 替換後的名字
	 */
	public static String replaceText(String text) {
		return RegExUtils.replaceAll(text, "(?:表|若依)", "");
	}

	/**
	 * 獲取資料庫類型欄位
	 *
	 * @param columnType 列類型
	 * @return 截取後的列類型
	 */
	public static String getDbType(String columnType) {
		if (StringUtils.indexOf(columnType, "(") > 0) {
			return StringUtils.substringBefore(columnType, "(");
		} else {
			return columnType;
		}
	}

	/**
	 * 獲取欄位長度
	 *
	 * @param columnType 列類型
	 * @return 截取後的列類型
	 */
	public static Integer getColumnLength(String columnType) {
		if (StringUtils.indexOf(columnType, "(") > 0) {
			String length = StringUtils.substringBetween(columnType, "(", ")");
			return Integer.valueOf(length);
		} else {
			return 0;
		}
	}
}
