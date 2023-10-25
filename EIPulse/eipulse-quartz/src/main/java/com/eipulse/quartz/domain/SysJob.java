package com.eipulse.quartz.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.eipulse.common.annotation.Excel;
import com.eipulse.common.annotation.Excel.ColumnType;
import com.eipulse.common.constant.ScheduleConstants;
import com.eipulse.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 定時任務調度表 sys_job
 *
 * @author eipulse
 */
@Entity
@Table(name = "sys_job")

public class SysJob extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 任務ID
	 */
	@Excel(name = "任務代號", cellType = ColumnType.NUMERIC)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "job_id")
	private Long jobId;

	/**
	 * 任務名稱
	 */
	@Excel(name = "任務名稱")
	@Column(name = "job_name")
	private String jobName;

	/**
	 * 任務組名
	 */
	@Excel(name = "任務組名")
	@Column(name = "job_group")
	private String jobGroup;

	/**
	 * 調用目標字符串
	 */
	@Excel(name = "調用目標字串")
	@Column(name = "invoke_target")
	private String invokeTarget;

	/**
	 * cron執行表達式
	 */
	@Excel(name = "執行表達式 ")
	@Column(name = "cron_expression")
	private String cronExpression;

	/**
	 * cron計劃策略
	 */
	@Excel(name = "計劃策略 ", readConverterExp = "0=默認,1=立即觸發執行,2=觸發一次執行,3=不觸發立即執行")
	@Column(name = "misfire_policy")
	private String misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;

	/**
	 * 是否併發執行（0允許 1禁止）
	 */
	@Excel(name = "併發執行", readConverterExp = "0=允許,1=禁止")
	@Column(name = "concurrent")
	private String concurrent;

	/**
	 * 任務狀態（0正常 1暫停）
	 */
	@Excel(name = "任務狀態", readConverterExp = "0=正常,1=暫停")
	@Column(name = "status")
	private String status;

	/**
	 * 創建者
	 */
	@Column(name = "create_by")
	private String createBy;

	/**
	 * 創建時間
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "create_time")
	private Date createTime;

	/**
	 * 更新者
	 */
	@Column(name = "update_by")
	private String updateBy;

	/**
	 * 更新時間
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "update_time")
	private Date updateTime;

	/**
	 * 備註
	 */
	@Column(name = "remark")
	private String remark;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getInvokeTarget() {
		return invokeTarget;
	}

	public void setInvokeTarget(String invokeTarget) {
		this.invokeTarget = invokeTarget;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getMisfirePolicy() {
		return misfirePolicy;
	}

	public void setMisfirePolicy(String misfirePolicy) {
		this.misfirePolicy = misfirePolicy;
	}

	public String getConcurrent() {
		return concurrent;
	}

	public void setConcurrent(String concurrent) {
		this.concurrent = concurrent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
