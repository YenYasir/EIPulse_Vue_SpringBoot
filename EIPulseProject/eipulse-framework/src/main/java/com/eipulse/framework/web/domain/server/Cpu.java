package com.eipulse.framework.web.domain.server;

import com.eipulse.common.utils.Arith;

/**
 * CPU相關資訊
 */
public class Cpu {
	/**
	 * 核心數
	 */
	private int cpuNum;

	/**
	 * CPU總的使用率
	 */
	private double total;

	/**
	 * CPU系統使用率
	 */
	private double sys;

	/**
	 * CPU員工使用率
	 */
	private double used;

	/**
	 * CPU當前等待率
	 */
	private double wait;

	/**
	 * CPU當前空閒率
	 */
	private double free;

	public int getCpuNum() {
		return cpuNum;
	}

	public void setCpuNum(int cpuNum) {
		this.cpuNum = cpuNum;
	}

	public double getTotal() {
		return Arith.round(Arith.mul(total, 100), 2);
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getSys() {
		return Arith.round(Arith.mul(sys / total, 100), 2);
	}

	public void setSys(double sys) {
		this.sys = sys;
	}

	public double getUsed() {
		return Arith.round(Arith.mul(used / total, 100), 2);
	}

	public void setUsed(double used) {
		this.used = used;
	}

	public double getWait() {
		return Arith.round(Arith.mul(wait / total, 100), 2);
	}

	public void setWait(double wait) {
		this.wait = wait;
	}

	public double getFree() {
		return Arith.round(Arith.mul(free / total, 100), 2);
	}

	public void setFree(double free) {
		this.free = free;
	}
}
