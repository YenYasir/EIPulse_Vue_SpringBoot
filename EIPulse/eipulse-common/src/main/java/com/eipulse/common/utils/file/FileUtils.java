package com.eipulse.common.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.eipulse.common.config.EipulseConfig;
import com.eipulse.common.utils.DateUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.uuid.IdUtils;

/**
 * 檔案處理工具類
 * 
 * @author ruoyi
 */
public class FileUtils {
	public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

	/**
	 * 輸出指定檔案的byte數組
	 * 
	 * @param filePath 檔案路徑
	 * @param os       輸出流
	 * @return
	 */
	public static void writeBytes(String filePath, OutputStream os) throws IOException {
		FileInputStream fis = null;
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				throw new FileNotFoundException(filePath);
			}
			fis = new FileInputStream(file);
			byte[] b = new byte[1024];
			int length;
			while ((length = fis.read(b)) > 0) {
				os.write(b, 0, length);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			IOUtils.close(os);
			IOUtils.close(fis);
		}
	}

	/**
	 * 寫數據到檔案中
	 *
	 * @param data 數據
	 * @return 目標檔案
	 * @throws IOException IO異常
	 */
	public static String writeImportBytes(byte[] data) throws IOException {
		return writeBytes(data, EipulseConfig.getImportPath());
	}

	/**
	 * 寫數據到檔案中
	 *
	 * @param data      數據
	 * @param uploadDir 目標檔案
	 * @return 目標檔案
	 * @throws IOException IO異常
	 */
	public static String writeBytes(byte[] data, String uploadDir) throws IOException {
		FileOutputStream fos = null;
		String pathName = "";
		try {
			String extension = getFileExtendName(data);
			pathName = DateUtils.datePath() + "/" + IdUtils.fastUUID() + "." + extension;
			File file = FileUploadUtils.getAbsoluteFile(uploadDir, pathName);
			fos = new FileOutputStream(file);
			fos.write(data);
		} finally {
			IOUtils.close(fos);
		}
		return FileUploadUtils.getPathFileName(uploadDir, pathName);
	}

	/**
	 * 刪除檔案
	 * 
	 * @param filePath 檔案
	 * @return
	 */
	public static boolean deleteFile(String filePath) {
		boolean flag = false;
		File file = new File(filePath);
		// 路徑為檔案且不為空則進行刪除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 檔案名稱驗證
	 * 
	 * @param filename 檔案名稱
	 * @return true 正常 false 非法
	 */
	public static boolean isValidFilename(String filename) {
		return filename.matches(FILENAME_PATTERN);
	}

	/**
	 * 檢查檔案是否可下載
	 * 
	 * @param resource 需要下載的檔案
	 * @return true 正常 false 非法
	 */
	public static boolean checkAllowDownload(String resource) {
		// 禁止目錄上跳級別
		if (StringUtils.contains(resource, "..")) {
			return false;
		}

		// 檢查允許下載的檔案規則
		if (ArrayUtils.contains(MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION, FileTypeUtils.getFileType(resource))) {
			return true;
		}

		// 不在允許下載的檔案規則
		return false;
	}

	/**
	 * 下載檔案名重新編碼
	 * 
	 * @param request  請求物件
	 * @param fileName 檔案名
	 * @return 編碼後的檔案名
	 */
	public static String setFileDownloadHeader(HttpServletRequest request, String fileName)
			throws UnsupportedEncodingException {
		final String agent = request.getHeader("USER-AGENT");
		String filename = fileName;
		if (agent.contains("MSIE")) {
			// IE瀏覽器
			filename = URLEncoder.encode(filename, "utf-8");
			filename = filename.replace("+", " ");
		} else if (agent.contains("Firefox")) {
			// 火狐瀏覽器
			filename = new String(fileName.getBytes(), "ISO8859-1");
		} else if (agent.contains("Chrome")) {
			// google瀏覽器
			filename = URLEncoder.encode(filename, "utf-8");
		} else {
			// 其它瀏覽器
			filename = URLEncoder.encode(filename, "utf-8");
		}
		return filename;
	}

	/**
	 * 下載檔案名重新編碼
	 *
	 * @param response     響應物件
	 * @param realFileName 真實檔案名
	 * @return
	 */
	public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName)
			throws UnsupportedEncodingException {
		String percentEncodedFileName = percentEncode(realFileName);

		StringBuilder contentDispositionValue = new StringBuilder();
		contentDispositionValue.append("attachment; filename=").append(percentEncodedFileName).append(";")
				.append("filename*=").append("utf-8''").append(percentEncodedFileName);

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename");
		response.setHeader("Content-disposition", contentDispositionValue.toString());
		response.setHeader("download-filename", percentEncodedFileName);
	}

	/**
	 * 百分號編碼工具方法
	 *
	 * @param s 需要百分號編碼的字符串
	 * @return 百分號編碼後的字符串
	 */
	public static String percentEncode(String s) throws UnsupportedEncodingException {
		String encode = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
		return encode.replaceAll("\\+", "%20");
	}

	/**
	 * 獲取圖像後綴
	 * 
	 * @param photoByte 圖像數據
	 * @return 後綴名
	 */
	public static String getFileExtendName(byte[] photoByte) {
		String strFileExtendName = "jpg";
		if ((photoByte[0] == 71) && (photoByte[1] == 73) && (photoByte[2] == 70) && (photoByte[3] == 56)
				&& ((photoByte[4] == 55) || (photoByte[4] == 57)) && (photoByte[5] == 97)) {
			strFileExtendName = "gif";
		} else if ((photoByte[6] == 74) && (photoByte[7] == 70) && (photoByte[8] == 73) && (photoByte[9] == 70)) {
			strFileExtendName = "jpg";
		} else if ((photoByte[0] == 66) && (photoByte[1] == 77)) {
			strFileExtendName = "bmp";
		} else if ((photoByte[1] == 80) && (photoByte[2] == 78) && (photoByte[3] == 71)) {
			strFileExtendName = "png";
		}
		return strFileExtendName;
	}
}
