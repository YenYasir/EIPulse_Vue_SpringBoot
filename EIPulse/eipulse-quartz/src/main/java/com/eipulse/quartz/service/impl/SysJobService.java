package com.eipulse.quartz.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.common.constant.ScheduleConstants;
import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.service.BaseService;
import com.eipulse.common.exception.job.TaskException;
import com.eipulse.quartz.dao.SysJobDao;
import com.eipulse.quartz.domain.SysJob;
import com.eipulse.quartz.util.CronUtils;
import com.eipulse.quartz.util.ScheduleUtils;

/**
 * 定時任務調度資訊 服務層
 * 
 * @author eipulse
 */
@Service
public class SysJobService extends BaseService<SysJob, Long> {
	@Autowired
	private Scheduler scheduler;

	@Autowired
	private SysJobDao sysJobDao;

	@Override
	protected BaseDao<SysJob, Long> getDao() {
		return sysJobDao;
	}

	/**
	 * 項目啟動時，初始化定時器 主要是防止手動修改數據庫導致未同步到定時任務處理（註：不能手動修改數據庫ID和任務組名，否則會導致臟數據）
	 */
	@PostConstruct
	public void init() throws SchedulerException, TaskException {
		scheduler.clear();
		List<SysJob> jobList = sysJobDao.getObjects();
		for (SysJob job : jobList) {
			ScheduleUtils.createScheduleJob(scheduler, job);
		}
	}

	/**
	 * 獲取quartz調度器的計劃任務列表(分頁)
	 * 
	 * @param job 調度資訊
	 * @return
	 */
	public Page selectJobList(SysJob job) {
		return sysJobDao.selectJobList(job);
	}

	/**
	 * 暫停任務
	 * 
	 * @param job 調度資訊
	 */
	@Transactional(rollbackFor = Exception.class)
	public void pauseJob(SysJob job) throws SchedulerException {
		Long jobId = job.getJobId();
		String jobGroup = job.getJobGroup();
		job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
		sysJobDao.updateObject(job);
		scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
	}

	/**
	 * 恢覆任務
	 * 
	 * @param job 調度資訊
	 */
	@Transactional(rollbackFor = Exception.class)
	public void resumeJob(SysJob job) throws SchedulerException {
		Long jobId = job.getJobId();
		String jobGroup = job.getJobGroup();
		job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
		sysJobDao.updateObject(job);
		scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
	}

	/**
	 * 刪除任務後，所對應的trigger也將被刪除
	 * 
	 * @param job 調度資訊
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteJob(SysJob job) throws SchedulerException {
		Long jobId = job.getJobId();
		String jobGroup = job.getJobGroup();
		sysJobDao.deleteObject(jobId);
		scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, jobGroup));
	}

	/**
	 * 批量刪除調度資訊
	 * 
	 * @param jobIds 需要刪除的任務ID
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteJobByIds(Long[] jobIds) throws SchedulerException {
		for (Long jobId : jobIds) {
			deleteObject(jobId);
		}
	}

	/**
	 * 任務調度狀態修改
	 * 
	 * @param job 調度資訊
	 */
	@Transactional(rollbackFor = Exception.class)
	public void changeStatus(SysJob job) throws SchedulerException {
		int rows = 0;
		String status = job.getStatus();
		if (ScheduleConstants.Status.NORMAL.getValue().equals(status)) {
			resumeJob(job);
		} else if (ScheduleConstants.Status.PAUSE.getValue().equals(status)) {
			pauseJob(job);
		}
	}

	/**
	 * 立即運行任務
	 * 
	 * @param job 調度資訊
	 */
	@Transactional(rollbackFor = Exception.class)
	public void run(SysJob job) throws SchedulerException {
		Long jobId = job.getJobId();
		String jobGroup = job.getJobGroup();
		SysJob properties = (SysJob) getObject(job.getJobId());
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
	public void insertJob(SysJob job) throws SchedulerException, TaskException {
		job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
		sysJobDao.addObject(job);
		ScheduleUtils.createScheduleJob(scheduler, job);
	}

	/**
	 * 更新任務的時間表達式
	 * 
	 * @param job 調度資訊
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateJob(SysJob job) throws SchedulerException, TaskException {
		SysJob properties = (SysJob) getObject(job.getJobId());
		sysJobDao.updateObject(job);
		updateSchedulerJob(job, properties.getJobGroup());
	}

	/**
	 * 更新任務
	 * 
	 * @param job      任務對象
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
	public boolean checkCronExpressionIsValid(String cronExpression) {
		return CronUtils.isValid(cronExpression);
	}
}
