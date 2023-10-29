package com.eipulse.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eipulse.common.core.domain.entity.SysDept;

/**
 * 部門管理 數據層
 */
@Repository
public interface SysDeptDao extends JpaRepository<SysDept, Long>, JpaSpecificationExecutor<SysDept>, SysDeptDaoCustom {

	List<SysDept> findByParentIdAndDeptName(Long parentId, String deptName);

	@Query(value = " select count(1) from sys_dept where status = 0 and del_flag = '0' and find_in_set(?1, ancestors) ", nativeQuery = true)
	Integer findNormalChildrenDeptById(Long deptId);

	@Query(value = " select count(1) from sys_dept where del_flag = '0' and parent_id=?1 limit 1 ", nativeQuery = true)
	Integer hasChildByDeptId(Long deptId);

	@Query(value = " select count(1) from sys_user where dept_id = ?1 and del_flag = '0' ", nativeQuery = true)
	Integer checkDeptExistUser(Long deptId);

	@Query(value = " select * from sys_dept where find_in_set(?1, ancestors) ", nativeQuery = true)
	List<SysDept> findChildrenDeptById(Long deptId);

}
