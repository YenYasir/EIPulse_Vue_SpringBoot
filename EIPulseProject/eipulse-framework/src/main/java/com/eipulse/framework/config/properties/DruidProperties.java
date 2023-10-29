package com.eipulse.framework.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * druid 配置屬性
 */
@Configuration
public class DruidProperties {
	@Value("${spring.datasource.druid.initialSize}")
	private int initialSize;

	@Value("${spring.datasource.druid.minIdle}")
	private int minIdle;

	@Value("${spring.datasource.druid.maxActive}")
	private int maxActive;

	@Value("${spring.datasource.druid.maxWait}")
	private int maxWait;

	@Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;

	@Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;

	@Value("${spring.datasource.druid.maxEvictableIdleTimeMillis}")
	private int maxEvictableIdleTimeMillis;

	@Value("${spring.datasource.druid.validationQuery}")
	private String validationQuery;

	@Value("${spring.datasource.druid.testWhileIdle}")
	private boolean testWhileIdle;

	@Value("${spring.datasource.druid.testOnBorrow}")
	private boolean testOnBorrow;

	@Value("${spring.datasource.druid.testOnReturn}")
	private boolean testOnReturn;

	public DruidDataSource dataSource(DruidDataSource datasource) {
		/** 配置初始化大小、最小、最大 */
		datasource.setInitialSize(initialSize);
		datasource.setMaxActive(maxActive);
		datasource.setMinIdle(minIdle);

		/** 配置獲取連接等待超時的時間 */
		datasource.setMaxWait(maxWait);

		/** 配置間隔多久才進行一次檢測，檢測需要關閉的空閒連接，單位是毫秒 */
		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

		/** 配置一個連接在池中最小、最大生存的時間，單位是毫秒 */
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		datasource.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);

		/**
		 * 用來檢測連接是否有效的sql，要求是一個查詢語句，常用select
		 * 'x'。如果validationQuery為null，testOnBorrow、testOnReturn、testWhileIdle都不會起作用。
		 */
		datasource.setValidationQuery(validationQuery);
		/**
		 * 建議配置為true，不影響性能，並且保證安全性。申請連接的時候檢測，如果空閒時間大於timeBetweenEvictionRunsMillis，執行validationQuery檢測連接是否有效。
		 */
		datasource.setTestWhileIdle(testWhileIdle);
		/** 申請連接時執行validationQuery檢測連接是否有效，做了這個配置會降低性能。 */
		datasource.setTestOnBorrow(testOnBorrow);
		/** 歸還連接時執行validationQuery檢測連接是否有效，做了這個配置會降低性能。 */
		datasource.setTestOnReturn(testOnReturn);
		return datasource;
	}
}
