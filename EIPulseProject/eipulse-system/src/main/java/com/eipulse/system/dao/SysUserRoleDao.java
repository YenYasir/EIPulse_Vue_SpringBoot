package com.eipulse.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eipulse.system.domain.SysUserRole;
import com.eipulse.system.domain.groupkey.SysUserRoleKey;

/**
 * 員工與角色關聯表 數據層
 */
@Repository
public interface SysUserRoleDao
		extends JpaRepository<SysUserRole, SysUserRoleKey>, JpaSpecificationExecutor<SysUserRole>, SysMenuDaoCustom {

	@Query(value = " select count(1) from sys_user_role where role_id=? ", nativeQuery = true)
	Integer countUserRoleByRoleId(Long roleId);

	void deleteByUserId(Long userId);

	Integer deleteByUserIdIn(List<Long> userIds);

	List<SysUserRole> findByUserId(Long userId);

}
