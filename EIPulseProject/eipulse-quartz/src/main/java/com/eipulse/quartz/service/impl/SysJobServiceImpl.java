package com.eipulse.quartz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.common.constant.ScheduleConstants;
import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.exception.job.TaskException;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.code.BusinessBizCode;
import com.eipulse.common.utils.sql.SqlUtil;
import com.eipulse.quartz.dao.SysJobDao;
import com.eipulse.quartz.domain.SysJob;
import com.eipulse.quartz.service.ISysJobService;
import com.eipulse.quartz.util.CronUtils;
import com.eipulse.quartz.util.ScheduleUtils;

/**
 * 定時任務調度資訊 服務層
 */
@Service
public class SysJobServiceImpl implements ISysJobService {

	@Autowired
	private Scheduler scheduler;

	@Autowired
	private SysJobDao jobDao;

	/**
	 * 項目啟動時，初始化定時器 主要是防止手動修改資料庫導致未同步到定時任務處理（註：不能手動修改資料庫ID和任務組名，否則會導致髒數據）
	 */
	@PostConstruct
	public void init() throws SchedulerException, TaskException {
		scheduler.clear();
		List<SysJob> jobList = jobDao.findAll();
		for (SysJob job : jobList) {
			ScheduleUtils.createScheduleJob(scheduler, job);
		}
	}

	/**
	 * 獲取quartz調度器的計劃任務列表
	 *
	 * @param req 調度資訊
	 * @return
	 */
	@Override
	public Page<SysJob> selectJobList(SysJob req) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		if (StringUtils.isNotNull(pageDomain.getPageNum()) && StringUtils.isNotNull(pageDomain.getPageSize())) {
			String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
		}
		Specification<SysJob> example = new Specification<SysJob>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<SysJob> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
				if (list.isEmpty()) {
					return null;
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		Pageable pageable = PageRequest.of(pageDomain.getPageNo(), pageDomain.getPageSize(), Sort.Direction.DESC,
				Optional.ofNullable(pageDomain.getOrderByColumn()).orElse("createTime"));
		Page<SysJob> page = jobDao.findAll(example, pageable);
		return page;
	}

	/**
	 * 通過調度任務ID查詢調度資訊
	 *
	 * @param jobId 調度任務ID
	 * @return 調度任務物件資訊
	 */
	@Override
	public SysJob selectJobById(Long jobId) {
		return jobDao.findById(jobId).orElse(new SysJob());
	}

	/**
	 * 暫停任務
	 *
	 * @param job 調度資訊
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int pauseJob(SysJob job) throws SchedulerException {
		Long jobId = job.getJobId();
		String jobGroup = job.getJobGroup();
		job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
		SysJob save = jobDao.save(job);
		if (null != save) {
			scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
		}
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 恢復任務
	 *
	 * @param job 調度資訊
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int resumeJob(SysJob job) throws SchedulerException {
		Long jobId = job.getJobId();
		String jobGroup = job.getJobGroup();
		job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
		SysJob save = jobDao.save(job);
		if (null != save) {
			scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
		}
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 刪除任務後，所對應的trigger也將被刪除
	 *
	 * @param job 調度資訊
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteJob(SysJob job) throws SchedulerException {
		Long jobId = job.getJobId();
		String jobGroup = job.getJobGroup();
		if (jobDao.existsById(jobId)) {
			jobDao.deleteById(jobId);
			scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, jobGroup));
		}
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 批次刪除調度資訊
	 *
	 * @param jobIds 需要刪除的任務ID
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteJobByIds(Long[] jobIds) throws SchedulerException {
		for (Long jobId : jobIds) {
			SysJob job = jobDao.findById(jobId).orElse(new SysJob());
			deleteJob(job);
		}
	}

	/**
	 * 任務調度狀態修改
	 *
	 * @param job 調度資訊
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int changeStatus(SysJob job) throws SchedulerException {
		int rows = 0;
		String status = job.getStatus();
		if (ScheduleConstants.Status.NORMAL.getValue().equals(status)) {
			rows = resumeJob(job);
		} else if (ScheduleConstants.Status.PAUSE.getValue().equals(status)) {
			rows = pauseJob(job);
		}
		return rows;
	}

	/**
	 * 立即運行任務
	 *
	 * @param job 調度資訊
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void run(SysJob job) throws SchedulerException {
		Long jobId = job.getJobId();
		String jobGroup = job.getJobGroup();
		SysJob properties = selectJobById(job.getJobId());
		// 參數
		JobDataMap dataMap = new JobDataMap();
		dataMap.put(ScheduleConstants.TASK_PROPERTIES, properties);
		scheduler.triggerJob(ScheduleUtils.getJobKey(jobId, jobGroup), dataMap);
	}

	/**
	 * 新增任務
	 *
	 * @param job 調度資訊 調度資訊
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int insertJob(SysJob job) throws SchedulerException, TaskException {
		job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
		SysJob save = jobDao.save(job);
		if (null != save) {
			ScheduleUtils.createScheduleJob(scheduler, job);
		}
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 更新任務的時間表達式
	 *
	 * @param job 調度資訊
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateJob(SysJob job) throws SchedulerException, TaskException {
		SysJob properties = selectJobById(job.getJobId());
		SysJob save = jobDao.save(job);
		if (null != save) {
			updateSchedulerJob(job, properties.getJobGroup());
		}
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 更新任務
	 *
	 * @param job      任務物件
	 * @param jobGroup 任務組名
	 */
	public void updateSchedulerJob(SysJob job, String jobGroup) throws SchedulerException, TaskException {
		Long jobId = job.getJobId();
		// 判斷是否存在
		JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
		if (scheduler.checkExists(jobKey)) {
			// 防止創建時存在數據問題 先移除，然後在執行創建操作
			scheduler.deleteJob(jobKey);
		}
		ScheduleUtils.createScheduleJob(scheduler, job);
	}

	/**
	 * 校驗cron表達式是否有效
	 *
	 * @param cronExpression 表達式
	 * @return 結果
	 */
	@Override
	public boolean checkCronExpressionIsValid(String cronExpression) {
		return CronUtils.isValid(cronExpression);
	}
}
