package com.eipulse.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.dao.dialect.FormatUtil;
import com.eipulse.common.core.domain.entity.SysRole;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.utils.ConvertUtil;
import com.eipulse.common.utils.ValidateUtil;

/**
 * 角色表 數據層
 *
 * @author eipulse
 */
@Repository
public class SysRoleDao extends BaseDao<SysRole, Long> {
	/**
	 * 根據條件分頁查詢角色數據
	 *
	 * @param role 角色資訊
	 * @return 角色數據集合資訊
	 */
	public Page findRoleList(SysRole role) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		String hql = "from SysRole where 1=1 ";
		if (role.getRoleId() != null && role.getRoleId() != 0) {
			hql += " and roleId= " + role.getRoleId();
		}
		if (!ValidateUtil.isEmpty(role.getRoleName())) {
			hql += " and roleName like '%" + role.getRoleName() + "%'";
		}
		if (!ValidateUtil.isEmpty(role.getStatus())) {
			hql += " and status= '" + role.getStatus() + "'";
		}
		if (!ValidateUtil.isEmpty(role.getRoleKey())) {
			hql += " and roleKey like '%" + role.getRoleKey() + "%'";
		}
		if (!ValidateUtil.isEmpty((String) role.getParams().get("beginTime"))) {
			hql += " and " + sqlUtil.string2ShortDate(sqlUtil.date2String("createTime"));
			hql += " >= "
					+ sqlUtil.string2ShortDate(FormatUtil.formatStrForDB((String) role.getParams().get("beginTime")));
		}
		if (!ValidateUtil.isEmpty((String) role.getParams().get("endTime"))) {
			hql += " and " + sqlUtil.string2ShortDate(sqlUtil.date2String("createTime"));
			hql += " <= "
					+ sqlUtil.string2ShortDate(FormatUtil.formatStrForDB((String) role.getParams().get("endTime")));
		}
		hql += " order by roleSort";
		return this.findPage(hql, pageDomain.getPageNum(), pageDomain.getPageSize().intValue());
	}

	/**
	 * 根據用戶ID查詢角色
	 *
	 * @param userId 用戶ID
	 * @return 角色列表
	 */
	public List<SysRole> findRolePermissionByUserId(Long userId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select ");
		sql.append(" distinct r.role_id , r.role_name , r.role_key , r.role_sort , ");
		sql.append(" r.data_scope , r.menu_check_strictly , r.dept_check_strictly , ");
		sql.append(" r.status , r.del_flag , r.create_time , r.remark ,r.create_by ,r.update_by ,r.update_time  ");
		sql.append(" from sys_role r ");
		sql.append(" left join sys_user_role ur on ur.role_id = r.role_id ");
		sql.append(" left join sys_user u on u.user_id = ur.user_id ");
		sql.append(" left join sys_dept d on u.dept_id = d.dept_id ");
		sql.append(" WHERE r.del_flag = '0' and ur.user_id = ");
		sql.append(userId);
		return findBySQL(sql.toString());
	}

	/**
	 * 查詢所有角色
	 *
	 * @return 角色列表
	 */
	public List<SysRole> findRoleAll() {
		String hql = "from SysRole where delFlag='0'";
		return find(hql);
	}

	/**
	 * 根據用戶ID獲取角色選擇框列表
	 *
	 * @param userId 用戶ID
	 * @return 選中角色ID列表
	 */
	public List<Long> findRoleListByUserId(Long userId) {
		String sql = " select r.role_id    " + " from sys_role r  "
				+ " left join sys_user_role ur on ur.role_id = r.role_id   "
				+ " left join sys_user u on u.user_id = ur.user_id  " + " where u.user_id =  " + userId;
		return this.findByFreeSQL(sql);
	}

	/**
	 * 根據用戶ID查詢角色
	 *
	 * @param userName 用戶名
	 * @return 角色列表
	 */
	public List<SysRole> findRolesByUserName(String userName) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select ");
		/**
		 * sql.append(" distinct r.role_id roleId, r.role_name roleName, r.role_key
		 * roleKey, r.role_sort roleSort, "); sql.append(" r.data_scope dataScope,
		 * r.menu_check_strictly menuCheckStrictly, r.dept_check_strictly
		 * deptCheckStrictly, "); sql.append(" r.status status, r.del_flag
		 * delFlag,r.create_by createBy,r.create_time createTime, r.remark
		 * remark,r.update_by updateBy,r.update_time updateTime ");
		 */
		sql.append(" distinct r.role_id,r.role_name,r.role_key,r.role_sort, ");
		sql.append(" r.data_scope,r.menu_check_strictly,r.dept_check_strictly, ");
		sql.append(" r.status,r.del_flag,r.create_by,r.create_time, r.remark,r.update_by,r.update_time ");
		sql.append(" from sys_role r ");
		sql.append(" left join sys_user_role ur on ur.role_id = r.role_id ");
		sql.append(" left join sys_user u on u.user_id = ur.user_id ");
		sql.append(" left join sys_dept d on u.dept_id = d.dept_id ");
		sql.append(" WHERE r.del_flag = '0' and u.user_name  = '");
		sql.append(userName);
		sql.append("'");
		return this.findByFreeSQL(sql.toString(), SysRole.class);
	}

	/**
	 * 校驗角色名稱是否唯一
	 *
	 * @param roleName 角色名稱
	 * @return 角色資訊
	 */
	public SysRole checkRoleNameUnique(String roleName) {
		SysRole sysRole = null;
		String hql = "from SysRole where roleName='" + roleName + "'";
		List<SysRole> list = this.find(hql);
		if (!ValidateUtil.isEmpty(list)) {
			sysRole = list.get(0);
		}
		return sysRole;
	}

	/**
	 * 校驗角色權限是否唯一
	 *
	 * @param roleKey 角色權限
	 * @return 角色資訊
	 */
	public SysRole checkRoleKeyUnique(String roleKey) {
		SysRole sysRole = null;
		String hql = "from SysRole where roleKey='" + roleKey + "'";
		List<SysRole> list = this.find(hql);
		if (!ValidateUtil.isEmpty(list)) {
			sysRole = list.get(0);
		}
		return sysRole;
	}

	/**
	 * 根據角色ID數組查詢角色
	 *
	 * @param roleIds 角色ID數組
	 * @return 角色列表
	 */
	public List<SysRole> findByRoleIdIn(Long[] roleIds) {
		String hql = "from SysRole where roleId in " + ConvertUtil.toDbString(roleIds);
		return this.find(hql);
	}

}
