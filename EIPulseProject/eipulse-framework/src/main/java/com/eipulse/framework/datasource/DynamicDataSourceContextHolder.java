package com.eipulse.framework.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 數據源切換處理
 */
public class DynamicDataSourceContextHolder {
	public static final Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

	/**
	 * 使用ThreadLocal維護變數，ThreadLocal為每個使用該變數的執行緒提供獨立的變數副本，
	 * 所以每一個執行緒都可以獨立地改變自己的副本，而不會影響其它執行緒所對應的副本。
	 */
	private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

	/**
	 * 設置數據源的變數
	 */
	public static void setDataSourceType(String dsType) {
		log.info("切換到{}數據源", dsType);
		CONTEXT_HOLDER.set(dsType);
	}

	/**
	 * 獲得數據源的變數
	 */
	public static String getDataSourceType() {
		return CONTEXT_HOLDER.get();
	}

	/**
	 * 清空數據源變數
	 */
	public static void clearDataSourceType() {
		CONTEXT_HOLDER.remove();
	}
}
