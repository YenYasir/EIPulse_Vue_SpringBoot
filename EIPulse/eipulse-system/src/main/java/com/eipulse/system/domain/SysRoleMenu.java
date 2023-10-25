package com.eipulse.system.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.eipulse.system.domain.groupkey.SysRoleMenuKey;

/**
 * 角色和菜單關聯 sys_role_menu
 *
 * @author eipulse
 */
@Entity
@Table(name = "sys_role_menu")
@IdClass(SysRoleMenuKey.class)
public class SysRoleMenu implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 角色ID
	 */
	@Id
	@Column(name = "role_id")
	private Long roleId;

	/**
	 * 菜單ID
	 */
	@Id
	@Column(name = "menu_id")
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