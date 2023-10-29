package com.eipulse.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.common.constant.UserConstants;
import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.exception.CustomException;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.code.BusinessBizCode;
import com.eipulse.common.utils.sql.SqlUtil;
import com.eipulse.system.dao.SysPostDao;
import com.eipulse.system.dao.SysUserPostDao;
import com.eipulse.system.domain.SysPost;
import com.eipulse.system.service.ISysPostService;

/**
 * 職位資訊 服務層處理
 */
@Transactional(readOnly = true)
@Service
public class SysPostServiceImpl implements ISysPostService {

	@Autowired
	private SysPostDao postDao;

	@Autowired
	private SysUserPostDao userPostDao;

	/**
	 * 查詢職位資訊集合
	 *
	 * @param req 職位資訊
	 * @return 職位資訊集合
	 */
	@Override
	public Page<SysPost> selectPostList(SysPost req) {
		Specification<SysPost> example = new Specification<SysPost>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<SysPost> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (StringUtils.isNoneBlank(req.getPostCode())) {
					Predicate pre = cb.like(root.get("postCode").as(String.class), "%" + req.getPostCode() + "%");
					list.add(pre);
				}
				if (StringUtils.isNoneBlank(req.getPostName())) {
					Predicate pre = cb.like(root.get("postName").as(String.class), "%" + req.getPostName() + "%");
					list.add(pre);
				}
				if (StringUtils.isNoneBlank(req.getStatus())) {
					Predicate pre = cb.equal(root.get("status").as(String.class), req.getStatus());
					list.add(pre);
				}
				if (list.isEmpty()) {
					return null;
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		PageDomain pageDomain = TableSupport.buildPageRequest();
		if (StringUtils.isNotNull(pageDomain.getPageNum()) && StringUtils.isNotNull(pageDomain.getPageSize())) {
			String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
		}
		Pageable pageable = PageRequest.of(pageDomain.getPageNo(), pageDomain.getPageSize(), Sort.Direction.ASC,
				Optional.ofNullable(pageDomain.getOrderByColumn()).orElse("postSort"));
		Page<SysPost> page = postDao.findAll(example, pageable);
		return page;
	}

	/**
	 * 查詢所有職位
	 *
	 * @return 職位列表
	 */
	@Override
	public List<SysPost> selectPostAll() {
		return postDao.findAll();
	}

	/**
	 * 通過職位ID查詢職位資訊
	 *
	 * @param postId 職位ID
	 * @return 角色物件資訊
	 */
	@Override
	public SysPost selectPostById(Long postId) {
		return postDao.findById(postId).orElse(new SysPost());
	}

	/**
	 * 根據員工ID獲取職位選擇框列表
	 *
	 * @param userId 員工ID
	 * @return 選中職位ID列表
	 */
	@Override
	public List<Long> selectPostListByUserId(Long userId) {
		List<Long> ids = postDao.findPostListByUserId(userId);
		return ids;
	}

	/**
	 * 校驗職位名稱是否唯一
	 *
	 * @param post 職位資訊
	 * @return 結果
	 */
	@Override
	public String checkPostNameUnique(SysPost post) {
		Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
		List<SysPost> posts = postDao.findByPostName(post.getPostName());
		if (!posts.isEmpty() && posts.get(0).getPostId().longValue() != postId.longValue()) {
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
	@Override
	public String checkPostCodeUnique(SysPost post) {
		Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
		List<SysPost> posts = postDao.findByPostCode(post.getPostName());
		if (!posts.isEmpty() && posts.get(0).getPostId().longValue() != postId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 透過職位ID查詢職位使用數量
	 *
	 * @param postId 職位ID
	 * @return 結果
	 */
	@Override
	public int countUserPostById(Long postId) {
		return userPostDao.countUserPostById(postId);
	}

	/**
	 * 刪除職位資訊
	 *
	 * @param postId 職位ID
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deletePostById(Long postId) {
		postDao.deleteById(postId);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 批次刪除職位資訊
	 *
	 * @param postIds 需要刪除的職位ID
	 * @return 結果
	 * @throws Exception 異常
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@Override
	public int deletePostByIds(Long[] postIds) {
		for (Long postId : postIds) {
			SysPost post = selectPostById(postId);
			if (countUserPostById(postId) > 0) {
				throw new CustomException(String.format("%1$s已分配,不能刪除", post.getPostName()));
			}
		}
		postDao.deleteByPostIdIn(Arrays.asList(postIds));
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 新增保存職位資訊
	 *
	 * @param post 職位資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int insertPost(SysPost post) {
		post.setCreateTime(new Date());
		postDao.save(post);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 修改保存職位資訊
	 *
	 * @param post 職位資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updatePost(SysPost post) {
		postDao.save(post);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}
}
