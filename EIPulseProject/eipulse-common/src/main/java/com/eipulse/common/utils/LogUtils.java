package com.eipulse.common.utils;

/**
 * 處理並記錄日誌檔案
 */
public class LogUtils {
	public static String getBlock(Object msg) {
		if (msg == null) {
			msg = "";
		}
		return "[" + msg.toString() + "]";
	}
}
