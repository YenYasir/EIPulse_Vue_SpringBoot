package com.eipulse.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.utils.ConvertUtil;
import com.eipulse.system.domain.SysUserPost;
import com.eipulse.system.domain.groupkey.SysUserPostKey;

/**
 * 員工與職位關聯表 數據層
 *
 * @author eipulse
 */
@Repository
public class SysUserPostDao extends BaseDao<SysUserPost, SysUserPostKey> {
	/**
	 * 通過員工ID刪除員工和職位關聯
	 *
	 * @param userId 員工ID
	 * @return 結果
	 */
	public int deleteUserPostByUserId(Long userId) {
		String hql = "delete from SysUserPost where userId=" + userId;
		return this.executeHql(hql);
	}

	/**
	 * 通過職位ID查詢職位使用數量
	 *
	 * @param postId 職位ID
	 * @return 結果
	 */
	public int countUserPostById(Long postId) {
		String hql = "select count(1) from SysUserPost  where postId =" + postId;
		Long total = (Long) this.find(hql).get(0);
		return total.intValue();
	}

	/**
	 * 批量刪除員工和職位關聯
	 *
	 * @param ids 需要刪除的數據ID
	 * @return 結果
	 */
	public int deleteUserPost(Long[] ids) {
		String hql = "delete from SysUserPost  where userId in " + ConvertUtil.toDbString(ids);
		return this.executeHql(hql);
	}

	public List<SysUserPost> findByUserId(Long userId) {
		String hql = "from SysUserPost where userId=" + userId;
		return this.find(hql);
	}

}
