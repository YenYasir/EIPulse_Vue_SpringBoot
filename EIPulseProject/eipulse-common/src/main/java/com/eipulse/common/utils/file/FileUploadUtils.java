package com.eipulse.common.utils.file;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.eipulse.common.config.EipulseConfig;
import com.eipulse.common.constant.Constants;
import com.eipulse.common.exception.file.FileNameLengthLimitExceededException;
import com.eipulse.common.exception.file.FileSizeLimitExceededException;
import com.eipulse.common.exception.file.InvalidExtensionException;
import com.eipulse.common.utils.DateUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.uuid.IdUtils;

/**
 * 檔案上傳工具類
 */
public class FileUploadUtils {
	/**
	 * 默認大小 50M
	 */
	public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

	/**
	 * 預設的檔案名最大長度 100
	 */
	public static final int DEFAULT_FILE_NAME_LENGTH = 100;

	/**
	 * 默認上傳的位址
	 */
	private static String defaultBaseDir = EipulseConfig.getProfile();

	public static void setDefaultBaseDir(String defaultBaseDir) {
		FileUploadUtils.defaultBaseDir = defaultBaseDir;
	}

	public static String getDefaultBaseDir() {
		return defaultBaseDir;
	}

	/**
	 * 以默認配置進行檔案上傳
	 *
	 * @param file 上傳的檔案
	 * @return 檔案名稱
	 * @throws Exception
	 */
	public static final String upload(MultipartFile file) throws IOException {
		try {
			return upload(getDefaultBaseDir(), file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
		} catch (Exception e) {
			throw new IOException(e.getMessage(), e);
		}
	}

	/**
	 * 根據檔案路徑上傳
	 *
	 * @param baseDir 相對應用的基目錄
	 * @param file    上傳的檔案
	 * @return 檔案名稱
	 * @throws IOException
	 */
	public static final String upload(String baseDir, MultipartFile file) throws IOException {
		try {
			return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
		} catch (Exception e) {
			throw new IOException(e.getMessage(), e);
		}
	}

	/**
	 * 檔案上傳
	 *
	 * @param baseDir          相對應用的基目錄
	 * @param file             上傳的檔案
	 * @param allowedExtension 上傳檔案類型
	 * @return 返回上傳成功的檔案名
	 * @throws FileSizeLimitExceededException       如果超出最大大小
	 * @throws FileNameLengthLimitExceededException 檔案名太長
	 * @throws IOException                          比如讀寫檔案出錯時
	 * @throws InvalidExtensionException            檔案校驗異常
	 */
	public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension)
			throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
			InvalidExtensionException {
		int fileNamelength = file.getOriginalFilename().length();
		if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
			throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
		}

		assertAllowed(file, allowedExtension);

		String fileName = extractFilename(file);

		File desc = getAbsoluteFile(baseDir, fileName);
		file.transferTo(desc);
		String pathFileName = getPathFileName(baseDir, fileName);
		return pathFileName;
	}

	/**
	 * 編碼檔案名
	 */
	public static final String extractFilename(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		String extension = getExtension(file);
		fileName = DateUtils.datePath() + "/" + IdUtils.fastUUID() + "." + extension;
		return fileName;
	}

	private static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
		File desc = new File(uploadDir + File.separator + fileName);

		if (!desc.getParentFile().exists()) {
			desc.getParentFile().mkdirs();
		}
		if (!desc.exists()) {
			desc.createNewFile();
		}
		return desc;
	}

	private static final String getPathFileName(String uploadDir, String fileName) throws IOException {
		int dirLastIndex = EipulseConfig.getProfile().length() + 1;
		String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
		String pathFileName = Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
		return pathFileName;
	}

	/**
	 * 檔案大小校驗
	 *
	 * @param file 上傳的檔案
	 * @return
	 * @throws FileSizeLimitExceededException 如果超出最大大小
	 * @throws InvalidExtensionException
	 */
	public static final void assertAllowed(MultipartFile file, String[] allowedExtension)
			throws FileSizeLimitExceededException, InvalidExtensionException {
		long size = file.getSize();
		if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE) {
			throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
		}

		String fileName = file.getOriginalFilename();
		String extension = getExtension(file);
		if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
			if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION) {
				throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension,
						fileName);
			} else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION) {
				throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension,
						fileName);
			} else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION) {
				throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension,
						fileName);
			} else {
				throw new InvalidExtensionException(allowedExtension, extension, fileName);
			}
		}

	}

	/**
	 * 判斷MIME類型是否是允許的MIME類型
	 *
	 * @param extension
	 * @param allowedExtension
	 * @return
	 */
	public static final boolean isAllowedExtension(String extension, String[] allowedExtension) {
		for (String str : allowedExtension) {
			if (str.equalsIgnoreCase(extension)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 獲取檔案名的後綴
	 *
	 * @param file 表單檔案
	 * @return 後綴名
	 */
	public static final String getExtension(MultipartFile file) {
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		if (StringUtils.isEmpty(extension)) {
			extension = MimeTypeUtils.getExtension(file.getContentType());
		}
		return extension;
	}
}
