package com.eipulse.business.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.eipulse.common.annotation.Excel;
import com.eipulse.common.core.domain.BaseEntity;

import lombok.Data;

/**
 * 測試代碼生成器物件 wang_ttt
 */
@Entity
@Table(name = "wang_ttt")
@Data
public class WangTtt extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 主鍵 */
	@Id
	@GenericGenerator(name = "idGen", strategy = "uuid")
	@GeneratedValue(generator = "idGen")
	@Column(name = "id")
	private String id;

	/** 字串欄位 */
	@Excel(name = "字串欄位")
	@Column(name = "column_a")
	private String columnA;

	/** int欄位 */
	@Excel(name = "int欄位")
	@Column(name = "column_b")
	private Long columnB;

	/** decimal欄位 */
	@Excel(name = "decimal欄位")
	@Column(name = "column_c")
	private BigDecimal columnC;

	/** 創建時間 */
	@Column(name = "create_time")
	private Date createTime;

	/** 創建者 */
	@Column(name = "create_by")
	private String createBy;

	/** 更新時間 */
	@Column(name = "update_time")
	private Date updateTime;

	/** 更新者 */
	@Column(name = "update_by")
	private String updateBy;

}
