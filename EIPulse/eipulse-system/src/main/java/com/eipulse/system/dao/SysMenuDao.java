package com.eipulse.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.domain.entity.SysMenu;
import com.eipulse.common.utils.ValidateUtil;

/**
 * 菜單表 數據層
 *
 * @author eipulse
 */
@Repository
public class SysMenuDao extends BaseDao<SysMenu, Long> {
	/**
	 * 查詢系統菜單列表
	 *
	 * @param menu 菜單資訊
	 * @return 菜單列表
	 */
	public List<SysMenu> findMenuList(SysMenu menu) {
		String hql = "from SysMenu where 1=1 ";
		if (!ValidateUtil.isEmpty(menu.getMenuName())) {
			hql += " and menuName like '%" + menu.getMenuName() + "%'";
		}
		if (!ValidateUtil.isEmpty(menu.getStatus())) {
			hql += " and status= " + menu.getStatus();
		}
		if (!ValidateUtil.isEmpty(menu.getVisible())) {
			hql += " and visible= " + menu.getVisible();
		}
		hql += " order by parentId, orderNum";
		return this.find(hql);
	}
	
	/**
	 * 根據員工所有權限
	 *
	 * @return 權限列表
	 */
	public List<String> findMenuPerms() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct m.perms as perms ");
		sql.append(" from sys_menu m ");
		sql.append(" left join sys_role_menu rm on m.menu_id = rm.menu_id ");
		sql.append(" left join sys_user_role ur on rm.role_id = ur.role_id ");
		return this.findByFreeSQL(sql.toString());
	}

	/**
	 * 根據員工查詢系統菜單列表
	 *
	 * @param menu 菜單資訊
	 * @return 菜單列表
	 */
	public List<SysMenu> findMenuListByUserId(SysMenu menu) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  ");
		sql.append(
				"         distinct m.menu_id menuId, m.parent_id parentId, m.menu_name menuName, m.path path, m.component component, m.visible visible, m.status status ");
		sql.append(
				"       , ifnull(m.perms,'') as perms, m.is_frame isFrame, m.is_cache isCache, m.menu_type menuType, m.icon icon, m.order_num orderNum");
		sql.append(
				"       , m.create_by createBy,m.create_time createTime ,m.update_by updateBy,m.update_time updateTime,m.remark remark ");
		sql.append(" FROM sys_menu m ");
		sql.append(" LEFT join sys_role_menu rm on m.menu_id = rm.menu_id ");
		sql.append(" LEFT join sys_user_role ur on rm.role_id = ur.role_id ");
		sql.append(" LEFT join sys_role ro on ur.role_id = ro.role_id ");
		sql.append(" WHERE ur.user_id =");
		sql.append(menu.getParams().get("userId"));
		if (!ValidateUtil.isEmpty(menu.getMenuName())) {
			sql.append(" AND menu_name like '%");
			sql.append(menu.getMenuName());
			sql.append("%'");
		}
		if (!ValidateUtil.isEmpty(menu.getVisible())) {
			sql.append(" AND visible ='");
			sql.append(menu.getVisible());
			sql.append("'");
		}
		if (!ValidateUtil.isEmpty(menu.getStatus())) {
			sql.append(" AND status ='");
			sql.append(menu.getStatus());
			sql.append("'");
		}
		sql.append(" order by m.parent_id, m.order_num ");
		return this.findBySQL(sql.toString());
	}

	/**
	 * 根據員工ID查詢權限
	 *
	 * @param userId 員工ID
	 * @return 權限列表
	 */
	public List<String> findMenuPermsByUserId(Long userId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct m.perms as perms ");
		sql.append(" from sys_menu m ");
		sql.append(" left join sys_role_menu rm on m.menu_id = rm.menu_id ");
		sql.append(" left join sys_user_role ur on rm.role_id = ur.role_id ");
		sql.append(" left join sys_role r on r.role_id = ur.role_id ");
		sql.append(" where m.status = '0' and r.status = '0' and ur.user_id =");
		sql.append(userId);
		return this.findByFreeSQL(sql.toString());
	}

	/**
	 * 根據員工ID查詢菜單
	 *
	 * @return 菜單列表
	 */
	public List<SysMenu> findMenuTreeAll() {
		String hql = "from SysMenu where status=0 and menuType in('M','C') ";
		hql += " order by parentId, orderNum";
		return this.find(hql);
	}

	/**
	 * 根據員工ID查詢菜單
	 *
	 * @param userId 員工ID
	 * @return 菜單列表
	 */
	public List<SysMenu> findMenuTreeByUserId(Long userId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				" select distinct m.menu_id menuId, m.parent_id parentId, m.menu_name menuName, m.path path, m.component component, m.visible visible, m.status status");
		sql.append(
				" , ifnull(m.perms,'') as perms, m.is_frame isFrame, m.is_cache isCache, m.menu_type menuType, m.icon icon, m.order_num orderNum ");
		sql.append(
				" , m.create_by createBy,m.create_time createTime ,m.update_by updateBy,m.update_time updateTime,m.remark remark ");
		sql.append(" from sys_menu m ");
		sql.append(" left join sys_role_menu rm on m.menu_id = rm.menu_id ");
		sql.append(" left join sys_user_role ur on rm.role_id = ur.role_id ");
		sql.append(" left join sys_role ro on ur.role_id = ro.role_id ");
		sql.append(" left join sys_user u on ur.user_id = u.user_id ");
		sql.append(" where u.user_id =");
		sql.append(userId);
		sql.append(" and m.menu_type in ('M', 'C') and m.status = 0  AND ro.status = 0");
		sql.append(" order by m.parent_id, m.order_num ");
		return this.findBySQL(sql.toString());
	}

	/**
	 * 根據角色ID查詢菜單樹資訊
	 *
	 * @param roleId            角色ID
	 * @param menuCheckStrictly 菜單樹選擇項是否關聯顯示
	 * @return 選中菜單列表
	 */
	public List<Long> findMenuListByRoleId(Long roleId, boolean menuCheckStrictly) {
		StringBuilder hql = new StringBuilder();
		hql.append(" select m.menuId ");
		hql.append(" from SysMenu m ");
		hql.append(" left join SysRoleMenu rm on m.menuId = rm.menuId ");
		hql.append(" where rm.roleId =").append(roleId);
		if (menuCheckStrictly) {
			hql.append(
					" and m.menuId not in (select m.parentId from SysMenu m inner join SysRoleMenu rm on m.menuId = rm.menuId and rm.roleId =")
					.append(roleId).append(") ");
		}
		hql.append(" order by m.parentId, m.orderNum ");
		return this.find(hql.toString());
	}

	/**
	 * 是否存在菜單子節點
	 *
	 * @param menuId 菜單ID
	 * @return 結果
	 */
	public int hasChildByMenuId(Long menuId) {
		String hql = "select count(1) from SysMenu  where parentId =" + menuId;
		Long total = (Long) this.find(hql).get(0);
		return total.intValue();
	}

	/**
	 * 校驗菜單名稱是否唯一
	 *
	 * @param menuName 菜單名稱
	 * @param parentId 父菜單ID
	 * @return 結果
	 */
	public SysMenu checkMenuNameUnique(String menuName, Long parentId) {
		SysMenu sysMenu = null;
		String hql = "from SysMenu where menuName='" + menuName + "' and parentId=" + parentId;
		List<SysMenu> list = find(hql);
		if (!ValidateUtil.isEmpty(list)) {
			sysMenu = list.get(0);
		}
		return sysMenu;
	}
}
