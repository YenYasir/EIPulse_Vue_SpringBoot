package com.eipulse.common.utils;

import java.util.Collection;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

/**
 * 驗證比較類
 * 
 * @author eipulse 實現物件為空為NULL的判斷
 */
public class ValidateUtil {

	/**
	 * 驗證物件是否為NULL，null返回true,否則為false
	 * 
	 * @param object 物件
	 */
	public static boolean isNull(Object object) {
		return object == null;
	}

	/**
	 * 驗證字串是否為空（包括null和空字串的判斷），為空返回true,否則為false
	 * 
	 * @param text 物件
	 */
	public static boolean isEmpty(String text) {
		return (text == null || text.trim().length() == 0);
	}

	/**
	 * 驗證物件數組是否為空，為空返回true,否則為false
	 * 
	 * @param array 物件
	 */
	public static boolean isEmpty(Object[] array) {
		return ObjectUtils.isEmpty(array);
	}

	/**
	 * 驗證集合是否為空，為空返回true,否則為false
	 * 
	 * @param collection 集合物件
	 */
	public static boolean isEmpty(Collection collection) {
		return CollectionUtils.isEmpty(collection);
	}

	/**
	 * 驗證Map物件是否為空，為空返回true,否則為false
	 * 
	 * @param map map物件
	 */
	public static boolean isEmpty(Map map) {
		return CollectionUtils.isEmpty(map);
	}

}
