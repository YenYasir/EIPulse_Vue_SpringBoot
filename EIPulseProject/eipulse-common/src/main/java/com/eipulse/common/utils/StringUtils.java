package com.eipulse.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.eipulse.common.core.text.StrFormatter;

/**
 * 字串工具類
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	/** 空字串 */
	private static final String NULLSTR = "";

	/** 下劃線 */
	private static final char SEPARATOR = '_';

	/**
	 * 獲取參數不為空值
	 * 
	 * @param value defaultValue 要判斷的value
	 * @return value 返回值
	 */
	public static <T> T nvl(T value, T defaultValue) {
		return value != null ? value : defaultValue;
	}

	/**
	 * * 判斷一個Collection是否為空， 包含List，Set，Queue
	 * 
	 * @param coll 要判斷的Collection
	 * @return true：為空 false：非空
	 */
	public static boolean isEmpty(Collection<?> coll) {
		return isNull(coll) || coll.isEmpty();
	}

	/**
	 * * 判斷一個Collection是否非空，包含List，Set，Queue
	 * 
	 * @param coll 要判斷的Collection
	 * @return true：非空 false：空
	 */
	public static boolean isNotEmpty(Collection<?> coll) {
		return !isEmpty(coll);
	}

	/**
	 * * 判斷一個物件數組是否為空
	 * 
	 * @param objects 要判斷的物件數組
	 ** @return true：為空 false：非空
	 */
	public static boolean isEmpty(Object[] objects) {
		return isNull(objects) || (objects.length == 0);
	}

	/**
	 * * 判斷一個物件數組是否非空
	 * 
	 * @param objects 要判斷的物件數組
	 * @return true：非空 false：空
	 */
	public static boolean isNotEmpty(Object[] objects) {
		return !isEmpty(objects);
	}

	/**
	 * * 判斷一個Map是否為空
	 * 
	 * @param map 要判斷的Map
	 * @return true：為空 false：非空
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return isNull(map) || map.isEmpty();
	}

	/**
	 * * 判斷一個Map是否為空
	 * 
	 * @param map 要判斷的Map
	 * @return true：非空 false：空
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	/**
	 * * 判斷一個字串是否為空串
	 * 
	 * @param str String
	 * @return true：為空 false：非空
	 */
	public static boolean isEmpty(String str) {
		return isNull(str) || NULLSTR.equals(str.trim());
	}

	/**
	 * * 判斷一個字串是否為非空串
	 * 
	 * @param str String
	 * @return true：非空串 false：空串
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * * 判斷一個物件是否為空
	 * 
	 * @param object Object
	 * @return true：為空 false：非空
	 */
	public static boolean isNull(Object object) {
		return object == null;
	}

	/**
	 * * 判斷一個物件是否非空
	 * 
	 * @param object Object
	 * @return true：非空 false：空
	 */
	public static boolean isNotNull(Object object) {
		return !isNull(object);
	}

	/**
	 * * 判斷一個物件是否是數組類型（Java基本型別的數組）
	 * 
	 * @param object 物件
	 * @return true：是數組 false：不是數組
	 */
	public static boolean isArray(Object object) {
		return isNotNull(object) && object.getClass().isArray();
	}

	/**
	 * 去空格
	 */
	public static String trim(String str) {
		return (str == null ? "" : str.trim());
	}

	/**
	 * 截取字串
	 * 
	 * @param str   字串
	 * @param start 開始
	 * @return 結果
	 */
	public static String substring(final String str, int start) {
		if (str == null) {
			return NULLSTR;
		}

		if (start < 0) {
			start = str.length() + start;
		}

		if (start < 0) {
			start = 0;
		}
		if (start > str.length()) {
			return NULLSTR;
		}

		return str.substring(start);
	}

	/**
	 * 截取字串
	 * 
	 * @param str   字串
	 * @param start 開始
	 * @param end   結束
	 * @return 結果
	 */
	public static String substring(final String str, int start, int end) {
		if (str == null) {
			return NULLSTR;
		}

		if (end < 0) {
			end = str.length() + end;
		}
		if (start < 0) {
			start = str.length() + start;
		}

		if (end > str.length()) {
			end = str.length();
		}

		if (start > end) {
			return NULLSTR;
		}

		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	/**
	 * 格式化文本, {} 表示占位符<br>
	 * 此方法只是簡單將占位符 {} 按照順序替換為參數<br>
	 * 如果想輸出 {} 使用 \\轉義 { 即可，如果想輸出 {} 之前的 \ 使用雙轉義符 \\\\ 即可<br>
	 * 例：<br>
	 * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
	 * 轉義{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
	 * 轉義\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
	 * 
	 * @param template 文本模板，被替換的部分用 {} 表示
	 * @param params   參數值
	 * @return 格式化後的文本
	 */
	public static String format(String template, Object... params) {
		if (isEmpty(params) || isEmpty(template)) {
			return template;
		}
		return StrFormatter.format(template, params);
	}

	/**
	 * 字串轉set
	 * 
	 * @param str 字串
	 * @param sep 分隔符號
	 * @return set集合
	 */
	public static final Set<String> str2Set(String str, String sep) {
		return new HashSet<String>(str2List(str, sep, true, false));
	}

	/**
	 * 字串轉list
	 * 
	 * @param str         字串
	 * @param sep         分隔符號
	 * @param filterBlank 過濾純空白
	 * @param trim        去掉首尾空白
	 * @return list集合
	 */
	public static final List<String> str2List(String str, String sep, boolean filterBlank, boolean trim) {
		List<String> list = new ArrayList<String>();
		if (StringUtils.isEmpty(str)) {
			return list;
		}

		// 過濾空白字串
		if (filterBlank && StringUtils.isBlank(str)) {
			return list;
		}
		String[] split = str.split(sep);
		for (String string : split) {
			if (filterBlank && StringUtils.isBlank(string)) {
				continue;
			}
			if (trim) {
				string = string.trim();
			}
			list.add(string);
		}

		return list;
	}

	/**
	 * 下劃線轉駝峰命名
	 */
	public static String toUnderScoreCase(String str) {
		if (str == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		// 前置字元是否大寫
		boolean preCharIsUpperCase = true;
		// 當前字元是否大寫
		boolean curreCharIsUpperCase = true;
		// 下一字元是否大寫
		boolean nexteCharIsUpperCase = true;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (i > 0) {
				preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
			} else {
				preCharIsUpperCase = false;
			}

			curreCharIsUpperCase = Character.isUpperCase(c);

			if (i < (str.length() - 1)) {
				nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
			}

			if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
				sb.append(SEPARATOR);
			} else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
				sb.append(SEPARATOR);
			}
			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}

	/**
	 * 是否包含字串
	 * 
	 * @param str  驗證字串
	 * @param strs 字串組
	 * @return 包含返回true
	 */
	public static boolean inStringIgnoreCase(String str, String... strs) {
		if (str != null && strs != null) {
			for (String s : strs) {
				if (str.equalsIgnoreCase(trim(s))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 將下劃線大寫方式命名的字串轉換為駝峰式。如果轉換前的下劃線大寫方式命名的字串為空，則返回空字串。 例如：HELLO_WORLD->HelloWorld
	 * 
	 * @param name 轉換前的下劃線大寫方式命名的字串
	 * @return 轉換後的駝峰式命名的字串
	 */
	public static String convertToCamelCase(String name) {
		StringBuilder result = new StringBuilder();
		// 快速檢查
		if (name == null || name.isEmpty()) {
			// 沒必要轉換
			return "";
		} else if (!name.contains("_")) {
			// 不含下劃線，僅將首字母大寫
			return name.substring(0, 1).toUpperCase() + name.substring(1);
		}
		// 用下劃線將原始字串分割
		String[] camels = name.split("_");
		for (String camel : camels) {
			// 跳過原始字串中開頭、結尾的下換線或雙重下劃線
			if (camel.isEmpty()) {
				continue;
			}
			// 首字母大寫
			result.append(camel.substring(0, 1).toUpperCase());
			result.append(camel.substring(1).toLowerCase());
		}
		return result.toString();
	}

	/**
	 * 駝峰式命名法 例如：user_name->userName
	 */
	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = s.toLowerCase();
		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public static <T> T cast(Object obj) {
		return (T) obj;
	}
}