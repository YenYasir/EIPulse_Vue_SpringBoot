package com.eipulse.system.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eipulse.common.core.domain.entity.SysMenu;
import com.eipulse.common.core.domain.entity.SysRole;

/**
 * 菜單表 數據層
 */
public interface SysMenuDaoCustom {

	List<SysMenu> findMenuListByUserId(SysMenu menu);

	List<String> findMenuPermsByUserId(Long userId);

	List<Long> findMenuListByRoleId(Long roleId, boolean menuCheckStrictly);

	Page<SysRole> findMixPage(SysRole req, Pageable pageable);

	List<SysRole> findRolePermissionByUserId(Long userId);

	List<SysMenu> findMenuTreeAll();

	List<SysMenu> findMenuTreeByUserId(Long userId);
}
