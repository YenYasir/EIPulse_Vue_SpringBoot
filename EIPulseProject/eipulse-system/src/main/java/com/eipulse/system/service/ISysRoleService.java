package com.eipulse.system.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.eipulse.common.core.domain.entity.SysRole;

/**
 * 角色業務層
 */
public interface ISysRoleService {
	/**
	 * 根據條件分頁查詢角色數據
	 *
	 * @param role 角色資訊
	 * @return 角色數據集合資訊
	 */
	Page<SysRole> findPage(SysRole role);

	List<SysRole> selectRoleList(SysRole role);

	/**
	 * 根據員工ID查詢角色
	 *
	 * @param userId 員工ID
	 * @return 權限列表
	 */
	Set<String> selectRolePermissionByUserId(Long userId);

	/**
	 * 查詢所有角色
	 *
	 * @return 角色列表
	 */
	List<SysRole> selectRoleAll();

	/**
	 * 根據員工ID獲取角色選擇框列表
	 *
	 * @param userId 員工ID
	 * @return 選中角色ID列表
	 */
	List<Long> selectRoleListByUserId(Long userId);

	/**
	 * 通過角色ID查詢角色
	 *
	 * @param roleId 角色ID
	 * @return 角色物件資訊
	 */
	SysRole selectRoleById(Long roleId);

	/**
	 * 校驗角色名稱是否唯一
	 *
	 * @param role 角色資訊
	 * @return 結果
	 */
	String checkRoleNameUnique(SysRole role);

	/**
	 * 校驗角色權限是否唯一
	 *
	 * @param role 角色資訊
	 * @return 結果
	 */
	String checkRoleKeyUnique(SysRole role);

	/**
	 * 校驗角色是否允許操作
	 *
	 * @param role 角色資訊
	 */
	void checkRoleAllowed(SysRole role);

	/**
	 * 透過角色ID查詢角色使用數量
	 *
	 * @param roleId 角色ID
	 * @return 結果
	 */
	int countUserRoleByRoleId(Long roleId);

	/**
	 * 新增保存角色資訊
	 *
	 * @param role 角色資訊
	 * @return 結果
	 */
	int insertRole(SysRole role);

	/**
	 * 修改保存角色資訊
	 *
	 * @param role 角色資訊
	 * @return 結果
	 */
	int updateRole(SysRole role);

	/**
	 * 修改角色狀態
	 *
	 * @param role 角色資訊
	 * @return 結果
	 */
	int updateRoleStatus(SysRole role);

	/**
	 * 修改數據權限資訊
	 *
	 * @param role 角色資訊
	 * @return 結果
	 */
	int authDataScope(SysRole role);

	/**
	 * 通過角色ID刪除角色
	 *
	 * @param roleId 角色ID
	 * @return 結果
	 */
	int deleteRoleById(Long roleId);

	/**
	 * 批次刪除角色資訊
	 *
	 * @param roleIds 需要刪除的角色ID
	 * @return 結果
	 */
	int deleteRoleByIds(Long[] roleIds);
}
