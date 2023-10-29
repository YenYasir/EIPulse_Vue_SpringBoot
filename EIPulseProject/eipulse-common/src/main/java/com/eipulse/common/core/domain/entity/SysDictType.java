package com.eipulse.common.core.domain.entity;

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
 * 字典類型表 sys_dict_type
 */
@Entity
@Table(name = "sys_dict_type")
@Data
public class SysDictType extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 字典主鍵
	 */
	@Excel(name = "字典主鍵", cellType = ColumnType.NUMERIC)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dict_id")
	private Long dictId;

	/**
	 * 字典名稱
	 */
	@Excel(name = "字典名稱")
	@NotBlank(message = "字典名稱不能為空")
	@Size(min = 0, max = 100, message = "字典類型名稱長度不能超過100個字元")
	@Column(name = "dict_name")
	private String dictName;

	/**
	 * 字典類型
	 */
	@Excel(name = "字典類型")
	@NotBlank(message = "字典類型不能為空")
	@Size(min = 0, max = 100, message = "字典類型類型長度不能超過100個字元")
	@Column(name = "dict_type")
	private String dictType;

	/**
	 * 狀態（0正常 1停用）
	 */
	@Excel(name = "狀態", readConverterExp = "0=正常,1=停用")
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

}
