package com.eipulse.quartz.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 定時任務配置
 */
@Configuration
public class ScheduleConfig {
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setDataSource(dataSource);

		// quartz參數
		Properties prop = new Properties();
		prop.put("org.quartz.scheduler.instanceName", "RuoyiScheduler");
		prop.put("org.quartz.scheduler.instanceId", "AUTO");
		// 執行緒池配置
		prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		prop.put("org.quartz.threadPool.threadCount", "20");
		prop.put("org.quartz.threadPool.threadPriority", "5");
		// JobStore配置
		prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
		// 集群配置
		prop.put("org.quartz.jobStore.isClustered", "true");
		prop.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
		prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");
		prop.put("org.quartz.jobStore.txIsolationLevelSerializable", "true");

		// sqlserver 啟用
		// prop.put("org.quartz.jobStore.selectWithLockSQL", "SELECT * FROM {0}LOCKS
		// UPDLOCK WHERE LOCK_NAME = ?");
		prop.put("org.quartz.jobStore.misfireThreshold", "12000");
		prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
		factory.setQuartzProperties(prop);

		factory.setSchedulerName("RuoyiScheduler");
		// 延時啟動
		factory.setStartupDelay(1);
		factory.setApplicationContextSchedulerContextKey("applicationContextKey");
		// 可選，QuartzScheduler
		// 啟動時更新己存在的Job，這樣就不用每次修改targetObject後刪除qrtz_job_details表對應記錄了
		factory.setOverwriteExistingJobs(true);
		// 設置自動啟動，預設為true
		factory.setAutoStartup(true);

		return factory;
	}
}
