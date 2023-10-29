package com.eipulse.framework.manager;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 確保應用退出時能關閉後台執行緒
 */
@Component
public class ShutdownManager {
	private static final Logger logger = LoggerFactory.getLogger("sys-user");

	@PreDestroy
	public void destroy() {
		shutdownAsyncManager();
	}

	/**
	 * 停止非同步執行任務
	 */
	private void shutdownAsyncManager() {
		try {
			logger.info("====關閉後台任務任務執行緒池====");
			AsyncManager.me().shutdown();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
