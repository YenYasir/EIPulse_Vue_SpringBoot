package com.eipulse.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
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
import com.eipulse.system.dao.SysOperLogDao;
import com.eipulse.system.domain.SysOperLog;
import com.eipulse.system.service.ISysOperLogService;

/**
 * 操作日誌 服務層處理
 */
@Transactional(readOnly = true)
@Service
public class SysOperLogServiceImpl implements ISysOperLogService {

	@Autowired
	private SysOperLogDao operLogDao;

	/**
	 * 新增操作日誌
	 *
	 * @param operLog 操作日誌物件
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertOperlog(SysOperLog operLog) {
		operLog.setOperTime(new Date());
		operLogDao.save(operLog);
	}

	/**
	 * 查詢系統操作日誌集合
	 *
	 * @param req 操作日誌物件
	 * @return 操作日誌集合
	 */
	@Override
	public Page<SysOperLog> selectOperLogList(SysOperLog req) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		if (StringUtils.isNotNull(pageDomain.getPageNum()) && StringUtils.isNotNull(pageDomain.getPageSize())) {
			String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
		}
		Specification<SysOperLog> example = new Specification<SysOperLog>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<SysOperLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (StringUtils.isNoneBlank(req.getTitle())) {
					Predicate pre = cb.like(root.get("title").as(String.class), "%" + req.getTitle() + "%");
					list.add(pre);
				}
				if (StringUtils.isNoneBlank(req.getOperName())) {
					Predicate pre = cb.like(root.get("operName").as(String.class), "%" + req.getOperName() + "%");
					list.add(pre);
				}
				if (null != req.getBusinessType()) {
					Predicate pre = cb.equal(root.get("businessType").as(Integer.class), req.getBusinessType());
					list.add(pre);
				}
				if (null != req.getBusinessTypes() && req.getBusinessTypes().length > 0) {
					In<Integer> in = cb.in(root.get("businessType"));
					Arrays.asList(req.getBusinessTypes()).forEach(in::value);
					list.add(in);
				}
				if (null != req.getStatus()) {
					Predicate pre = cb.equal(root.get("status").as(Integer.class), req.getStatus());
					list.add(pre);
				}
				if (null != req.getParams().get("beginTime")) {
					Predicate pre = cb.greaterThanOrEqualTo(root.get("operTime").as(Date.class),
							DateUtils.parseDate(req.getParams().get("beginTime")));
					list.add(pre);
				}
				if (null != req.getParams().get("endTime")) {
					Predicate pre = cb.lessThanOrEqualTo(root.get("operTime").as(Date.class),
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
				Optional.ofNullable(pageDomain.getOrderByColumn()).orElse("operTime"));
		Page<SysOperLog> page = operLogDao.findAll(example, pageable);
		return page;
	}

	/**
	 * 批次刪除系統操作日誌
	 *
	 * @param operIds 需要刪除的操作日誌ID
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteOperLogByIds(Long[] operIds) {
		operLogDao.deleteByOperIdIn(Arrays.asList(operIds));
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 查詢操作日誌詳細
	 *
	 * @param operId 操作ID
	 * @return 操作日誌物件
	 */
	@Override
	public SysOperLog selectOperLogById(Long operId) {
		return operLogDao.findById(operId).orElse(new SysOperLog());
	}

	/**
	 * 清空操作日誌
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void cleanOperLog() {
		operLogDao.cleanOperLog();
	}
}
