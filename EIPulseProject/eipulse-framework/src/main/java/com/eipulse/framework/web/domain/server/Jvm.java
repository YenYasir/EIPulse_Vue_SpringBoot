package com.eipulse.framework.web.domain.server;

import java.lang.management.ManagementFactory;

import com.eipulse.common.utils.Arith;
import com.eipulse.common.utils.DateUtils;

/**
 * JVM相關資訊
 */
public class Jvm {
	/**
	 * 當前JVM占用的記憶體總數(M)
	 */
	private double total;

	/**
	 * JVM最大可用記憶體總數(M)
	 */
	private double max;

	/**
	 * JVM空閒記憶體(M)
	 */
	private double free;

	/**
	 * JDK版本
	 */
	private String version;

	/**
	 * JDK路徑
	 */
	private String home;

	public double getTotal() {
		return Arith.div(total, (1024 * 1024), 2);
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getMax() {
		return Arith.div(max, (1024 * 1024), 2);
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getFree() {
		return Arith.div(free, (1024 * 1024), 2);
	}

	public void setFree(double free) {
		this.free = free;
	}

	public double getUsed() {
		return Arith.div(total - free, (1024 * 1024), 2);
	}

	public double getUsage() {
		return Arith.mul(Arith.div(total - free, total, 4), 100);
	}

	/**
	 * 獲取JDK名稱
	 */
	public String getName() {
		return ManagementFactory.getRuntimeMXBean().getVmName();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	/**
	 * JDK啟動時間
	 */
	public String getStartTime() {
		return DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, DateUtils.getServerStartDate());
	}

	/**
	 * JDK運行時間
	 */
	public String getRunTime() {
		return DateUtils.getDatePoor(DateUtils.getNowDate(), DateUtils.getServerStartDate());
	}
}
