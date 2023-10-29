package com.eipulse.framework.web.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.eipulse.common.core.domain.entity.SysRole;
import com.eipulse.common.core.domain.model.LoginUser;
import com.eipulse.common.utils.ServletUtils;
import com.eipulse.common.utils.StringUtils;

/**
 * 自訂權限實現，ss取自SpringSecurity首字母
 */
@Service("ss")
public class PermissionService {
	/**
	 * 所有權限標識
	 */
	private static final String ALL_PERMISSION = "*:*:*";

	/**
	 * 管理員角色權限標識
	 */
	private static final String SUPER_ADMIN = "admin";

	private static final String ROLE_DELIMETER = ",";

	private static final String PERMISSION_DELIMETER = ",";

	@Autowired
	private TokenService tokenService;

	/**
	 * 驗證員工是否具備某權限
	 *
	 * @param permission 權限字串
	 * @return 員工是否具備某權限
	 */
	public boolean hasPermi(String permission) {
		if (StringUtils.isEmpty(permission)) {
			return false;
		}
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions())) {
			return false;
		}
		return hasPermissions(loginUser.getPermissions(), permission);
	}

	/**
	 * 驗證員工是否不具備某權限，與 hasPermi邏輯相反
	 *
	 * @param permission 權限字串
	 * @return 員工是否不具備某權限
	 */
	public boolean lacksPermi(String permission) {
		return hasPermi(permission) != true;
	}

	/**
	 * 驗證員工是否具有以下任意一個權限
	 *
	 * @param permissions 以 PERMISSION_NAMES_DELIMETER 為分隔符號的權限列表
	 * @return 員工是否具有以下任意一個權限
	 */
	public boolean hasAnyPermi(String permissions) {
		if (StringUtils.isEmpty(permissions)) {
			return false;
		}
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions())) {
			return false;
		}
		Set<String> authorities = loginUser.getPermissions();
		for (String permission : permissions.split(PERMISSION_DELIMETER)) {
			if (permission != null && hasPermissions(authorities, permission)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判斷員工是否擁有某個角色
	 *
	 * @param role 角色字串
	 * @return 員工是否具備某角色
	 */
	public boolean hasRole(String role) {
		if (StringUtils.isEmpty(role)) {
			return false;
		}
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getUser().getRoles())) {
			return false;
		}
		for (SysRole sysRole : loginUser.getUser().getRoles()) {
			String roleKey = sysRole.getRoleKey();
			if (SUPER_ADMIN.equals(roleKey) || roleKey.equals(StringUtils.trim(role))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 驗證員工是否不具備某角色，與 isRole邏輯相反。
	 *
	 * @param role 角色名稱
	 * @return 員工是否不具備某角色
	 */
	public boolean lacksRole(String role) {
		return hasRole(role) != true;
	}

	/**
	 * 驗證員工是否具有以下任意一個角色
	 *
	 * @param roles 以 ROLE_NAMES_DELIMETER 為分隔符號的角色列表
	 * @return 員工是否具有以下任意一個角色
	 */
	public boolean hasAnyRoles(String roles) {
		if (StringUtils.isEmpty(roles)) {
			return false;
		}
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getUser().getRoles())) {
			return false;
		}
		for (String role : roles.split(ROLE_DELIMETER)) {
			if (hasRole(role)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判斷是否包含權限
	 *
	 * @param permissions 權限列表
	 * @param permission  權限字串
	 * @return 員工是否具備某權限
	 */
	private boolean hasPermissions(Set<String> permissions, String permission) {
		return permissions.contains(ALL_PERMISSION) || permissions.contains(StringUtils.trim(permission));
	}
}
