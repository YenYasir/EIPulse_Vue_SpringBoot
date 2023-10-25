package com.eipulse.system.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eipulse.common.constant.UserConstants;
import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.service.BaseService;
import com.eipulse.common.exception.ServiceException;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.system.dao.SysPostDao;
import com.eipulse.system.dao.SysUserPostDao;
import com.eipulse.system.domain.SysPost;

/**
 * 職位資訊 服務層處理
 * 
 * @author eipulse
 */
@Service
public class SysPostService extends BaseService<SysPost, Long> {
	@Autowired
	private SysPostDao sysPostDao;

	@Resource
	private SysUserPostDao sysUserPostDao;

	@Override
	protected BaseDao<SysPost, Long> getDao() {
		return sysPostDao;
	}

	/**
	 * 查詢職位資訊集合(分頁)
	 * 
	 * @param post 職位資訊
	 * @return 職位資訊集合
	 */
	public Page findPostList(SysPost post) {
		return sysPostDao.findPostList(post);
	}

	/**
	 * 根據用戶ID獲取職位選擇框列表
	 * 
	 * @param userId 用戶ID
	 * @return 選中職位ID列表
	 */
	public List<Long> findPostListByUserId(Long userId) {
		return sysPostDao.findPostListByUserId(userId);
	}

	/**
	 * 校驗職位名稱是否唯一
	 * 
	 * @param post 職位資訊
	 * @return 結果
	 */
	public String checkPostNameUnique(SysPost post) {
		Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
		SysPost info = sysPostDao.checkPostNameUnique(post.getPostName());
		if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 校驗職位編碼是否唯一
	 * 
	 * @param post 職位資訊
	 * @return 結果
	 */
	public String checkPostCodeUnique(SysPost post) {
		Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
		SysPost info = sysPostDao.checkPostCodeUnique(post.getPostCode());
		if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 通過職位ID查詢職位使用數量
	 * 
	 * @param postId 職位ID
	 * @return 結果
	 */
	public int countUserPostById(Long postId) {
		return sysUserPostDao.countUserPostById(postId);
	}

	/**
	 * 批量刪除職位資訊
	 * 
	 * @param postIds 需要刪除的職位ID
	 * @return 結果
	 * @throws Exception 異常
	 */
	public void deletePostByIds(Long[] postIds) {
		for (Long postId : postIds) {
			SysPost post = (SysPost) getObject(postId);
			if (countUserPostById(postId) > 0) {
				throw new ServiceException(String.format("%1$s已分配,不能刪除", post.getPostName()));
			}
		}
		this.deleteObject(postIds);
	}

}
