package com.eipulse.system.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;

import com.eipulse.common.core.domain.entity.SysDept;
import com.eipulse.common.core.dto.SysDeptDto;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.bean.BeanUtils;

/**
 * 部門管理 複雜查詢
 */
public class SysDeptDaoCustomImpl implements SysDeptDaoCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Long> findDeptListByRoleId(Long roleId, boolean deptCheckStrictly) {
		Map<Integer, Object> params = new HashMap<>(16);
		Integer paramIdx = 0;
		StringBuilder hql = new StringBuilder();
		hql.append(" select  d.deptId from SysDept d ");
		hql.append(" left join SysRoleDept rd on d.deptId = rd.deptId ");
		hql.append(" where rd.roleId = ?").append(++paramIdx);
		params.put(paramIdx, roleId);
		if (deptCheckStrictly) {
			hql.append(
					" and d.deptId not in (select d.parentId from SysDept d inner join SysRoleDept rd on d.deptId = rd.deptId and rd.roleId = ?")
					.append(++paramIdx).append(" )");
			params.put(paramIdx, roleId);
		}
		hql.append(" order by d.parentId desc, d.orderNum desc ");

		Query contentQuery = entityManager.createQuery(hql.toString());
		params.forEach((p, v) -> contentQuery.setParameter(p, v));

		List<Long> dataList = contentQuery.getResultList();
		return dataList;
	}

	@Override
	public List<SysDept> findDeptList(SysDept req) {
		List<Object> params = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append(" select ");
		sql.append(" dept_id deptId, ");
		sql.append(" parent_id parentId, ");
		sql.append(" ancestors ancestors, ");
		sql.append(" dept_name deptName, ");
		sql.append(" order_num orderNum, ");
		sql.append(" leader leader, ");
		sql.append(" Phone phone, ");
		sql.append(" Email email, ");
		sql.append(" status status, ");
		sql.append(" del_flag delFlag, ");
		sql.append(" create_by createBy, ");
		sql.append(" create_time createTime, ");
		sql.append(" update_by updateBy, ");
		sql.append(" update_time updateTime ");
		sql.append(" from sys_dept d");
		sql.append(" where 1=1 ");
		if (StringUtils.isNotBlank(req.getDeptName())) {
			sql.append(" and dept_name like ? ");
			params.add("%" + req.getDeptName() + "%");
		}
		if (null != req.getParentId()) {
			sql.append(" and parent_id = ? ");
			params.add(req.getParentId());
		}
		if (StringUtils.isNoneBlank(req.getStatus())) {
			sql.append(" and status = ? ");
			params.add(req.getStatus());
		}
		sql.append(req.getParams().getOrDefault("dataScope", ""));

		Query contentQuery = entityManager.createNativeQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			contentQuery.setParameter(i + 1, params.get(i));
		}
		contentQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SysDeptDto.class));
		List<SysDeptDto> results = contentQuery.getResultList();
		List<SysDept> collect = results.stream().map(s -> {
			SysDept sysDept = new SysDept();
			BeanUtils.copyProperties(s, sysDept);
			sysDept.setDeptId(s.getDeptId().longValue());
			sysDept.setParentId(s.getParentId().longValue());
			sysDept.setStatus(s.getStatus().toString());
			sysDept.setDelFlag(s.getDelFlag().toString());
			return sysDept;
		}).collect(Collectors.toList());
		return collect;
	}
}
