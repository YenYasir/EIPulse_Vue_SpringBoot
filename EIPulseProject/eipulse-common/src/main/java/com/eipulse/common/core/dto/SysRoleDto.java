package com.eipulse.common.core.dto;

import java.math.BigInteger;
import java.util.Date;

import com.eipulse.common.core.domain.BaseEntity;

import lombok.Data;

/**
 * 角色表 sys_role
 */
@Data
public class SysRoleDto extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	private BigInteger roleId;

	/**
	 * 角色名稱
	 */
	private String roleName;

	/**
	 * 角色權限
	 */
	private String roleKey;

	/**
	 * 角色排序
	 */
	private Integer roleSort;

	/**
	 * 數據範圍（1：所有數據權限；2：自訂數據權限；3：本部門數據權限；4：本部門及以下數據權限）
	 */
	private Character dataScope;

	/**
	 * 菜單樹選擇項是否關聯顯示（ 0：父子不互相關聯顯示 1：父子互相關聯顯示）
	 */
	private boolean menuCheckStrictly;

	/**
	 * 部門樹選擇項是否關聯顯示（0：父子不互相關聯顯示 1：父子互相關聯顯示 ）
	 */
	private boolean deptCheckStrictly;

	/**
	 * 角色狀態（0正常 1停用）
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

	/**
	 * 備註
	 */
	private String remark;

}
