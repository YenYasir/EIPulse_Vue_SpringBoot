package com.eipulse.system.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.eipulse.system.domain.groupkey.SysUserRoleKey;

import lombok.Data;

/**
 * 員工和角色關聯 sys_user_role
 */
@Entity
@Table(name = "sys_user_role")
@IdClass(SysUserRoleKey.class)
@Data
public class SysUserRole implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 員工ID
	 */
	@Id
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 角色ID
	 */
	@Id
	@Column(name = "role_id")
	private Long roleId;
}
