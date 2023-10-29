package com.eipulse.common.exception.file;

/**
 * 檔案名稱超長限制異常類
 */
public class FileNameLengthLimitExceededException extends FileException {
	private static final long serialVersionUID = 1L;

	public FileNameLengthLimitExceededException(int defaultFileNameLength) {
		super("upload.filename.exceed.length", new Object[] { defaultFileNameLength });
	}
}
