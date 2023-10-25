package com.eipulse.common.core.dao.dialect;

import java.util.List;

/**
 * SQL Script format tools
 *
 */
public class FormatUtil {

	/**
	 * 字符型數據格式化，在參數上增加左右兩個“'”，以對應數據庫腳本語法
	 * 
	 * @param params 字符型數組
	 * @return
	 */
	public static List formatStrForDB(List params) {
		for (int i = 0; i < params.size(); i++) {
			Object o = params.get(i);
			String value;
			if (o instanceof String)
				value = (String) o;
			else if (o instanceof Integer)
				value = String.valueOf(o);
			else {
				try {
					value = String.valueOf(o);
				} catch (Exception e) {
					value = "";
				}
			}
			params.set(i, String.format("'%s'", value));
		}
		return params;
	}

	/**
	 * 字符型數據格式化，在參數上增加左右兩個“'”，以對應數據庫腳本語法
	 * 
	 * @param str 一個字符串
	 * @return
	 */
	public static String formatStrForDB(String str) {
		return String.format("'%s'", str);
	}

	public static boolean isNull(String str) {
		return (str == null) || ("".equals(str));
	}

}
