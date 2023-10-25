package com.eipulse.system.dao;

import org.springframework.stereotype.Repository;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.utils.ConvertUtil;
import com.eipulse.system.domain.SysRoleDept;
import com.eipulse.system.domain.groupkey.SysRoleDeptKey;

/**
 * 角色與部門關聯表 數據層
 *
 * @author eipulse
 */
@Repository
public class SysRoleDeptDao extends BaseDao<SysRoleDept, SysRoleDeptKey> {
	/**
	 * 通過角色ID刪除角色和部門關聯
	 *
	 * @param roleId 角色ID
	 * @return 結果
	 */
	public int deleteRoleDeptByRoleId(Long roleId) {
		String hql = "delete from SysRoleDept where roleId=" + roleId;
		return this.executeHql(hql);
	}

	/**
	 * 批量刪除角色部門關聯資訊
	 *
	 * @param ids 需要刪除的數據ID
	 * @return 結果
	 */
	public int deleteRoleDept(Long[] ids) {
		String hql = "delete from SysRoleDept where roleId in " + ConvertUtil.toDbString(ids);
		return this.executeHql(hql);
	}

	/**
	 * 查詢部門使用數量
	 *
	 * @param deptId 部門ID
	 * @return 結果
	 */
	public int selectCountRoleDeptByDeptId(Long deptId) {
		String hql = "select count(1) from SysRoleDept  where deptId =" + deptId;
		Long total = (Long) this.find(hql).get(0);
		return total.intValue();
	}

}
