package com.eipulse.common.utils;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 執行緒相關工具類
 */
public class Threads {
	private static final Logger logger = LoggerFactory.getLogger(Threads.class);

	/**
	 * sleep等待,單位為毫秒
	 */
	public static void sleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			return;
		}
	}

	/**
	 * 停止執行緒池 先使用shutdown, 停止接收新任務並嘗試完成所有已存在任務. 如果超時, 則調用shutdownNow,
	 * 取消在workQueue中Pending的任務,並中斷所有阻塞函數. 如果仍人超時，則強制退出. 另對在shutdown時執行緒本身被調用中斷做了處理.
	 */
	public static void shutdownAndAwaitTermination(ExecutorService pool) {
		if (pool != null && !pool.isShutdown()) {
			pool.shutdown();
			try {
				if (!pool.awaitTermination(120, TimeUnit.SECONDS)) {
					pool.shutdownNow();
					if (!pool.awaitTermination(120, TimeUnit.SECONDS)) {
						logger.info("Pool did not terminate");
					}
				}
			} catch (InterruptedException ie) {
				pool.shutdownNow();
				Thread.currentThread().interrupt();
			}
		}
	}

	/**
	 * 列印執行緒異常資訊
	 */
	public static void printException(Runnable r, Throwable t) {
		if (t == null && r instanceof Future<?>) {
			try {
				Future<?> future = (Future<?>) r;
				if (future.isDone()) {
					future.get();
				}
			} catch (CancellationException ce) {
				t = ce;
			} catch (ExecutionException ee) {
				t = ee.getCause();
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
			}
		}
		if (t != null) {
			logger.error(t.getMessage(), t);
		}
	}
}
