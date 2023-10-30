package com.eipulse.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 讀取項目相關配置
 */
@Component
@ConfigurationProperties(prefix = "eipulse")
public class EipulseConfig {
	/**
	 * 項目名稱
	 */
	private String name;

	/**
	 * 版本
	 */
	private String version;

	/**
	 * 版權年份
	 */
	private String copyrightYear;

	/**
	 * 實例示範開關
	 */
	private boolean demoEnabled;

	/**
	 * 上傳路徑
	 */
	private static String profile;

	/**
	 * 獲取位址開關
	 */
	private static boolean addressEnabled;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCopyrightYear() {
		return copyrightYear;
	}

	public void setCopyrightYear(String copyrightYear) {
		this.copyrightYear = copyrightYear;
	}

	public boolean isDemoEnabled() {
		return demoEnabled;
	}

	public void setDemoEnabled(boolean demoEnabled) {
		this.demoEnabled = demoEnabled;
	}

	public static String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		EipulseConfig.profile = profile;
	}

	public static boolean isAddressEnabled() {
		return addressEnabled;
	}

	public void setAddressEnabled(boolean addressEnabled) {
		EipulseConfig.addressEnabled = addressEnabled;
	}

	/**
	 * 獲取頭像上傳路徑
	 */
	public static String getAvatarPath() {
		return getProfile() + "/avatar";
	}

	/**
	 * 獲取下載路徑
	 */
	public static String getDownloadPath() {
		return getProfile() + "/download/";
	}

	/**
	 * 獲取上傳路徑
	 */
	public static String getUploadPath() {
		return getProfile() + "/upload";
	}
}
