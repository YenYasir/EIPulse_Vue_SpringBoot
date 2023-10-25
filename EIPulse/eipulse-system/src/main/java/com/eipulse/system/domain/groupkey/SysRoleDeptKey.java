package com.eipulse.system.domain.groupkey;

import java.io.Serializable;

/**
 * 角色部門表 聯合主鍵
 *
 * @author eipulse
 */

public class SysRoleDeptKey implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
	private Long roleId;

	/**
	 * 部門id
	 */
	private Long deptId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
}