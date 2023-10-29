package com.eipulse.system.domain.groupkey;

import java.io.Serializable;

import lombok.Data;

/**
 * 角色和菜單關聯 sys_role_menu 聯合主鍵
 */

@Data
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

}
