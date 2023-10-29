package com.eipulse.system.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.eipulse.common.annotation.Excel;
import com.eipulse.common.annotation.Excel.ColumnType;
import com.eipulse.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 操作日誌記錄表
 */
@Entity
@Table(name = "sys_oper_log")
@Data
public class SysOperLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 日誌主鍵
	 */
	@Excel(name = "操作序號", cellType = ColumnType.NUMERIC)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "oper_id")
	private Long operId;

	/**
	 * 操作模組
	 */
	@Excel(name = "操作模組")
	@Column(name = "title")
	private String title;

	/**
	 * 業務類型（0其它 1新增 2修改 3刪除）
	 */
	@Excel(name = "業務類型", readConverterExp = "0=其它,1=新增,2=修改,3=刪除,4=授權,5=導出,6=導入,7=強退,8=生成程式碼,9=清空數據")
	@Column(name = "business_type")
	private Integer businessType;

	/**
	 * 請求方法
	 */
	@Column(name = "method")
	@Excel(name = "請求方法")
	private String method;

	/**
	 * 請求方式
	 */
	@Excel(name = "請求方式")
	@Column(name = "request_method")
	private String requestMethod;

	/**
	 * 操作類別（0其它 1後台員工 2手機端員工）
	 */
	@Excel(name = "操作類別", readConverterExp = "0=其它,1=後台員工,2=手機端員工")
	@Column(name = "operator_type")
	private Integer operatorType;

	/**
	 * 操作人員
	 */
	@Excel(name = "操作人員")
	@Column(name = "oper_name")
	private String operName;

	/**
	 * 部門名稱
	 */
	@Excel(name = "部門名稱")
	@Column(name = "dept_name")
	private String deptName;

	/**
	 * 請求url
	 */
	@Excel(name = "請求位址")
	@Column(name = "oper_url")
	private String operUrl;

	/**
	 * 操作位址
	 */
	@Excel(name = "操作位址")
	@Column(name = "oper_ip")
	private String operIp;

	/**
	 * 操作地點
	 */
	@Excel(name = "操作地點")
	@Column(name = "oper_location")
	private String operLocation;

	/**
	 * 請求參數
	 */
	@Excel(name = "請求參數")
	@Column(name = "oper_param")
	private String operParam;

	/**
	 * 返回參數
	 */
	@Excel(name = "返回參數")
	@Column(name = "json_result")
	private String jsonResult;

	/**
	 * 操作狀態（0正常 1異常）
	 */
	@Excel(name = "狀態", readConverterExp = "0=正常,1=異常")
	@Column(name = "status")
	private Integer status;

	/**
	 * 錯誤消息
	 */
	@Excel(name = "錯誤消息")
	@Column(name = "error_msg")
	private String errorMsg;

	/**
	 * 操作時間
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Excel(name = "操作時間", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "oper_time")
	private Date operTime;

	/**
	 * 業務類型數組
	 */
	@Transient
	private Integer[] businessTypes;

}
