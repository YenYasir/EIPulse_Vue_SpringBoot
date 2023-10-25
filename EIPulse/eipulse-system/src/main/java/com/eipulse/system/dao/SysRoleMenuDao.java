package com.eipulse.system.dao;

import org.springframework.stereotype.Repository;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.utils.ConvertUtil;
import com.eipulse.system.domain.SysRoleMenu;
import com.eipulse.system.domain.groupkey.SysRoleMenuKey;

/**
 * 角色與菜單關聯表 數據層
 *
 * @author eipulse
 */
@Repository
public class SysRoleMenuDao extends BaseDao<SysRoleMenu, SysRoleMenuKey> {
	/**
	 * 查詢菜單使用數量
	 *
	 * @param menuId 菜單ID
	 * @return 結果
	 */
	public int checkMenuExistRole(Long menuId) {
		String hql = "select count(1) from SysRoleMenu  where menuId =" + menuId;
		Long total = (Long) this.find(hql).get(0);
		return total.intValue();
	}

	/**
	 * 通過角色ID刪除角色和菜單關聯
	 *
	 * @param roleId 角色ID
	 * @return 結果
	 */
	public int deleteRoleMenuByRoleId(Long roleId) {
		String hql = "delete from SysRoleMenu where roleId=" + roleId;
		return this.executeHql(hql);
	}

	/**
	 * 批量刪除角色菜單關聯資訊
	 *
	 * @param ids 需要刪除的數據ID
	 * @return 結果
	 */
	public int deleteRoleMenu(Long[] ids) {
		String hql = "delete from SysRoleMenu where roleId in " + ConvertUtil.toDbString(ids);
		return this.executeHql(hql);
	}

}
