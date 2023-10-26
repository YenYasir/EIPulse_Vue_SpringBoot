package com.eipulse.common.utils;

/**
 * 類型轉換類
 * 
 * @author eipulse
 *
 */
public class ConvertUtil {

	/**
	 * 數組轉化成字串，用"[]"分開
	 * 
	 * @param values String[]
	 * @return String
	 */
	public static String toString(String[] values) {
		String value = "";
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				value += "[" + values[i] + "]";
			}
		}
		return value;
	}

	/**
	 * 數字數組轉化成字串，用"[]"分開
	 * 
	 * @param values String[]
	 * @return String
	 */
	public static String toString(long[] values) {
		String value = "";
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				value += "[" + values[i] + "]";
			}
		}
		return value;
	}

	/**
	 * 整數數組轉化成字串，用"[]"分開
	 * 
	 * @param values String[]
	 * @return String
	 */
	public static String toString(int[] values) {
		String value = "";
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				value += "[" + values[i] + "]";
			}
		}
		return value;
	}

	/**
	 * []型字串轉換成數字數組
	 * 
	 * @param parameter String
	 * @return String
	 */
	public static long[] toLongArray(String parameter) {
		long[] value = new long[0];
		if (parameter == null || parameter.length() < 2)
			return value;

		try {
			String[] strArray = parameter.substring(1, parameter.length() - 1).split("\\]\\[");
			if (strArray == null) {
				return value;
			}
			value = new long[strArray.length];
			for (int i = 0; i < strArray.length; i++) {
				try {
					value[i] = Long.parseLong(strArray[i]);
				} catch (NumberFormatException ex1) {
					value[i] = 0;
				}
			}
			return value;
		} catch (Exception ex) {
			return value;
		}
	}

	/**
	 * []型字串轉化成整數數組
	 * 
	 * @param parameter String
	 * @return String
	 */
	public static int[] toIntArray(String parameter) {
		int[] value = new int[0];
		if (parameter == null || parameter.length() < 2)
			return value;

		try {
			String[] strArray = parameter.substring(1, parameter.length() - 1).split("\\]\\[");
			if (strArray == null) {
				return value;
			}
			value = new int[strArray.length];
			for (int i = 0; i < strArray.length; i++) {
				try {
					value[i] = Integer.parseInt(strArray[i]);
				} catch (NumberFormatException ex1) {
					value[i] = 0;
				}
			}
			return value;
		} catch (Exception ex) {
			return value;
		}
	}

	/**
	 * []型字串轉化成字串數組
	 * 
	 * @param parameter String
	 * @return String
	 */
	public static String[] toStringArray(String parameter) {
		String[] value = new String[0];
		if (parameter == null || parameter.length() < 2)
			return value;
		if (parameter.indexOf("[") < 0) {
			String[] ret = { parameter };
			return ret;
		}

		try {
			String[] strArray = parameter.substring(1, parameter.length() - 1).split("\\]\\[");
			return strArray;
		} catch (Exception ex) {
			return value;
		}
	}

	/**
	 * 將字串數據，轉換成數據庫sql或hql的in 函數 如 strArr =["a","b","c"]，轉成 “('a','b','c')”
	 * 
	 * @param values
	 * @return
	 */
	public static String toDbString(String[] values) {
		String text = "(";
		for (int i = 0; i < values.length; i++) {
			if (i == 0)
				text += "'" + values[i] + "'";
			else
				text += ",'" + values[i] + "'";
		}
		text += ")";
		return text;
	}

	/**
	 * 將Long數組數據，轉換成數據庫sql或hql的in 函數 如 longArr =[1,2,3]，轉成 “(1,2,3)”
	 * 
	 * @param values
	 * @return
	 */
	public static String toDbString(Long[] values) {
		String text = "(";
		for (int i = 0; i < values.length; i++) {
			if (i == 0)
				text += values[i];
			else
				text += "," + values[i];
		}
		text += ")";
		return text;
	}

	/**
	 * 文本替換函數，不適合大規模的文本替換，例如長度達到幾個Mb的文本
	 * 
	 * @param source      即將被替換的文本
	 * @param find        查找的字串
	 * @param replacewith 替換的字串
	 * @return 替換過的文本
	 */
	public static String replace(String source, String find, String replacewith) {
		if (source == null || find == null) {
			return source;
		}
		int index = source.indexOf(find);
		if (index == -1) {
			return source;
		}
		int nOldLength = find.length();
		if (nOldLength == 0) {
			return source;
		}
		int indexStart = index + nOldLength;
		StringBuffer strDest = new StringBuffer(0);
		strDest.append(source.substring(0, index) + replacewith);
		while ((index = source.indexOf(find, indexStart)) != -1) {
			strDest.append(source.substring(indexStart, index) + replacewith);
			indexStart = index + nOldLength;
		}
		strDest.append(source.substring(indexStart));
		return strDest.toString();
	}

}
