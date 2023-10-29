package com.eipulse.system.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.eipulse.common.annotation.Excel;
import com.eipulse.common.annotation.Excel.ColumnType;
import com.eipulse.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 參數配置表 sys_config
 */
@Entity
@Table(name = "sys_config")
@Data
public class SysConfig extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 參數主鍵
	 */
	@Excel(name = "參數主鍵", cellType = ColumnType.NUMERIC)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "config_id")
	private Long configId;

	/**
	 * 參數名稱
	 */
	@Excel(name = "參數名稱")
	@NotBlank(message = "參數名稱不能為空")
	@Size(min = 0, max = 100, message = "參數名稱不能超過100個字元")
	@Column(name = "config_name")
	private String configName;

	/**
	 * 參數鍵名
	 */
	@Excel(name = "參數鍵名")
	@NotBlank(message = "參數鍵名長度不能為空")
	@Size(min = 0, max = 100, message = "參數鍵名長度不能超過100個字元")
	@Column(name = "config_key")
	private String configKey;

	/**
	 * 參數鍵值
	 */
	@Excel(name = "參數鍵值")
	@NotBlank(message = "參數鍵值不能為空")
	@Size(min = 0, max = 500, message = "參數鍵值長度不能超過500個字元")
	@Column(name = "config_value")
	private String configValue;

	/**
	 * 系統內建（Y是 N否）
	 */
	@Excel(name = "系統內建", readConverterExp = "Y=是,N=否")
	@Column(name = "config_type")
	private String configType;

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

}
