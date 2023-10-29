package com.eipulse.system.service;

import java.util.List;

import com.eipulse.common.core.domain.TreeSelect;
import com.eipulse.common.core.domain.entity.SysDept;

/**
 * 部門管理 服務層
 */
public interface ISysDeptService {
	/**
	 * 查詢部門管理數據
	 *
	 * @param dept 部門資訊
	 * @return 部門資訊集合
	 */
	List<SysDept> selectDeptList(SysDept dept);

	/**
	 * 構建前端所需要樹結構
	 *
	 * @param depts 部門列表
	 * @return 樹結構列表
	 */
	List<SysDept> buildDeptTree(List<SysDept> depts);

	/**
	 * 構建前端所需要下拉樹結構
	 *
	 * @param depts 部門列表
	 * @return 下拉樹結構列表
	 */
	List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts);

	/**
	 * 根據角色ID查詢部門樹資訊
	 *
	 * @param roleId 角色ID
	 * @return 選中部門列表
	 */
	List<Long> selectDeptListByRoleId(Long roleId);

	/**
	 * 根據部門ID查詢資訊
	 *
	 * @param deptId 部門ID
	 * @return 部門資訊
	 */
	SysDept selectDeptById(Long deptId);

	/**
	 * 根據ID查詢所有子部門（正常狀態）
	 *
	 * @param deptId 部門ID
	 * @return 子部門數
	 */
	int selectNormalChildrenDeptById(Long deptId);

	/**
	 * 是否存在部門子節點
	 *
	 * @param deptId 部門ID
	 * @return 結果
	 */
	boolean hasChildByDeptId(Long deptId);

	/**
	 * 查詢部門是否存在員工
	 *
	 * @param deptId 部門ID
	 * @return 結果 true 存在 false 不存在
	 */
	boolean checkDeptExistUser(Long deptId);

	/**
	 * 校驗部門名稱是否唯一
	 *
	 * @param dept 部門資訊
	 * @return 結果
	 */
	String checkDeptNameUnique(SysDept dept);

	/**
	 * 新增保存部門資訊
	 *
	 * @param dept 部門資訊
	 * @return 結果
	 */
	int insertDept(SysDept dept);

	/**
	 * 修改保存部門資訊
	 *
	 * @param dept 部門資訊
	 * @return 結果
	 */
	int updateDept(SysDept dept);

	/**
	 * 刪除部門管理資訊
	 *
	 * @param deptId 部門ID
	 * @return 結果
	 */
	int deleteDeptById(Long deptId);
}
