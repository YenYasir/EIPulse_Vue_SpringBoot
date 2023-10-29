package com.eipulse.system.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * 職位資訊 數據層
 */
public class SysPostDaoCustomImpl implements SysPostDaoCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Long> findPostListByUserId(Long userId) {
		List<Object> params = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append(" select p.post_id ");
		sql.append(" from sys_post p ");
		sql.append(" left join sys_user_post up on up.post_id = p.post_id ");
		sql.append(" left join sys_user u on u.user_id = up.user_id ");
		sql.append(" where u.user_id = ? ");
		params.add(userId);
		Query contentQuery = entityManager.createNativeQuery(sql.toString());
		for (int i = 0; i < params.size(); i++) {
			contentQuery.setParameter(i + 1, params.get(i));
		}
		List<Long> results = contentQuery.getResultList();
		return results;
	}
}
