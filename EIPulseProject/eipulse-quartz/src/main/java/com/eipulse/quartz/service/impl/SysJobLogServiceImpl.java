package com.eipulse.quartz.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import com.eipulse.quartz.dao.SysJobLogDao;
import com.eipulse.quartz.domain.SysJobLog;
import com.eipulse.quartz.service.ISysJobLogService;

/**
 * 定時任務調度日誌資訊 服務層
 */
@Service
public class SysJobLogServiceImpl implements ISysJobLogService {

	@Autowired
	private SysJobLogDao jobLogDao;

	/**
	 * 獲取quartz調度器日誌的計劃任務
	 *
	 * @param req 調度日誌資訊
	 * @return 調度任務日誌集合
	 */
	@Override
	public Page<SysJobLog> selectJobLogList(SysJobLog req) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		if (StringUtils.isNotNull(pageDomain.getPageNum()) && StringUtils.isNotNull(pageDomain.getPageSize())) {
			String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
		}
		Pageable pageable = PageRequest.of(pageDomain.getPageNum(), pageDomain.getPageSize(), Sort.Direction.DESC,
				pageDomain.getOrderBy());
		Specification<SysJobLog> example = new Specification<SysJobLog>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<SysJobLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (StringUtils.isNoneBlank(req.getJobName())) {
					Predicate pre = cb.like(root.get("jobName").as(String.class), "%" + req.getJobName() + "%");
					list.add(pre);
				}
				if (StringUtils.isNoneBlank(req.getInvokeTarget())) {
					Predicate pre = cb.like(root.get("invokeTarget").as(String.class),
							"%" + req.getInvokeTarget() + "%");
					list.add(pre);
				}
				if (StringUtils.isNoneBlank(req.getJobGroup())) {
					Predicate pre = cb.equal(root.get("jobGroup").as(String.class), req.getJobGroup());
					list.add(pre);
				}
				if (StringUtils.isNoneBlank(req.getStatus())) {
					Predicate pre = cb.equal(root.get("status").as(String.class), req.getStatus());
					list.add(pre);
				}
				if (null != req.getParams().get("beginTime")) {
					Predicate pre = cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class),
							(Date) req.getParams().get("beginTime"));
					list.add(pre);
				}
				if (null != req.getParams().get("endTime")) {
					Predicate pre = cb.lessThanOrEqualTo(root.get("createTime").as(Date.class),
							(Date) req.getParams().get("endTime"));
					list.add(pre);
				}
				if (list.isEmpty()) {
					return null;
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		Page<SysJobLog> page = jobLogDao.findAll(example, pageable);
		return page;
	}

	/**
	 * 通過調度任務日誌ID查詢調度資訊
	 *
	 * @param jobLogId 調度任務日誌ID
	 * @return 調度任務日誌物件資訊
	 */
	@Override
	public SysJobLog selectJobLogById(Long jobLogId) {
		SysJobLog sysJobLog = jobLogDao.findById(jobLogId).orElse(new SysJobLog());
		return sysJobLog;
	}

	/**
	 * 新增任務日誌
	 *
	 * @param jobLog 調度日誌資訊
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addJobLog(SysJobLog jobLog) {
		jobLogDao.save(jobLog);
	}

	/**
	 * 批次刪除調度日誌資訊
	 *
	 * @param logIds 需要刪除的數據ID
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteJobLogByIds(Long[] logIds) {
		return jobLogDao.deleteByJobLogIdIn(Arrays.asList(logIds));
	}

	/**
	 * 刪除任務日誌
	 *
	 * @param jobId 調度日誌ID
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteJobLogById(Long jobId) {
		jobLogDao.deleteById(jobId);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 清空任務日誌
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void cleanJobLog() {
		jobLogDao.cleanJobLog();
	}
}
