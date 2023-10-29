package com.eipulse.quartz.util;

import org.quartz.JobExecutionContext;

import com.eipulse.quartz.domain.SysJob;

/**
 * 定時任務處理（允許併發執行）
 *
 */
public class QuartzJobExecution extends AbstractQuartzJob {
	@Override
	protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
		JobInvokeUtil.invokeMethod(sysJob);
	}
}
