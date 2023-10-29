package com.eipulse.system.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.eipulse.common.annotation.Excel;
import com.eipulse.common.annotation.Excel.ColumnType;
import com.eipulse.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 系統訪問記錄表 sys_logininfor
 */
@Entity
@Table(name = "sys_logininfor")
@Data
public class SysLogininfor extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Excel(name = "序號", cellType = ColumnType.NUMERIC)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "info_id")
	private Long infoId;

	/**
	 * 員工帳號
	 */
	@Excel(name = "員工帳號")
	@Column(name = "user_name")
	private String userName;

	/**
	 * 登入狀態 0成功 1失敗
	 */
	@Excel(name = "登入狀態", readConverterExp = "0=成功,1=失敗")
	@Column(name = "status")
	private String status;

	/**
	 * 登入IP位址
	 */
	@Excel(name = "登入位址")
	@Column(name = "ipaddr")
	private String ipaddr;

	/**
	 * 登入地點
	 */
	@Excel(name = "登入地點")
	@Column(name = "login_location")
	private String loginLocation;

	/**
	 * 瀏覽器類型
	 */
	@Excel(name = "瀏覽器")
	@Column(name = "browser")
	private String browser;

	/**
	 * 操作系統
	 */
	@Excel(name = "操作系統")
	@Column(name = "os")
	private String os;

	/**
	 * 提示消息
	 */
	@Excel(name = "提示消息")
	@Column(name = "msg")
	private String msg;

	/**
	 * 訪問時間
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Excel(name = "訪問時間", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "login_time")
	private Date loginTime;

}
