package com.eipulse.framework.config;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.eipulse.common.utils.Threads;

/**
 * 執行緒池配置
 **/
@Configuration
public class ThreadPoolConfig {
	// 核心執行緒池大小
	private int corePoolSize = 50;

	// 最大可創建的執行緒數
	private int maxPoolSize = 200;

	// 隊列最大長度
	private int queueCapacity = 1000;

	// 執行緒池維護執行緒所允許的空閒時間
	private int keepAliveSeconds = 300;

	@Bean(name = "threadPoolTaskExecutor")
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setMaxPoolSize(maxPoolSize);
		executor.setCorePoolSize(corePoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setKeepAliveSeconds(keepAliveSeconds);
		// 執行緒池對拒絕任務(無執行緒可用)的處理策略
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return executor;
	}

	/**
	 * 執行週期性或定時任務
	 */
	@Bean(name = "scheduledExecutorService")
	protected ScheduledExecutorService scheduledExecutorService() {
		return new ScheduledThreadPoolExecutor(corePoolSize,
				new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build()) {
			@Override
			protected void afterExecute(Runnable r, Throwable t) {
				super.afterExecute(r, t);
				Threads.printException(r, t);
			}
		};
	}
}
