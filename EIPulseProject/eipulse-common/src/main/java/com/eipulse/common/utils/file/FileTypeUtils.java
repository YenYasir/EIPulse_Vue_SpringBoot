package com.eipulse.common.utils.file;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

/**
 * 檔案類型工具類
 */
public class FileTypeUtils {
	/**
	 * 獲取檔案類型
	 * <p>
	 * 例如: eipulse.txt, 返回: txt
	 * 
	 * @param file 檔案名
	 * @return 後綴（不含".")
	 */
	public static String getFileType(File file) {
		if (null == file) {
			return StringUtils.EMPTY;
		}
		return getFileType(file.getName());
	}

	/**
	 * 獲取檔案類型
	 * <p>
	 * 例如: eipulse.txt, 返回: txt
	 *
	 * @param fileName 檔案名
	 * @return 後綴（不含".")
	 */
	public static String getFileType(String fileName) {
		int separatorIndex = fileName.lastIndexOf(".");
		if (separatorIndex < 0) {
			return "";
		}
		return fileName.substring(separatorIndex + 1).toLowerCase();
	}
}