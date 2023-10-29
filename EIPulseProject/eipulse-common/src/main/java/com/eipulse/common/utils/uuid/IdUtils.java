package com.eipulse.common.utils.uuid;

/**
 * ID生成器工具類
 */
public class IdUtils {
	/**
	 * 獲取隨機UUID
	 * 
	 * @return 隨機UUID
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 簡化的UUID，去掉了橫線
	 * 
	 * @return 簡化的UUID，去掉了橫線
	 */
	public static String simpleUUID() {
		return UUID.randomUUID().toString(true);
	}

	/**
	 * 獲取隨機UUID，使用性能更好的ThreadLocalRandom生成UUID
	 * 
	 * @return 隨機UUID
	 */
	public static String fastUUID() {
		return UUID.fastUUID().toString();
	}

	/**
	 * 簡化的UUID，去掉了橫線，使用性能更好的ThreadLocalRandom生成UUID
	 * 
	 * @return 簡化的UUID，去掉了橫線
	 */
	public static String fastSimpleUUID() {
		return UUID.fastUUID().toString(true);
	}
}
