package com.eipulse.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eipulse.common.core.domain.entity.SysRole;

/**
 * 角色表 數據層
 */
@Repository
public interface SysRoleDao extends JpaRepository<SysRole, Long>, JpaSpecificationExecutor<SysRole>, SysMenuDaoCustom {

	SysRole findByRoleId(Long roleId);

	@Query(value = " select r.role_id    " + " from sys_role r  "
			+ " left join sys_user_role ur on ur.role_id = r.role_id   "
			+ " left join sys_user u on u.user_id = ur.user_id  " + " where u.user_id = ? ", nativeQuery = true)
	List<Long> findRoleListByUserId(Long userId);

	List<SysRole> findByRoleName(String roleName);

	List<SysRole> findByRoleKey(String roleKey);

	void deleteByRoleId(Long roleId);

	void deleteByRoleIdIn(List<Long> roleId);

	@Modifying
	@Query(value = "select distinct r.role_id, r.role_name, r.role_key, r.role_sort, r.data_scope, r.menu_check_strictly"
			+ "            , r.dept_check_strictly,r.status, r.del_flag,r.create_by, r.create_time, r.remark,r.update_by,r.update_time "
			+ "        from sys_role r " + "        left join sys_user_role ur on ur.role_id = r.role_id"
			+ "        left join sys_user u on u.user_id = ur.user_id"
			+ "        left join sys_dept d on u.dept_id = d.dept_id"
			+ "       where r.del_flag = '0' and u.user_name =?1  ", nativeQuery = true)
	List<SysRole> findRolesByUserName(String userName);

	List<SysRole> findByRoleIdIn(List<Long> roleIds);
}
