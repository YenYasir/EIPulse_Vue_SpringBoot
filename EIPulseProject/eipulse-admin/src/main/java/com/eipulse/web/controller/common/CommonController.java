package com.eipulse.web.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eipulse.common.config.EipulseConfig;
import com.eipulse.common.constant.Constants;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.file.FileUploadUtils;
import com.eipulse.common.utils.file.FileUtils;
import com.eipulse.framework.config.ServerConfig;

/**
 * 通用請求處理
 */
@RestController
public class CommonController {
	private static final Logger log = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	private ServerConfig serverConfig;

	/**
	 * 通用下載請求
	 *
	 * @param fileName 檔案名稱
	 * @param delete   是否刪除
	 */
	@GetMapping("common/download")
	public void fileDownload(String fileName, Boolean delete, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			if (!FileUtils.checkAllowDownload(fileName)) {
				throw new Exception(StringUtils.format("檔案名稱({})非法，不允許下載。 ", fileName));
			}
			String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
			String filePath = EipulseConfig.getDownloadPath() + fileName;

			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			FileUtils.setAttachmentResponseHeader(response, realFileName);
			FileUtils.writeBytes(filePath, response.getOutputStream());
			if (delete) {
				FileUtils.deleteFile(filePath);
			}
		} catch (Exception e) {
			log.error("下載文件失敗", e);
		}
	}

	/**
	 * 通用上傳請求
	 */
	@PostMapping("/common/upload")
	public AjaxResult uploadFile(MultipartFile file) throws Exception {
		try {
			// 上傳文件路徑
			String filePath = EipulseConfig.getUploadPath();
			// 上傳並返回新檔案名稱
			String fileName = FileUploadUtils.upload(filePath, file);
			String url = serverConfig.getUrl() + fileName;
			AjaxResult ajax = AjaxResult.success();
			ajax.put("fileName", fileName);
			ajax.put("url", url);
			return ajax;
		} catch (Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}

	/**
	 * 本地資源通用下載
	 */
	@GetMapping("/common/download/resource")
	public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			if (!FileUtils.checkAllowDownload(resource)) {
				throw new Exception(StringUtils.format("資源文件({})非法，不允許下載。 ", resource));
			}
			// 本地資源路徑
			String localPath = EipulseConfig.getProfile();
			// 資料庫資源位址
			String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
			// 下載名稱
			String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			FileUtils.setAttachmentResponseHeader(response, downloadName);
			FileUtils.writeBytes(downloadPath, response.getOutputStream());
		} catch (Exception e) {
			log.error("下載文件失敗", e);
		}
	}
}
