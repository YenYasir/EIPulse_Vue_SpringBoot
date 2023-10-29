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

import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.code.BusinessBizCode;
import com.eipulse.common.utils.sql.SqlUtil;
import com.eipulse.system.dao.SysNoticeDao;
import com.eipulse.system.domain.SysNotice;
import com.eipulse.system.service.ISysNoticeService;

/**
 * 公告 服務層實現
 */
@Transactional(readOnly = true)
@Service
public class SysNoticeServiceImpl implements ISysNoticeService {

	@Autowired
	private SysNoticeDao noticeDao;

	/**
	 * 查詢公告資訊
	 *
	 * @param noticeId 公告ID
	 * @return 公告資訊
	 */
	@Override
	public SysNotice selectNoticeById(Long noticeId) {
		return noticeDao.findById(noticeId).orElse(new SysNotice());
	}

	/**
	 * 查詢公告列表
	 *
	 * @param req 公告資訊
	 * @return 公告集合
	 */
	@Override
	public Page<SysNotice> selectNoticeList(SysNotice req) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		if (StringUtils.isNotNull(pageDomain.getPageNum()) && StringUtils.isNotNull(pageDomain.getPageSize())) {
			String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
		}
		Specification<SysNotice> example = new Specification<SysNotice>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<SysNotice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (StringUtils.isNoneBlank(req.getNoticeTitle())) {
					Predicate pre = cb.like(root.get("noticeTitle").as(String.class), "%" + req.getNoticeTitle() + "%");
					list.add(pre);
				}
				if (StringUtils.isNoneBlank(req.getNoticeType())) {
					Predicate pre = cb.equal(root.get("noticeType").as(String.class), req.getNoticeType());
					list.add(pre);
				}
				if (StringUtils.isNoneBlank(req.getCreateBy())) {
					Predicate pre = cb.like(root.get("createBy").as(String.class), "%" + req.getCreateBy() + "%");
					list.add(pre);
				}
				if (list.isEmpty()) {
					return null;
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		Pageable pageable = PageRequest.of(pageDomain.getPageNo(), pageDomain.getPageSize(), Sort.Direction.DESC,
				Optional.ofNullable(pageDomain.getOrderByColumn()).orElse("createTime"));
		Page<SysNotice> page = noticeDao.findAll(example, pageable);
		return page;
	}

	/**
	 * 新增公告
	 *
	 * @param notice 公告資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int insertNotice(SysNotice notice) {
		notice.setCreateTime(new Date());
		noticeDao.save(notice);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 修改公告
	 *
	 * @param notice 公告資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateNotice(SysNotice notice) {
		noticeDao.save(notice);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 刪除公告物件
	 *
	 * @param noticeId 公告ID
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteNoticeById(Long noticeId) {
		noticeDao.deleteById(noticeId);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 批次刪除公告資訊
	 *
	 * @param noticeIds 需要刪除的公告ID
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteNoticeByIds(Long[] noticeIds) {
		noticeDao.deleteByNoticeIdIn(Arrays.asList(noticeIds));
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}
}
