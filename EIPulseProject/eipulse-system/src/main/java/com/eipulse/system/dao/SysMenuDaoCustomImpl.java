package com.eipulse.system.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.eipulse.common.core.domain.entity.SysMenu;
import com.eipulse.common.core.domain.entity.SysRole;
import com.eipulse.common.core.dto.SysMenuDto;
import com.eipulse.common.core.dto.SysRoleDto;
import com.eipulse.common.utils.bean.BeanUtils;

/**
 * 菜單表 複雜查詢
 */
public class SysMenuDaoCustomImpl implements SysMenuDaoCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<SysMenu> findMenuListByUserId(SysMenu menu) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
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
		sql.append(" WHERE ur.user_id = ?  ");
		params.add(menu.getParams().get("userId"));
		if (StringUtils.isNotBlank(menu.getMenuName())) {
			sql.append(" AND menu_name like ? ");
			params.add("%" + menu.getMenuName() + "%");
		}
		if (StringUtils.isNotBlank(menu.getVisible())) {
			sql.append(" AND visible = ? ");
			params.add(menu.getVisible());
		}
		if (StringUtils.isNotBlank(menu.getStatus())) {
			sql.append(" AND status = ? ");
			params.add(menu.getStatus());
		}
		sql.append(" order by m.parent_id, m.order_num ");
		Query contentQuery = entityManager.createNativeQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			contentQuery.setParameter(i + 1, params.get(i));
		}
		contentQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SysMenuDto.class));
		List<SysMenuDto> results = contentQuery.getResultList();
		List<SysMenu> collect = results.stream().map(m -> newMenu(m)).collect(Collectors.toList());
		return collect;
	}

	private SysMenu newMenu(SysMenuDto m) {
		SysMenu sysMenu = new SysMenu();
		BeanUtils.copyProperties(m, sysMenu);
		sysMenu.setMenuId(m.getMenuId().longValue());
		sysMenu.setParentId(m.getParentId().longValue());
		sysMenu.setVisible(m.getVisible().toString());
		sysMenu.setStatus(m.getStatus().toString());
		sysMenu.setMenuType(m.getMenuType().toString());
		return sysMenu;
	}

	@Override
	public List<String> findMenuPermsByUserId(Long userId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		sql.append(" select distinct m.perms as perms ");
		sql.append(" from sys_menu m ");
		sql.append(" left join sys_role_menu rm on m.menu_id = rm.menu_id ");
		sql.append(" left join sys_user_role ur on rm.role_id = ur.role_id ");
		sql.append(" left join sys_role r on r.role_id = ur.role_id ");
		sql.append(" where m.status = '0' and r.status = '0' and ur.user_id = ?  ");
		params.add(userId);
		Query contentQuery = entityManager.createNativeQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			contentQuery.setParameter(i + 1, params.get(i));
		}
		List<String> results = contentQuery.getResultList();
		return results;
	}

	@Override
	public List<Long> findMenuListByRoleId(Long roleId, boolean menuCheckStrictly) {
		Map<Integer, Object> params = new HashMap<>(16);
		Integer paramIdx = 0;
		StringBuilder hql = new StringBuilder();
		hql.append(" select m.menuId ");
		hql.append(" from SysMenu m ");
		hql.append(" left join SysRoleMenu rm on m.menuId = rm.menuId ");
		hql.append(" where rm.roleId = ?").append(++paramIdx);
		params.put(paramIdx, roleId);
		if (menuCheckStrictly) {
			hql.append(
					" and m.menuId not in (select m.parentId from SysMenu m inner join SysRoleMenu rm on m.menuId = rm.menuId and rm.roleId = ?")
					.append(++paramIdx).append(") ");
			params.put(paramIdx, roleId);
		}
		hql.append(" order by m.parentId, m.orderNum ");
		Query contentQuery = entityManager.createQuery(hql.toString());
		params.forEach((p, v) -> contentQuery.setParameter(p, v));
		List<Long> content = contentQuery.getResultList();
		return content;
	}

	@Override
	public Page<SysRole> findMixPage(SysRole req, Pageable pageable) {
		ArrayList<Object> params = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		buildFrom(sql);
		buildWhere(sql, req, params);
		Query totalQuery = entityManager.createNativeQuery("select count(*) " + sql.toString());
		int size = params.size();
		for (int i = 0; i < size; i++) {
			totalQuery.setParameter(i + 1, params.get(i));
		}

		List<?> resultList = totalQuery.getResultList();
		if (resultList.isEmpty()) {
			return Page.empty();
		}
		BigInteger total = (BigInteger) resultList.get(0);
		Query contentQuery = entityManager.createNativeQuery(buildSelect() + sql.toString() + buildOrder());
		for (int i = 0; i < size; i++) {
			contentQuery.setParameter(i + 1, params.get(i));
		}
		contentQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SysRoleDto.class));
		contentQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		contentQuery.setMaxResults(pageable.getPageSize());
		return new PageImpl<>(format(contentQuery.getResultList()), pageable, total.intValue());
	}

	@Override
	public List<SysRole> findRolePermissionByUserId(Long userId) {
		List<Object> params = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append(" select ");
		sql.append(" distinct r.role_id roleId, r.role_name roleName, r.role_key roleKey, r.role_sort roleSort, ");
		sql.append(
				" r.data_scope dataScope, r.menu_check_strictly menuCheckStrictly, r.dept_check_strictly deptCheckStrictly, ");
		sql.append(
				" r.status status, r.del_flag delFlag, r.create_time createTime, r.remark remark,r.create_by createBy,r.update_by updateBy,r.update_time updateTime ");
		sql.append(" from sys_role r ");
		sql.append(" left join sys_user_role ur on ur.role_id = r.role_id ");
		sql.append(" left join sys_user u on u.user_id = ur.user_id ");
		sql.append(" left join sys_dept d on u.dept_id = d.dept_id ");
		sql.append(" WHERE r.del_flag = '0' and ur.user_id = ? ");
		params.add(userId);
		Query contentQuery = entityManager.createNativeQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			contentQuery.setParameter(i + 1, params.get(i));
		}
		contentQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SysRoleDto.class));
		List<SysRoleDto> results = contentQuery.getResultList();
		return format(results);
	}

	private List<SysRole> format(List<SysRoleDto> results) {
		List<SysRole> collect = results.stream().map(r -> {
			SysRole sysRole = new SysRole();
			BeanUtils.copyProperties(r, sysRole);
			sysRole.setRoleId(r.getRoleId().longValue());
			sysRole.setRoleSort(r.getRoleSort().toString());
			sysRole.setStatus(r.getStatus().toString());
			sysRole.setDelFlag(r.getDelFlag().toString());
			return sysRole;
		}).collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<SysMenu> findMenuTreeAll() {
		List<Object> params = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append(" select ");
		sql.append(
				" distinct m.menu_id menuId, m.parent_id parentId, m.menu_name menuName, m.path path, m.component component, m.visible visible, m.status status ");
		sql.append(
				" , ifnull(m.perms,'') as perms, m.is_frame isFrame, m.is_cache isCache, m.menu_type menuType, m.icon icon, m.order_num orderNum ");
		sql.append(
				" , m.create_by createBy,m.create_time createTime ,m.update_by updateBy,m.update_time updateTime,m.remark remark ");
		sql.append(" from sys_menu m ");
		sql.append(" where m.menu_type in ('M', 'C') and m.status = 0 ");
		sql.append(" order by m.parent_id, m.order_num ");
		Query contentQuery = entityManager.createNativeQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			contentQuery.setParameter(i + 1, params.get(i));
		}
		contentQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SysMenuDto.class));
		List<SysMenuDto> results = contentQuery.getResultList();
		List<SysMenu> collect = results.stream().map(m -> newMenu(m)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<SysMenu> findMenuTreeByUserId(Long userId) {
		List<Object> params = new ArrayList<>();
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
		sql.append(" where u.user_id = ? and m.menu_type in ('M', 'C') and m.status = 0  AND ro.status = 0 ");
		sql.append(" order by m.parent_id, m.order_num ");
		params.add(userId);
		Query contentQuery = entityManager.createNativeQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			contentQuery.setParameter(i + 1, params.get(i));
		}
		contentQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SysMenuDto.class));
		List<SysMenuDto> results = contentQuery.getResultList();
		List<SysMenu> collect = results.stream().map(m -> newMenu(m)).collect(Collectors.toList());
		return collect;
	}

	private String buildSelect() {
		StringBuffer sql = new StringBuffer();
		sql.append(
				" select distinct r.role_id roleId, r.role_name roleName, r.role_key roleKey, r.role_sort roleSort, r.data_scope dataScope ");
		sql.append(
				" , r.menu_check_strictly menuCheckStrictly, r.dept_check_strictly deptCheckStrictly, r.status status, r.del_flag delFlag ");
		sql.append(
				" , r.create_by createBy,r.create_time createTime ,r.update_by updateBy,r.update_time updateTime,r.remark remark ");
		return sql.toString();
	}

	private void buildFrom(StringBuilder sql) {
		sql.append(" from sys_role r  ");
		sql.append(" left join sys_user_role ur on ur.role_id = r.role_id  ");
		sql.append(" left join sys_user u on u.user_id = ur.user_id  ");
		sql.append(" left join sys_dept d on u.dept_id = d.dept_id  ");
	}

	private void buildWhere(StringBuilder sql, SysRole req, List<Object> params) {
		sql.append("  where r.del_flag = '0'  ");
		if (StringUtils.isNotBlank(req.getRoleName())) {
			sql.append("   AND r.role_name like ? ");
			params.add("%" + req.getRoleName() + "%");
		}
		if (StringUtils.isNotBlank(req.getStatus())) {
			sql.append("   AND r.status = ? ");
			params.add(req.getStatus());
		}
		if (StringUtils.isNotBlank(req.getRoleKey())) {
			sql.append("   AND r.role_key like ? ");
			params.add("%" + req.getRoleKey() + "%");
		}
		if (null != req.getParams().get("beginTime")) {
			sql.append("   AND date_format(r.create_time,'%y%m%d') >= date_format(?,'%y%m%d') ");
			params.add(req.getParams().get("beginTime"));
		}
		if (null != req.getParams().get("endTime")) {
			sql.append("   AND date_format(r.create_time,'%y%m%d') <= date_format(?,'%y%m%d') ");
			params.add(req.getParams().get("endTime"));
		}
		sql.append(req.getParams().getOrDefault("dataScope", ""));

	}

	private String buildOrder() {
		return " order by r.role_sort ";
	}

}
