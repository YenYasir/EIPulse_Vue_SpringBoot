package com.eipulse.common.core.dto;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

/**
 * 部門表 sys_dept
 */
@Data
public class SysDeptDto {

	/**
	 * 部門ID
	 */
	private BigInteger deptId;

	/**
	 * 父部門ID
	 */
	private BigInteger parentId;

	/**
	 * 祖級列表
	 */
	private String ancestors;

	/**
	 * 部門名稱
	 */
	private String deptName;

	/**
	 * 顯示順序
	 */
	private Integer orderNum;

	/**
	 * 負責人
	 */
	private String leader;

	/**
	 * 聯絡電話
	 */
	private String phone;

	/**
	 * 信箱
	 */
	private String email;

	/**
	 * 部門狀態:0正常,1停用
	 */
	private Character status;

	/**
	 * 刪除標誌（0代表存在 2代表刪除）
	 */
	private Character delFlag;

	/**
	 * 創建者
	 */

	private String createBy;

	/**
	 * 創建時間
	 */

	private Date createTime;

	/**
	 * 更新者
	 */

	private String updateBy;

	/**
	 * 更新時間
	 */

	private Date updateTime;

}
