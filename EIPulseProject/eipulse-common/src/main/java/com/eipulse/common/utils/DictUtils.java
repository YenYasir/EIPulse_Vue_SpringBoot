package com.eipulse.common.utils;

import java.util.Collection;
import java.util.List;

import com.eipulse.common.constant.Constants;
import com.eipulse.common.core.domain.entity.SysDictData;
import com.eipulse.common.core.redis.RedisCache;
import com.eipulse.common.utils.spring.SpringUtils;

/**
 * 字典工具類
 */
public class DictUtils {
	/**
	 * 分隔符號
	 */
	public static final String SEPARATOR = ",";

	/**
	 * 設置字典快取
	 * 
	 * @param key       參數鍵
	 * @param dictDatas 字典數據列表
	 */
	public static void setDictCache(String key, List<SysDictData> dictDatas) {
		SpringUtils.getBean(RedisCache.class).setCacheObject(getCacheKey(key), dictDatas);
	}

	/**
	 * 獲取字典快取
	 * 
	 * @param key 參數鍵
	 * @return dictDatas 字典數據列表
	 */
	public static List<SysDictData> getDictCache(String key) {
		Object cacheObj = SpringUtils.getBean(RedisCache.class).getCacheObject(getCacheKey(key));
		if (StringUtils.isNotNull(cacheObj)) {
			List<SysDictData> dictDatas = StringUtils.cast(cacheObj);
			return dictDatas;
		}
		return null;
	}

	/**
	 * 根據字典類型和字典值獲取字典標籤
	 * 
	 * @param dictType  字典類型
	 * @param dictValue 字典值
	 * @return 字典標籤
	 */
	public static String getDictLabel(String dictType, String dictValue) {
		return getDictLabel(dictType, dictValue, SEPARATOR);
	}

	/**
	 * 根據字典類型和字典標籤獲取字典值
	 * 
	 * @param dictType  字典類型
	 * @param dictLabel 字典標籤
	 * @return 字典值
	 */
	public static String getDictValue(String dictType, String dictLabel) {
		return getDictValue(dictType, dictLabel, SEPARATOR);
	}

	/**
	 * 根據字典類型和字典值獲取字典標籤
	 * 
	 * @param dictType  字典類型
	 * @param dictValue 字典值
	 * @param separator 分隔符號
	 * @return 字典標籤
	 */
	public static String getDictLabel(String dictType, String dictValue, String separator) {
		StringBuilder propertyString = new StringBuilder();
		List<SysDictData> datas = getDictCache(dictType);

		if (StringUtils.containsAny(separator, dictValue) && StringUtils.isNotEmpty(datas)) {
			for (SysDictData dict : datas) {
				for (String value : dictValue.split(separator)) {
					if (value.equals(dict.getDictValue())) {
						propertyString.append(dict.getDictLabel() + separator);
						break;
					}
				}
			}
		} else {
			for (SysDictData dict : datas) {
				if (dictValue.equals(dict.getDictValue())) {
					return dict.getDictLabel();
				}
			}
		}
		return StringUtils.stripEnd(propertyString.toString(), separator);
	}

	/**
	 * 根據字典類型和字典標籤獲取字典值
	 * 
	 * @param dictType  字典類型
	 * @param dictLabel 字典標籤
	 * @param separator 分隔符號
	 * @return 字典值
	 */
	public static String getDictValue(String dictType, String dictLabel, String separator) {
		StringBuilder propertyString = new StringBuilder();
		List<SysDictData> datas = getDictCache(dictType);

		if (StringUtils.containsAny(separator, dictLabel) && StringUtils.isNotEmpty(datas)) {
			for (SysDictData dict : datas) {
				for (String label : dictLabel.split(separator)) {
					if (label.equals(dict.getDictLabel())) {
						propertyString.append(dict.getDictValue() + separator);
						break;
					}
				}
			}
		} else {
			for (SysDictData dict : datas) {
				if (dictLabel.equals(dict.getDictLabel())) {
					return dict.getDictValue();
				}
			}
		}
		return StringUtils.stripEnd(propertyString.toString(), separator);
	}

	/**
	 * 清空字典快取
	 */
	public static void clearDictCache() {
		Collection<String> keys = SpringUtils.getBean(RedisCache.class).keys(Constants.SYS_DICT_KEY + "*");
		SpringUtils.getBean(RedisCache.class).deleteObject(keys);
	}

	/**
	 * 設置cache key
	 * 
	 * @param configKey 參數鍵
	 * @return 快取鍵key
	 */
	public static String getCacheKey(String configKey) {
		return Constants.SYS_DICT_KEY + configKey;
	}
}
