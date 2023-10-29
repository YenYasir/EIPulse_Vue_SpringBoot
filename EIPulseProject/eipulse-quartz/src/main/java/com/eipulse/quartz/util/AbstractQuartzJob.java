package com.eipulse.quartz.util;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eipulse.common.constant.Constants;
import com.eipulse.common.constant.ScheduleConstants;
import com.eipulse.common.utils.ExceptionUtil;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.bean.BeanUtils;
import com.eipulse.common.utils.spring.SpringUtils;
import com.eipulse.quartz.domain.SysJob;
import com.eipulse.quartz.domain.SysJobLog;
import com.eipulse.quartz.service.ISysJobLogService;

/**
 * 抽象quartz調用
 */
public abstract class AbstractQuartzJob implements Job {
	private static final Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);

	/**
	 * 執行緒本地變數
	 */
	private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		SysJob sysJob = new SysJob();
		BeanUtils.copyBeanProp(sysJob, context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES));
		try {
			before(context, sysJob);
			if (sysJob != null) {
				doExecute(context, sysJob);
			}
			after(context, sysJob, null);
		} catch (Exception e) {
			log.error("任務執行異常  - ：", e);
			after(context, sysJob, e);
		}
	}

	/**
	 * 執行前
	 *
	 * @param context 工作執行上下文物件
	 * @param sysJob  系統計劃任務
	 */
	protected void before(JobExecutionContext context, SysJob sysJob) {
		threadLocal.set(new Date());
	}

	/**
	 * 執行後
	 *
	 * @param context 工作執行上下文物件
	 * @param sysJob  系統計劃任務
	 */
	protected void after(JobExecutionContext context, SysJob sysJob, Exception e) {
		Date startTime = threadLocal.get();
		threadLocal.remove();

		final SysJobLog sysJobLog = new SysJobLog();
		sysJobLog.setJobName(sysJob.getJobName());
		sysJobLog.setJobGroup(sysJob.getJobGroup());
		sysJobLog.setInvokeTarget(sysJob.getInvokeTarget());
		sysJobLog.setStartTime(startTime);
		sysJobLog.setStopTime(new Date());
		long runMs = sysJobLog.getStopTime().getTime() - sysJobLog.getStartTime().getTime();
		sysJobLog.setJobMessage(sysJobLog.getJobName() + " 總共耗時：" + runMs + "毫秒");
		if (e != null) {
			sysJobLog.setStatus(Constants.FAIL);
			String errorMsg = StringUtils.substring(ExceptionUtil.getExceptionMessage(e), 0, 2000);
			sysJobLog.setExceptionInfo(errorMsg);
		} else {
			sysJobLog.setStatus(Constants.SUCCESS);
		}

		// 寫入資料庫當中
		SpringUtils.getBean(ISysJobLogService.class).addJobLog(sysJobLog);
	}

	/**
	 * 執行方法，由子類重載
	 *
	 * @param context 工作執行上下文物件
	 * @param sysJob  系統計劃任務
	 * @throws Exception 執行過程中的異常
	 */
	protected abstract void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception;
}
