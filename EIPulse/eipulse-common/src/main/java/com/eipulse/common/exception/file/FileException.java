package com.eipulse.common.exception.file;

import com.eipulse.common.exception.base.BaseException;

/**
 * 檔案訊息異常類
 * 
 * @author eipulse
 */
public class FileException extends BaseException {
	private static final long serialVersionUID = 1L;

	public FileException(String code, Object[] args) {
		super("file", code, args, null);
	}

}
