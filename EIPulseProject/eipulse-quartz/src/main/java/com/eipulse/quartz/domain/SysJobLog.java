package com.eipulse.quartz.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.eipulse.common.annotation.Excel;
import com.eipulse.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 定時任務調度日誌表 sys_job_log
 */
@Entity
@Table(name = "sys_job_log")
@Data
public class SysJobLog extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Excel(name = "日誌序號")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "job_log_id")
	private Long jobLogId;

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
	 * 調用目標字串
	 */
	@Excel(name = "調用目標字串")
	@Column(name = "invoke_target")
	private String invokeTarget;

	/**
	 * 日誌資訊
	 */
	@Excel(name = "日誌資訊")
	@Column(name = "job_message")
	private String jobMessage;

	/**
	 * 執行狀態（0正常 1失敗）
	 */
	@Excel(name = "執行狀態", readConverterExp = "0=正常,1=失敗")
	@Column(name = "status")
	private String status;

	/**
	 * 異常資訊
	 */
	@Excel(name = "異常資訊")
	@Column(name = "exception_info")
	private String exceptionInfo;

	/**
	 * 開始時間
	 */
	private Date startTime;

	/**
	 * 停止時間
	 */
	private Date stopTime;

	/**
	 * 創建時間
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "create_time")
	private Date createTime;

}
