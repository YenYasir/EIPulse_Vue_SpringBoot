package com.eipulse.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.dao.dialect.FormatUtil;
import com.eipulse.common.core.domain.entity.SysUser;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.utils.ValidateUtil;

/**
 * 員工表 數據層
 *
 * @author liuyj
 */
@Repository
public class SysUserDao extends BaseDao<SysUser, Long> {
	/**
	 * 根據條件分頁查詢員工列表
	 *
	 * @param sysUser 員工資訊
	 * @return 員工資訊集合資訊
	 */
	public Page findUserList(SysUser sysUser) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		String hql = "from SysUser where 1=1 ";
		if (sysUser.getUserId() != null && sysUser.getUserId() != 0) {
			hql += " and userId= " + sysUser.getUserId();
		}
		if (!ValidateUtil.isEmpty(sysUser.getUserName())) {
			hql += " and userName like '%" + sysUser.getUserName() + "%'";
		}
		if (!ValidateUtil.isEmpty(sysUser.getStatus())) {
			hql += " and status= " + sysUser.getStatus();
		}
		if (!ValidateUtil.isEmpty(sysUser.getPhonenumber())) {
			hql += " and phonenumber like '%" + sysUser.getPhonenumber() + "%'";
		}
		if (sysUser.getDeptId() != null && sysUser.getDeptId() != 0) {
			hql += " and deptId= " + sysUser.getDeptId();
		}
		if (!ValidateUtil.isEmpty((String) sysUser.getParams().get("beginTime"))) {
			hql += " and " + sqlUtil.string2ShortDate(sqlUtil.date2String("createTime"));
			hql += " >= " + sqlUtil
					.string2ShortDate(FormatUtil.formatStrForDB((String) sysUser.getParams().get("beginTime")));
		}
		if (!ValidateUtil.isEmpty((String) sysUser.getParams().get("endTime"))) {
			hql += " and " + sqlUtil.string2ShortDate(sqlUtil.date2String("createTime"));
			hql += " <= "
					+ sqlUtil.string2ShortDate(FormatUtil.formatStrForDB((String) sysUser.getParams().get("endTime")));
		}
		return this.findPage(hql, pageDomain.getPageNum(), pageDomain.getPageSize().intValue());
	}

	/**
	 * 根據條件分頁查詢已配員工角色列表
	 *
	 * @param user 員工資訊
	 * @return 員工資訊集合資訊
	 */
	public Page findAllocatedList(SysUser user) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		String sql = "select distinct u.user_id, u.dept_id, u.user_name, u.nick_name, u.email, u.phonenumber, u.status, u.create_time "
				+ " from sys_user u " + " left join sys_dept d on u.dept_id = d.dept_id "
				+ " left join sys_user_role ur on u.user_id = ur.user_id "
				+ " left join sys_role r on r.role_id = ur.role_id " + " where u.del_flag = '0' and r.role_id = "
				+ user.getRoleId();
		if (!ValidateUtil.isEmpty(user.getUserName())) {
			sql += " and u.user_name like '%" + user.getUserName() + "%'";
		}
		if (!ValidateUtil.isEmpty(user.getPhonenumber())) {
			sql += " and u.phonenumber like '%" + user.getPhonenumber() + "%'";
		}
		return this.findPageBySQL(sql, pageDomain.getPageNum(), pageDomain.getPageSize().intValue());
	}

	/**
	 * 根據條件分頁查詢未分配員工角色列表
	 *
	 * @param user 員工資訊
	 * @return 員工資訊集合資訊
	 */
	public Page findUnallocatedList(SysUser user) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		String sql = "select distinct u.user_id as userId, u.dept_id as deptId, u.user_name as userName, u.nick_name as nickName, u.email as email, u.phonenumber as phonenumber, u.status as status, u.create_time as createTime "
				+ " from sys_user u " + " left join sys_dept d on u.dept_id = d.dept_id "
				+ " left join sys_user_role ur on u.user_id = ur.user_id "
				+ " left join sys_role r on r.role_id = ur.role_id " + " where u.del_flag = '0' and (r.role_id != "
				+ user.getRoleId() + " or r.role_id IS NULL)"
				+ " and u.user_id not in (select u.user_id from sys_user u inner join sys_user_role ur on u.user_id = ur.user_id and ur.role_id = "
				+ user.getRoleId() + ")";
		if (!ValidateUtil.isEmpty(user.getUserName())) {
			sql += " and u.user_name like '%" + user.getUserName() + "%'";
		}
		if (!ValidateUtil.isEmpty(user.getPhonenumber())) {
			sql += " and u.phonenumber like '%" + user.getPhonenumber() + "%'";
		}
		return this.findPageBySQL(sql, pageDomain.getPageNum(), pageDomain.getPageSize().intValue());
	}

	/**
	 * 通過員工名查詢員工
	 *
	 * @param userName 員工名
	 * @return 員工對象資訊
	 */
	public SysUser selectUserByUserName(String userName) {
		SysUser sysUser = null;
		String hql = "from SysUser where userName=" + FormatUtil.formatStrForDB(userName);
		List<SysUser> list = this.find(hql);
		if (!ValidateUtil.isEmpty(list)) {
			sysUser = list.get(0);
		}
		return sysUser;
	}

	/**
	 * 修改員工頭像
	 *
	 * @param userName 員工名
	 * @param avatar   頭像地址
	 * @return 結果
	 */
	public int updateUserAvatar(String userName, String avatar) {
		String hql = "update SysUser set avatar=" + FormatUtil.formatStrForDB(avatar) + " where userName="
				+ FormatUtil.formatStrForDB(userName);
		return this.executeHql(hql);
	}

	/**
	 * 重置員工密碼
	 *
	 * @param userName 員工名
	 * @param password 密碼
	 * @return 結果
	 */
	public int resetUserPwd(String userName, String password) {
		String hql = "update SysUser set password=" + FormatUtil.formatStrForDB(password) + " where userName="
				+ FormatUtil.formatStrForDB(userName);
		return this.executeHql(hql);
	}

	/**
	 * 校驗員工名稱是否唯一
	 *
	 * @param userName 員工名稱
	 * @return 結果
	 */
	public int checkUserNameUnique(String userName) {
		String hql = "select count(1) from SysUser  where userName =" + FormatUtil.formatStrForDB(userName);
		Long total = (Long) this.find(hql).get(0);
		return total.intValue();
	}

	/**
	 * 校驗手機號碼是否唯一
	 *
	 * @param phonenumber 手機號碼
	 * @return 結果
	 */
	public SysUser checkPhoneUnique(String phonenumber) {
		SysUser sysUser = null;
		String hql = "from SysUser where phonenumber=?1";
		List<SysUser> list = this.find(hql, phonenumber);
		if (!ValidateUtil.isEmpty(list)) {
			sysUser = list.get(0);
		}
		return sysUser;
	}

	/**
	 * 校驗email是否唯一
	 *
	 * @param email 員工信箱
	 * @return 結果
	 */
	public SysUser checkEmailUnique(String email) {
		SysUser sysUser = null;
		String hql = "from SysUser where email=?1";
		List<SysUser> list = this.find(hql, email);
		if (!ValidateUtil.isEmpty(list)) {
			sysUser = list.get(0);
		}
		return sysUser;
	}
}
