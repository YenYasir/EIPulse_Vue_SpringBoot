package com.eipulse.system.domain.groupkey;

import java.io.Serializable;

/**
 * 員工和角色關聯 sys_user_role 聯合主鍵
 *
 * @author eipulse
 */
public class SysUserRoleKey implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 員工ID
	 */
	private Long userId;

	/**
	 * 角色ID
	 */
	private Long roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}