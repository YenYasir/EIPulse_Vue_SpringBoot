package com.eipulse.generator.dto;

import java.util.Date;

import lombok.Data;

/**
 * GenTableDto
 */
@Data
public class GenTableDto {

	private String tableName;

	private String tableComment;

	private Date createTime;

	private Date updateTime;

}
