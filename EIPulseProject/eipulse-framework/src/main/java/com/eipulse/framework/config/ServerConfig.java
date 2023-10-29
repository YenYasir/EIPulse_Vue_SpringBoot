package com.eipulse.framework.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.eipulse.common.utils.ServletUtils;

/**
 * 服務相關配置
 */
@Component
public class ServerConfig {
	/**
	 * 獲取完整的請求路徑，包括：域名，埠，上下文訪問路徑
	 *
	 * @return 服務位址
	 */
	public String getUrl() {
		HttpServletRequest request = ServletUtils.getRequest();
		return getDomain(request);
	}

	public static String getDomain(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		String contextPath = request.getServletContext().getContextPath();
		return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
	}
}
