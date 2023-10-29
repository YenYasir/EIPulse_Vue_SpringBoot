package com.eipulse.system.service;

import java.util.List;
import java.util.Set;

import com.eipulse.common.core.domain.TreeSelect;
import com.eipulse.common.core.domain.entity.SysMenu;
import com.eipulse.system.dto.RouterVo;

/**
 * 菜單 業務層
 */
public interface ISysMenuService {
	/**
	 * 根據員工查詢系統菜單列表
	 *
	 * @param userId 員工ID
	 * @return 菜單列表
	 */
	List<SysMenu> selectMenuList(Long userId);

	/**
	 * 根據員工查詢系統菜單列表
	 *
	 * @param menu   菜單資訊
	 * @param userId 員工ID
	 * @return 菜單列表
	 */
	List<SysMenu> selectMenuList(SysMenu menu, Long userId);

	/**
	 * 根據員工ID查詢權限
	 *
	 * @param userId 員工ID
	 * @return 權限列表
	 */
	Set<String> selectMenuPermsByUserId(Long userId);

	/**
	 * 根據員工ID查詢菜單樹資訊
	 *
	 * @param userId 員工ID
	 * @return 菜單列表
	 */
	List<SysMenu> selectMenuTreeByUserId(Long userId);

	/**
	 * 根據角色ID查詢菜單樹資訊
	 *
	 * @param roleId 角色ID
	 * @return 選中菜單列表
	 */
	List<Long> selectMenuListByRoleId(Long roleId);

	/**
	 * 構建前端路由所需要的菜單
	 *
	 * @param menus 菜單列表
	 * @return 路由列表
	 */
	List<RouterVo> buildMenus(List<SysMenu> menus);

	/**
	 * 構建前端所需要樹結構
	 *
	 * @param menus 菜單列表
	 * @return 樹結構列表
	 */
	List<SysMenu> buildMenuTree(List<SysMenu> menus);

	/**
	 * 構建前端所需要下拉樹結構
	 *
	 * @param menus 菜單列表
	 * @return 下拉樹結構列表
	 */
	List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus);

	/**
	 * 根據菜單ID查詢資訊
	 *
	 * @param menuId 菜單ID
	 * @return 菜單資訊
	 */
	SysMenu selectMenuById(Long menuId);

	/**
	 * 是否存在菜單子節點
	 *
	 * @param menuId 菜單ID
	 * @return 結果 true 存在 false 不存在
	 */
	boolean hasChildByMenuId(Long menuId);

	/**
	 * 查詢菜單是否存在角色
	 *
	 * @param menuId 菜單ID
	 * @return 結果 true 存在 false 不存在
	 */
	boolean checkMenuExistRole(Long menuId);

	/**
	 * 新增保存菜單資訊
	 *
	 * @param menu 菜單資訊
	 * @return 結果
	 */
	int insertMenu(SysMenu menu);

	/**
	 * 修改保存菜單資訊
	 *
	 * @param menu 菜單資訊
	 * @return 結果
	 */
	int updateMenu(SysMenu menu);

	/**
	 * 刪除菜單管理資訊
	 *
	 * @param menuId 菜單ID
	 * @return 結果
	 */
	int deleteMenuById(Long menuId);

	/**
	 * 校驗菜單名稱是否唯一
	 *
	 * @param menu 菜單資訊
	 * @return 結果
	 */
	String checkMenuNameUnique(SysMenu menu);
}
