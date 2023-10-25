package com.eipulse.quartz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.common.annotation.Log;
import com.eipulse.common.core.controller.BaseController;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.page.TableDataInfo;
import com.eipulse.common.enums.BusinessType;
import com.eipulse.common.utils.poi.ExcelUtil;
import com.eipulse.quartz.domain.SysJobLog;
import com.eipulse.quartz.service.impl.SysJobLogService;

/**
 * 調度日誌操作處理
 * 
 * @author eipulse
 */
@RestController
@RequestMapping("/monitor/jobLog")
public class SysJobLogController extends BaseController {
	@Autowired
	private SysJobLogService jobLogService;

	/**
	 * 查詢定時任務調度日誌列表
	 */
	@PreAuthorize("@ss.hasPermi('monitor:job:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysJobLog sysJobLog) {
		Page page = jobLogService.selectJobLogList(sysJobLog);
		return getDataTable(page);
	}

	/**
	 * 導出定時任務調度日誌列表
	 */
	@PreAuthorize("@ss.hasPermi('monitor:job:export')")
	@Log(title = "任務調度日誌", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	public AjaxResult export(SysJobLog sysJobLog) {
		Page page = jobLogService.selectJobLogList(sysJobLog);
		ExcelUtil<SysJobLog> util = new ExcelUtil<SysJobLog>(SysJobLog.class);
		return util.exportExcel(page.getItems(), "調度日誌");
	}

	/**
	 * 根據調度編號獲取詳細信息
	 */
	@PreAuthorize("@ss.hasPermi('monitor:job:query')")
	@GetMapping(value = "/{configId}")
	public AjaxResult getInfo(@PathVariable Long jobLogId) {
		return AjaxResult.success(jobLogService.getObject(jobLogId));
	}

	/**
	 * 刪除定時任務調度日誌
	 */
	@PreAuthorize("@ss.hasPermi('monitor:job:remove')")
	@Log(title = "定時任務調度日誌", businessType = BusinessType.DELETE)
	@DeleteMapping("/{jobLogIds}")
	public AjaxResult remove(@PathVariable Long[] jobLogIds) {
		jobLogService.deleteObject(jobLogIds);
		return AjaxResult.success();
	}

	/**
	 * 清空定時任務調度日誌
	 */
	@PreAuthorize("@ss.hasPermi('monitor:job:remove')")
	@Log(title = "調度日誌", businessType = BusinessType.CLEAN)
	@DeleteMapping("/clean")
	public AjaxResult clean() {
		jobLogService.cleanJobLog();
		return AjaxResult.success();
	}
}
