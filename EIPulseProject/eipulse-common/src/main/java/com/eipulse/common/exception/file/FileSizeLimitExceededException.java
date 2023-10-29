package com.eipulse.common.exception.file;

/**
 * 檔案名大小限制異常類
 */
public class FileSizeLimitExceededException extends FileException {
	private static final long serialVersionUID = 1L;

	public FileSizeLimitExceededException(long defaultMaxSize) {
		super("upload.exceed.maxSize", new Object[] { defaultMaxSize });
	}
}
