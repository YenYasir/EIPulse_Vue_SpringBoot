package com.eipulse.system.domain.groupkey;

import java.io.Serializable;

/**
 * 角色和菜單關聯 sys_role_menu 聯合主鍵
 *
 * @author eipulse
 */

public class SysRoleMenuKey implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 菜單ID
	 */
	private Long menuId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
}