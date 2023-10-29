package com.eipulse.generator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 讀取代碼生成相關配置
 */
@Component
@ConfigurationProperties(prefix = "gen")
@PropertySource(value = { "classpath:generator.yml" })
public class GenConfig {
	/**
	 * 作者
	 */
	public static String author;

	/**
	 * 生成包路徑
	 */
	public static String packageName;

	/**
	 * 自動去除表前綴，預設是false
	 */
	public static boolean autoRemovePre;

	/**
	 * 表前綴(類名不會包含表前綴)
	 */
	public static String tablePrefix;

	public static String getAuthor() {
		return author;
	}

	@Value("${author}")
	public void setAuthor(String author) {
		GenConfig.author = author;
	}

	public static String getPackageName() {
		return packageName;
	}

	@Value("${packageName}")
	public void setPackageName(String packageName) {
		GenConfig.packageName = packageName;
	}

	public static boolean getAutoRemovePre() {
		return autoRemovePre;
	}

	@Value("${autoRemovePre}")
	public void setAutoRemovePre(boolean autoRemovePre) {
		GenConfig.autoRemovePre = autoRemovePre;
	}

	public static String getTablePrefix() {
		return tablePrefix;
	}

	@Value("${tablePrefix}")
	public void setTablePrefix(String tablePrefix) {
		GenConfig.tablePrefix = tablePrefix;
	}
}
