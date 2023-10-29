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
import com.eipulse.common.utils.DateUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.code.BusinessBizCode;
import com.eipulse.common.utils.sql.SqlUtil;
import com.eipulse.system.dao.SysLogininforDao;
import com.eipulse.system.domain.SysLogininfor;
import com.eipulse.system.service.ISysLogininforService;

/**
 * 系統訪問日誌情況資訊 服務層處理
 */
@Transactional(readOnly = true)
@Service
public class SysLogininforServiceImpl implements ISysLogininforService {

	@Autowired
	private SysLogininforDao logininforDao;

	/**
	 * 新增系統登入日誌
	 *
	 * @param logininfor 訪問日誌物件
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertLogininfor(SysLogininfor logininfor) {
		logininfor.setLoginTime(new Date());
		logininforDao.save(logininfor);
	}

	/**
	 * 查詢系統登入日誌集合
	 *
	 * @param req 訪問日誌物件
	 * @return 登入記錄集合
	 */
	@Override
	public Page<SysLogininfor> selectLogininforList(SysLogininfor req) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		if (StringUtils.isNotNull(pageDomain.getPageNum()) && StringUtils.isNotNull(pageDomain.getPageSize())) {
			String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
		}
		Specification<SysLogininfor> example = new Specification<SysLogininfor>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<SysLogininfor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (StringUtils.isNoneBlank(req.getIpaddr())) {
					Predicate pre = cb.like(root.get("ipaddr").as(String.class), "%" + req.getIpaddr() + "%");
					list.add(pre);
				}
				if (StringUtils.isNoneBlank(req.getStatus())) {
					Predicate pre = cb.equal(root.get("status").as(String.class), req.getStatus());
					list.add(pre);
				}
				if (StringUtils.isNoneBlank(req.getUserName())) {
					Predicate pre = cb.like(root.get("userName").as(String.class), "%" + req.getUserName() + "%");
					list.add(pre);
				}
				if (null != req.getParams().get("beginTime")) {
					Predicate pre = cb.greaterThanOrEqualTo(root.get("loginTime").as(Date.class),
							DateUtils.parseDate(req.getParams().get("beginTime")));
					list.add(pre);
				}
				if (null != req.getParams().get("endTime")) {
					Predicate pre = cb.lessThanOrEqualTo(root.get("loginTime").as(Date.class),
							DateUtils.parseDate(req.getParams().get("endTime")));
					list.add(pre);
				}
				if (list.isEmpty()) {
					return null;
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		Pageable pageable = PageRequest.of(pageDomain.getPageNo(), pageDomain.getPageSize(), Sort.Direction.DESC,
				Optional.ofNullable(pageDomain.getOrderByColumn()).orElse("loginTime"));
		Page<SysLogininfor> page = logininforDao.findAll(example, pageable);
		return page;
	}

	/**
	 * 批次刪除系統登入日誌
	 *
	 * @param infoIds 需要刪除的登入日誌ID
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteLogininforByIds(Long[] infoIds) {
		logininforDao.deleteAllByInfoIdIn(Arrays.asList(infoIds));
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 清空系統登入日誌
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void cleanLogininfor() {
		logininforDao.truncateTable();
	}
}
