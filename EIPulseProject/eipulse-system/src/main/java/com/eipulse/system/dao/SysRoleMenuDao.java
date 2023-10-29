package com.eipulse.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eipulse.system.domain.SysRoleMenu;
import com.eipulse.system.domain.groupkey.SysRoleMenuKey;

/**
 * 角色與菜單關聯表 數據層
 */
@Repository
public interface SysRoleMenuDao
		extends JpaRepository<SysRoleMenu, SysRoleMenuKey>, JpaSpecificationExecutor<SysRoleMenu>, SysMenuDaoCustom {

	@Query(value = " select count(1) from sys_role_menu where menu_id = ?1   ", nativeQuery = true)
	Integer checkMenuExistRole(Long menuId);

	void deleteByRoleId(Long roleId);

	void deleteByRoleIdIn(List<Long> roleId);

	List<SysRoleMenu> findByRoleId(Long roleId);

}
