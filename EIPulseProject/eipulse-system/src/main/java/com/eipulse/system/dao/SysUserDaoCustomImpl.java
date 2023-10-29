package com.eipulse.system.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

import com.eipulse.common.core.domain.entity.SysDept;
import com.eipulse.common.core.domain.entity.SysUser;
import com.eipulse.common.core.dto.SysUserDto;
import com.eipulse.common.utils.bean.BeanUtils;

/**
 * 員工表 數據層 複雜查詢
 */
public class SysUserDaoCustomImpl implements SysUserDaoCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<SysUser> findMixPage(SysUser req, Pageable pageable) {

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
		contentQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SysUserDto.class));
		contentQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		contentQuery.setMaxResults(pageable.getPageSize());
		List<SysUserDto> result = contentQuery.getResultList();
		List<SysUser> collect = result.stream().map(s -> {
			SysUser sysUser = new SysUser();
			BeanUtils.copyProperties(s, sysUser);
			sysUser.setUserId(s.getUserId().longValue());
			sysUser.setDeptId(s.getDeptId().longValue());
			sysUser.setSex(Optional.ofNullable(s.getSex()).orElse(new Character(' ')).toString());
			sysUser.setDelFlag(s.getDelFlag().toString());
			sysUser.setStatus(s.getStatus().toString());
			SysDept sysDept = new SysDept();
			sysDept.setDeptId(s.getDeptId().longValue());
			sysDept.setDeptName(s.getDeptName());
			sysDept.setLeader(s.getLeader());
			sysUser.setDept(sysDept);
			return sysUser;
		}).collect(Collectors.toList());
		return new PageImpl<>(collect, pageable, total.intValue());
	}

	private String buildSelect() {
		StringBuffer select = new StringBuffer();
		select.append(" select u.user_id userId, ");
		select.append(" u.dept_id deptId, ");
		select.append(" u.nick_name nickName, ");
		select.append(" u.user_name userName, ");
		select.append(" u.email email, ");
		select.append(" u.avatar avatar, ");
		select.append(" u.phonenumber phonenumber, ");
		select.append(" u.password password, ");
		select.append(" u.sex sex, ");
		select.append(" u.status status, ");
		select.append(" u.del_flag delFlag, ");
		select.append(" u.login_ip loginIp, ");
		select.append(" u.login_date loginDate, ");
		select.append(" u.create_by createBy, ");
		select.append(" u.create_time createTime, ");
		select.append(" u.update_time updateTime, ");
		select.append(" u.update_by updateBy, ");
		select.append(" u.remark remark, ");
		select.append(" d.dept_name deptName, ");
		select.append(" d.leader leader ");
		return select.toString();
	}

	private void buildFrom(StringBuilder sql) {
		sql.append(" from sys_user u  ");
		sql.append(" left join sys_dept d on u.dept_id = d.dept_id  ");
	}

	private void buildWhere(StringBuilder sql, SysUser req, List<Object> params) {
		sql.append("  where u.del_flag = '0'  ");
		if (StringUtils.isNotBlank(req.getUserName())) {
			sql.append("   AND u.user_name like ? ");
			params.add("%" + req.getUserName() + "%");
		}
		if (StringUtils.isNotBlank(req.getStatus())) {
			sql.append("   AND u.status = ? ");
			params.add(req.getStatus());
		}
		if (StringUtils.isNotBlank(req.getPhonenumber())) {
			sql.append("   AND u.phonenumber like ? ");
			params.add("%" + req.getPhonenumber() + "%");
		}
		if (null != req.getDeptId()) {
			sql.append(
					"   AND (u.dept_id = ? OR u.dept_id IN ( SELECT t.dept_id FROM sys_dept t WHERE find_in_set(?, ancestors) )) ");
			params.add(req.getDeptId());
			params.add(req.getDeptId());
		}
		if (null != req.getParams().get("beginTime")) {
			sql.append("   AND date_format(u.create_time,'%y%m%d') >= date_format(?,'%y%m%d') ");
			params.add(req.getParams().get("beginTime"));
		}
		if (null != req.getParams().get("endTime")) {
			sql.append("   AND date_format(u.create_time,'%y%m%d') <= date_format(?,'%y%m%d') ");
			params.add(req.getParams().get("endTime"));
		}
		sql.append(req.getParams().getOrDefault("dataScope", ""));
	}

	private String buildOrder() {
		return "  ";
	}
}
