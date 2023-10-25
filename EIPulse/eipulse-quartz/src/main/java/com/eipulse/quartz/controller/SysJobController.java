package com.eipulse.quartz.controller;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.common.annotation.Log;
import com.eipulse.common.constant.Constants;
import com.eipulse.common.core.controller.BaseController;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.page.TableDataInfo;
import com.eipulse.common.enums.BusinessType;
import com.eipulse.common.exception.job.TaskException;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.poi.ExcelUtil;
import com.eipulse.quartz.domain.SysJob;
import com.eipulse.quartz.service.impl.SysJobService;
import com.eipulse.quartz.util.CronUtils;

/**
 * 調度任務資訊操作處理
 * 
 * @author eipulse
 */
@RestController
@RequestMapping("/monitor/job")
public class SysJobController extends BaseController {
	@Autowired
	private SysJobService jobService;

	/**
	 * 查詢定時任務列表
	 */
	@PreAuthorize("@ss.hasPermi('monitor:job:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysJob sysJob) {
		Page page = jobService.selectJobList(sysJob);
		return getDataTable(page);
	}

	/**
	 * 導出定時任務列表
	 */
	@PreAuthorize("@ss.hasPermi('monitor:job:export')")
	@Log(title = "定時任務", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	public AjaxResult export(SysJob sysJob) {
		Page page = jobService.selectJobList(sysJob);
		ExcelUtil<SysJob> util = new ExcelUtil<SysJob>(SysJob.class);
		return util.exportExcel(page.getItems(), "定時任務");
	}

	/**
	 * 獲取定時任務詳細資訊
	 */
	@PreAuthorize("@ss.hasPermi('monitor:job:query')")
	@GetMapping(value = "/{jobId}")
	public AjaxResult getInfo(@PathVariable("jobId") Long jobId) {
		return AjaxResult.success(jobService.getObject(jobId));
	}

	/**
	 * 新增定時任務
	 */
	@PreAuthorize("@ss.hasPermi('monitor:job:add')")
	@Log(title = "定時任務", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody SysJob job) throws SchedulerException, TaskException {
		if (!CronUtils.isValid(job.getCronExpression())) {
			return error("新增任務'" + job.getJobName() + "'失敗，Cron表達式不正確");
		} else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_RMI)) {
			return error("新增任務'" + job.getJobName() + "'失敗，目標字串不允許'rmi://'調用");
		} else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_LDAP)) {
			return error("新增任務'" + job.getJobName() + "'失敗，目標字串不允許'ldap://'調用");
		} else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(),
				new String[] { Constants.HTTP, Constants.HTTPS })) {
			return error("新增任務'" + job.getJobName() + "'失敗，目標字串不允許'http(s)//'調用");
		}
		job.setCreateBy(getUsername());
		jobService.insertJob(job);
		return AjaxResult.success();
	}

	/**
	 * 修改定時任務
	 */
	@PreAuthorize("@ss.hasPermi('monitor:job:edit')")
	@Log(title = "定時任務", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody SysJob job) throws SchedulerException, TaskException {
		if (!CronUtils.isValid(job.getCronExpression())) {
			return error("修改任務'" + job.getJobName() + "'失敗，Cron表達式不正確");
		} else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_RMI)) {
			return error("修改任務'" + job.getJobName() + "'失敗，目標字串不允許'rmi://'調用");
		} else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_LDAP)) {
			return error("修改任務'" + job.getJobName() + "'失敗，目標字串不允許'ldap://'調用");
		} else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(),
				new String[] { Constants.HTTP, Constants.HTTPS })) {
			return error("修改任務'" + job.getJobName() + "'失敗，目標字串不允許'http(s)//'調用");
		}
		job.setUpdateBy(getUsername());
		jobService.updateJob(job);
		return AjaxResult.success();
	}

	/**
	 * 定時任務狀態修改
	 */
	@PreAuthorize("@ss.hasPermi('monitor:job:changeStatus')")
	@Log(title = "定時任務", businessType = BusinessType.UPDATE)
	@PutMapping("/changeStatus")
	public AjaxResult changeStatus(@RequestBody SysJob job) throws SchedulerException {
		SysJob newJob = (SysJob) jobService.getObject(job.getJobId());
		newJob.setStatus(job.getStatus());
		jobService.changeStatus(newJob);
		return AjaxResult.success();
	}

	/**
	 * 定時任務立即執行一次
	 */
	@PreAuthorize("@ss.hasPermi('monitor:job:changeStatus')")
	@Log(title = "定時任務", businessType = BusinessType.UPDATE)
	@PutMapping("/run")
	public AjaxResult run(@RequestBody SysJob job) throws SchedulerException {
		jobService.run(job);
		return AjaxResult.success();
	}

	/**
	 * 刪除定時任務
	 */
	@PreAuthorize("@ss.hasPermi('monitor:job:remove')")
	@Log(title = "定時任務", businessType = BusinessType.DELETE)
	@DeleteMapping("/{jobIds}")
	public AjaxResult remove(@PathVariable Long[] jobIds) throws SchedulerException, TaskException {
		jobService.deleteJobByIds(jobIds);
		return AjaxResult.success();
	}
}
