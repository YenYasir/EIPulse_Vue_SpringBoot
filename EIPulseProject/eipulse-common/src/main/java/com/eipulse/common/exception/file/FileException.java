package com.eipulse.common.exception.file;

import com.eipulse.common.exception.BaseException;

/**
 * 檔案資訊異常類
 */
public class FileException extends BaseException {
	private static final long serialVersionUID = 1L;

	public FileException(String code, Object[] args) {
		super("file", code, args, null);
	}

}
