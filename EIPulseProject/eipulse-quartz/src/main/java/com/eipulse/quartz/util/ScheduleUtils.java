package com.eipulse.quartz.util;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.eipulse.common.constant.ScheduleConstants;
import com.eipulse.common.exception.job.TaskException;
import com.eipulse.common.exception.job.TaskException.Code;
import com.eipulse.quartz.domain.SysJob;

/**
 * 定時任務工具類
 *
 */
public class ScheduleUtils {
	/**
	 * 得到quartz任務類
	 *
	 * @param sysJob 執行計劃
	 * @return 具體執行任務類
	 */
	private static Class<? extends Job> getQuartzJobClass(SysJob sysJob) {
		boolean isConcurrent = "0".equals(sysJob.getConcurrent());
		return isConcurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
	}

	/**
	 * 構建任務觸發物件
	 */
	public static TriggerKey getTriggerKey(Long jobId, String jobGroup) {
		return TriggerKey.triggerKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
	}

	/**
	 * 構建任務鍵物件
	 */
	public static JobKey getJobKey(Long jobId, String jobGroup) {
		return JobKey.jobKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
	}

	/**
	 * 創建定時任務
	 */
	public static void createScheduleJob(Scheduler scheduler, SysJob job) throws SchedulerException, TaskException {
		Class<? extends Job> jobClass = getQuartzJobClass(job);
		// 構建job資訊
		Long jobId = job.getJobId();
		String jobGroup = job.getJobGroup();
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(jobId, jobGroup)).build();

		// 表達式調度構建器
		CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
		cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);

		// 按新的cronExpression表達式構建一個新的trigger
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(jobId, jobGroup))
				.withSchedule(cronScheduleBuilder).build();

		// 放入參數，運行時的方法可以獲取
		jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);

		// 判斷是否存在
		if (scheduler.checkExists(getJobKey(jobId, jobGroup))) {
			// 防止創建時存在數據問題 先移除，然後在執行創建操作
			scheduler.deleteJob(getJobKey(jobId, jobGroup));
		}

		scheduler.scheduleJob(jobDetail, trigger);

		// 暫停任務
		if (job.getStatus().equals(ScheduleConstants.Status.PAUSE.getValue())) {
			scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
		}
	}

	/**
	 * 設置定時任務策略
	 */
	public static CronScheduleBuilder handleCronScheduleMisfirePolicy(SysJob job, CronScheduleBuilder cb)
			throws TaskException {
		switch (job.getMisfirePolicy()) {
		case ScheduleConstants.MISFIRE_DEFAULT:
			return cb;
		case ScheduleConstants.MISFIRE_IGNORE_MISFIRES:
			return cb.withMisfireHandlingInstructionIgnoreMisfires();
		case ScheduleConstants.MISFIRE_FIRE_AND_PROCEED:
			return cb.withMisfireHandlingInstructionFireAndProceed();
		case ScheduleConstants.MISFIRE_DO_NOTHING:
			return cb.withMisfireHandlingInstructionDoNothing();
		default:
			throw new TaskException(
					"The task misfire policy '" + job.getMisfirePolicy() + "' cannot be used in cron schedule tasks",
					Code.CONFIG_ERROR);
		}
	}
}
