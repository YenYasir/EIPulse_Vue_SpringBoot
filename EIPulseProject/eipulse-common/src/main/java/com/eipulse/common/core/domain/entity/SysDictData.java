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
 * 字典數據表 sys_dict_data
 */
@Entity
@Table(name = "sys_dict_data")
@Data
public class SysDictData extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 字典編碼
	 */
	@Excel(name = "字典編碼", cellType = ColumnType.NUMERIC)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dict_code")
	private Long dictCode;

	/**
	 * 字典排序
	 */
	@Excel(name = "字典排序", cellType = ColumnType.NUMERIC)
	@Column(name = "dict_sort")
	private Long dictSort;

	/**
	 * 字典標籤
	 */
	@NotBlank(message = "字典標籤不能為空")
	@Size(min = 0, max = 100, message = "字典標籤長度不能超過100個字元")
	@Excel(name = "字典標籤")
	@Column(name = "dict_label")
	private String dictLabel;

	/**
	 * 字典鍵值
	 */
	@Excel(name = "字典鍵值")
	@NotBlank(message = "字典鍵值不能為空")
	@Size(min = 0, max = 100, message = "字典鍵值長度不能超過100個字元")
	@Column(name = "dict_value")
	private String dictValue;

	/**
	 * 字典類型
	 */
	@Excel(name = "字典類型")
	@NotBlank(message = "字典類型不能為空")
	@Size(min = 0, max = 100, message = "字典類型長度不能超過100個字元")
	@Column(name = "dict_type")
	private String dictType;

	/**
	 * 樣式屬性（其他樣式擴展）
	 */
	@Size(min = 0, max = 100, message = "樣式屬性長度不能超過100個字元")
	@Column(name = "css_class")
	private String cssClass;

	/**
	 * 表格字典樣式
	 */
	@Column(name = "list_class")
	private String listClass;

	/**
	 * 是否默認（Y是 N否）
	 */
	@Excel(name = "是否默認", readConverterExp = "Y=是,N=否")
	@Column(name = "is_default")
	private String isDefault;

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
	 *
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
