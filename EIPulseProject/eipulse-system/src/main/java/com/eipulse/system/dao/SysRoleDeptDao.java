package com.eipulse.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eipulse.system.domain.SysRoleDept;
import com.eipulse.system.domain.groupkey.SysRoleDeptKey;

/**
 * 角色與部門關聯表 數據層
 */
@Repository
public interface SysRoleDeptDao extends JpaRepository<SysRoleDept, SysRoleDeptKey> {

	List<SysRoleDept> findByRoleId(Long roleId);

	void deleteByRoleId(Long roleId);

	void deleteByRoleIdIn(List<Long> roleId);

}
