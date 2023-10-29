package com.eipulse.system.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.eipulse.system.domain.groupkey.SysRoleDeptKey;

import lombok.Data;

/**
 * 角色和部門關聯 sys_role_dept
 */
@Entity
@Table(name = "sys_role_dept")
@IdClass(SysRoleDeptKey.class)
@Data
public class SysRoleDept implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 角色ID
	 */
	@Id
	@Column(name = "role_id")
	private Long roleId;

	/**
	 * 部門ID
	 */
	@Id
	@Column(name = "dept_id")
	private Long deptId;

}
