package com.eipulse.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.utils.ConvertUtil;
import com.eipulse.system.domain.SysUserRole;
import com.eipulse.system.domain.groupkey.SysUserRoleKey;

/**
 * 員工與角色關聯表 數據層
 *
 * @author eipulse
 */
@Repository
public class SysUserRoleDao extends BaseDao<SysUserRole, SysUserRoleKey> {
	/**
	 * 通過員工ID刪除員工和角色關聯
	 *
	 * @param userId 員工ID
	 * @return 結果
	 */
	public int deleteUserRoleByUserId(Long userId) {
		String hql = "delete from SysUserRole where userId=" + userId;
		return this.executeHql(hql);
	}

	/**
	 * 批量刪除員工和角色關聯
	 *
	 * @param ids 需要刪除的數據ID
	 * @return 結果
	 */
	public int deleteUserRole(Long[] ids) {
		String hql = "delete from SysUserRole  where userId in " + ConvertUtil.toDbString(ids);
		return this.executeHql(hql);
	}

	/**
	 * 通過角色ID查詢角色使用數量
	 *
	 * @param roleId 角色ID
	 * @return 結果
	 */
	public int countUserRoleByRoleId(Long roleId) {
		String hql = "select count(1) from SysUserRole  where roleId =" + roleId;
		Long total = (Long) this.find(hql).get(0);
		return total.intValue();
	}

	/**
	 * 刪除員工和角色關聯信息
	 *
	 * @param userRole 員工和角色關聯信息
	 * @return 結果
	 */
	public int deleteUserRoleInfo(SysUserRole userRole) {
		String hql = "delete from SysUserRole where userId=" + userRole.getUserId() + " and roleId="
				+ userRole.getRoleId();
		return this.executeHql(hql);
	}

	/**
	 * 批量取消授權員工角色
	 *
	 * @param roleId  角色ID
	 * @param userIds 需要刪除的員工數據ID
	 * @return 結果
	 */
	public int deleteUserRoleInfos(Long roleId, Long[] userIds) {
		String hql = "delete from SysUserRole where roleId=" + roleId + " and userId in "
				+ ConvertUtil.toDbString(userIds);
		return this.executeHql(hql);
	}

	/**
	 * 通過員工ID獲取員工角色列表
	 * 
	 * @param userId 員工ID
	 * @return 員工角色列表
	 */
	public List<SysUserRole> findByUserId(Long userId) {
		String hql = "from SysUserRole where userId=" + userId;
		return this.find(hql);
	}

}
