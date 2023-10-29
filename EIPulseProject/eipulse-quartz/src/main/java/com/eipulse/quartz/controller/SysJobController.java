package com.eipulse.quartz.controller;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.eipulse.common.core.controller.BaseController;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.core.page.TableDataInfo;
import com.eipulse.common.enums.BusinessType;
import com.eipulse.common.exception.job.TaskException;
import com.eipulse.common.utils.SecurityUtils;
import com.eipulse.common.utils.poi.ExcelUtil;
import com.eipulse.quartz.domain.SysJob;
import com.eipulse.quartz.service.ISysJobService;
import com.eipulse.quartz.util.CronUtils;

/**
 * 調度任務資訊操作處理
 */
@RestController
@RequestMapping("/monitor/job")
public class SysJobController extends BaseController {
	@Autowired
	private ISysJobService jobService;

	/**
	 * 查詢定時任務列表
	 */
	@PreAuthorize("@ss.hasPermi('monitor:job:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysJob sysJob) {
		Page<SysJob> page = jobService.selectJobList(sysJob);
		return getDataTable(page);
	}

	/**
	 * 導出定時任務列表
	 */
	@PreAuthorize("@ss.hasPermi('monitor:job:export')")
	@Log(title = "定時任務", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	public AjaxResult export(SysJob sysJob) {
		Page<SysJob> page = jobService.selectJobList(sysJob);
		ExcelUtil<SysJob> util = new ExcelUtil<>(SysJob.class);
		return util.exportExcel(page.getContent(), "定時任務");
	}

	/**
	 * 獲取定時任務詳細資訊
	 */
	@PreAuthorize("@ss.hasPermi('monitor:job:query')")
	@GetMapping(value = "/{jobId}")
	public AjaxResult getInfo(@PathVariable("jobId") Long jobId) {
		return AjaxResult.success(jobService.selectJobById(jobId));
	}

	/**
	 * 新增定時任務
	 */
	@PreAuthorize("@ss.hasPermi('monitor:job:add')")
	@Log(title = "定時任務", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody SysJob sysJob) throws SchedulerException, TaskException {
		if (!CronUtils.isValid(sysJob.getCronExpression())) {
			return AjaxResult.error("cron表達式不正確");
		}
		sysJob.setCreateBy(SecurityUtils.getUsername());
		return toAjax(jobService.insertJob(sysJob));
	}

	/**
	 * 修改定時任務
	 */
	@PreAuthorize("@ss.hasPermi('monitor:job:edit')")
	@Log(title = "定時任務", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody SysJob sysJob) throws SchedulerException, TaskException {
		if (!CronUtils.isValid(sysJob.getCronExpression())) {
			return AjaxResult.error("cron表達式不正確");
		}
		sysJob.setUpdateBy(SecurityUtils.getUsername());
		return toAjax(jobService.updateJob(sysJob));
	}

	/**
	 * 定時任務狀態修改
	 */
	@PreAuthorize("@ss.hasPermi('monitor:job:changeStatus')")
	@Log(title = "定時任務", businessType = BusinessType.UPDATE)
	@PutMapping("/changeStatus")
	public AjaxResult changeStatus(@RequestBody SysJob job) throws SchedulerException {
		SysJob newJob = jobService.selectJobById(job.getJobId());
		newJob.setStatus(job.getStatus());
		return toAjax(jobService.changeStatus(newJob));
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
