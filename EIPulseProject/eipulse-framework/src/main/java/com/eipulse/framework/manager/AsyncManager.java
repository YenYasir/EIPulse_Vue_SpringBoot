package com.eipulse.framework.manager;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.eipulse.common.utils.Threads;
import com.eipulse.common.utils.spring.SpringUtils;

/**
 * 非同步任務管理器
 */
public class AsyncManager {
	/**
	 * 操作延遲10毫秒
	 */
	private final int OPERATE_DELAY_TIME = 10;

	/**
	 * 非同步操作任務調度執行緒池
	 */
	private ScheduledExecutorService executor = SpringUtils.getBean("scheduledExecutorService");

	/**
	 * 單例模式
	 */
	private AsyncManager() {
	}

	private static AsyncManager me = new AsyncManager();

	public static AsyncManager me() {
		return me;
	}

	/**
	 * 執行任務
	 */
	public void execute(TimerTask task) {
		executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
	}

	/**
	 * 停止任務執行緒池
	 */
	public void shutdown() {
		Threads.shutdownAndAwaitTermination(executor);
	}
}
