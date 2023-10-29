package com.eipulse.system.dao;

import java.util.List;

import com.eipulse.common.core.domain.entity.SysDept;

/**
 * 部門管理 數據層
 */
public interface SysDeptDaoCustom {

	/**
	 * @param roleId
	 * @param deptCheckStrictly 部門樹選擇項是否關聯顯示（0：父子不互相關聯顯示 1：父子互相關聯顯示 ）
	 * @return
	 */
	List<Long> findDeptListByRoleId(Long roleId, boolean deptCheckStrictly);

	List<SysDept> findDeptList(SysDept req);

}
