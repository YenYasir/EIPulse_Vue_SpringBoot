package com.eipulse.framework.web.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eipulse.common.core.domain.entity.SysUser;
import com.eipulse.system.service.ISysMenuService;
import com.eipulse.system.service.ISysRoleService;

/**
 * 員工權限處理
 */
@Component
public class SysPermissionService {
	@Autowired
	private ISysRoleService roleService;

	@Autowired
	private ISysMenuService menuService;

	/**
	 * 獲取角色數據權限
	 *
	 * @param user 員工資訊
	 * @return 角色權限資訊
	 */
	public Set<String> getRolePermission(SysUser user) {
		Set<String> roles = new HashSet<>();
		// 管理員擁有所有權限
		if (user.isAdmin()) {
			roles.add("admin");
		} else {
			roles.addAll(roleService.selectRolePermissionByUserId(user.getUserId()));
		}
		return roles;
	}

	/**
	 * 獲取菜單數據權限
	 *
	 * @param user 員工資訊
	 * @return 菜單權限資訊
	 */
	public Set<String> getMenuPermission(SysUser user) {
		Set<String> perms = new HashSet<>();
		// 管理員擁有所有權限
		if (user.isAdmin()) {
			perms.add("*:*:*");
		} else {
			perms.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
		}
		return perms;
	}
}
