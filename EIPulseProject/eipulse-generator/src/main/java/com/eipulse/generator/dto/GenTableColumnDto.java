package com.eipulse.generator.dto;

import com.eipulse.common.core.domain.BaseEntity;

import lombok.Data;

/**
 * 代碼生成業務欄位表 gen_table_column
 */
@Data
public class GenTableColumnDto extends BaseEntity {

	/**
	 * 列名稱
	 */
	private String columnName;

	/**
	 * 是否必填（1是）
	 */
	private String isRequired;

	/**
	 * 是否主鍵（1是）
	 */
	private String isPk;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 列描述
	 */
	private String columnComment;

	/**
	 * 是否自增（1是）
	 */
	private String isIncrement;

	/**
	 * 列類型
	 */
	private String columnType;

}
