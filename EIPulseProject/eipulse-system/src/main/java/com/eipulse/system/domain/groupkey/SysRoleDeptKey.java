package com.eipulse.system.domain.groupkey;

import java.io.Serializable;

import lombok.Data;

/**
 * 角色部門表 聯合主鍵
 */
@Data
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

}
