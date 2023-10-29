package com.eipulse.system.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.eipulse.system.domain.groupkey.SysRoleMenuKey;

import lombok.Data;

/**
 * 角色和菜單關聯 sys_role_menu
 */
@Entity
@Table(name = "sys_role_menu")
@IdClass(SysRoleMenuKey.class)
@Data
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

}
