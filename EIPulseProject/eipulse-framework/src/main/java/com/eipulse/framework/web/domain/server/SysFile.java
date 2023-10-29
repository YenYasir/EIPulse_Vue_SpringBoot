package com.eipulse.framework.web.domain.server;

/**
 * 系統檔案相關資訊
 */
public class SysFile {
	/**
	 * 盤符路徑
	 */
	private String dirName;

	/**
	 * 盤符類型
	 */
	private String sysTypeName;

	/**
	 * 檔案類型
	 */
	private String typeName;

	/**
	 * 總大小
	 */
	private String total;

	/**
	 * 剩餘大小
	 */
	private String free;

	/**
	 * 已經使用量
	 */
	private String used;

	/**
	 * 資源的使用率
	 */
	private double usage;

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public String getSysTypeName() {
		return sysTypeName;
	}

	public void setSysTypeName(String sysTypeName) {
		this.sysTypeName = sysTypeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getFree() {
		return free;
	}

	public void setFree(String free) {
		this.free = free;
	}

	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}

	public double getUsage() {
		return usage;
	}

	public void setUsage(double usage) {
		this.usage = usage;
	}
}
