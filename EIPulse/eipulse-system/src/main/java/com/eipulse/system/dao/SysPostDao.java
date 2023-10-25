package com.eipulse.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.utils.ValidateUtil;
import com.eipulse.system.domain.SysPost;

/**
 * 職位資訊 數據層
 *
 * @author eipulse
 */
@Repository
public class SysPostDao extends BaseDao<SysPost, Long> {
	/**
	 * 查詢職位數據集合(分頁)
	 *
	 * @param post 職位資訊
	 * @return 職位數據集合
	 */
	public Page findPostList(SysPost post) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		String hql = "from SysPost where 1=1 ";
		if (!ValidateUtil.isEmpty(post.getPostCode())) {
			hql += " and postCode like '%" + post.getPostCode() + "%'";
		}
		if (!ValidateUtil.isEmpty(post.getStatus())) {
			hql += " and status= '" + post.getStatus() + "'";
		}
		if (!ValidateUtil.isEmpty(post.getPostName())) {
			hql += " and postName like '%" + post.getPostName() + "%'";
		}
		return this.findPage(hql, pageDomain.getPageNum(), pageDomain.getPageSize().intValue());
	}

	/**
	 * 根據員工ID獲取職位選擇框列表
	 *
	 * @param userId 員工ID
	 * @return 選中職位ID列表
	 */
	public List<Long> findPostListByUserId(Long userId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select p.post_id ");
		sql.append(" from sys_post p ");
		sql.append(" left join sys_user_post up on up.post_id = p.post_id ");
		sql.append(" left join sys_user u on u.user_id = up.user_id ");
		sql.append(" where u.user_id =  ");
		sql.append(userId);
		return this.findByFreeSQL(sql.toString());
	}

	/**
	 * 查詢員工所屬職位組
	 *
	 * @param userName 員工名
	 * @return 結果
	 */
	public List<SysPost> findPostsByUserName(String userName) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select p.* ");
		sql.append(" from sys_post p ");
		sql.append(" left join sys_user_post up on up.post_id = p.post_id ");
		sql.append(" left join sys_user u on u.user_id = up.user_id ");
		sql.append(" where u.user_name ='");
		sql.append(userName);
		sql.append("'");
		return this.findBySQL(sql.toString());
	}

	/**
	 * 校驗職位名稱
	 *
	 * @param postName 職位名稱
	 * @return 結果
	 */
	public SysPost checkPostNameUnique(String postName) {
		SysPost sysPost = null;
		String hql = "from SysPost where postName=?1";
		List<SysPost> list = find(hql, postName);
		if (!ValidateUtil.isEmpty(list)) {
			sysPost = list.get(0);
		}
		return sysPost;
	}

	/**
	 * 校驗職位編碼
	 *
	 * @param postCode 職位編碼
	 * @return 結果
	 */
	public SysPost checkPostCodeUnique(String postCode) {
		SysPost sysPost = null;
		String hql = "from SysPost where postCode=?1";
		List<SysPost> list = find(hql, postCode);
		if (!ValidateUtil.isEmpty(list)) {
			sysPost = list.get(0);
		}
		return sysPost;
	}
}
