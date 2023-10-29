package com.eipulse.system.domain.groupkey;

import java.io.Serializable;

import lombok.Data;

/**
 * 員工和角色關聯 sys_user_role 聯合主鍵
 */
@Data
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
}
