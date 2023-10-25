package com.eipulse.web.controller.monitor;

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
import com.eipulse.system.domain.SysOperLog;
import com.eipulse.system.service.SysOperLogService;

/**
 * 操作日誌記錄
 * 
 * @author eipulse
 */
@RestController
@RequestMapping("/monitor/operlog")
public class SysOperlogController extends BaseController {
	@Autowired
	private SysOperLogService operLogService;

	@PreAuthorize("@ss.hasPermi('monitor:operlog:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysOperLog operLog) {
		Page page = operLogService.findOperLogList(operLog);
		return getDataTable(page);
	}

	@Log(title = "操作日誌", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
	@GetMapping("/export")
	public AjaxResult export(SysOperLog operLog) {
		Page page = operLogService.findOperLogList(operLog);
		ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
		return util.exportExcel(page.getItems(), "操作日誌");
	}

	@Log(title = "操作日誌", businessType = BusinessType.DELETE)
	@PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
	@DeleteMapping("/{operIds}")
	public AjaxResult remove(@PathVariable Long[] operIds) {
		operLogService.deleteObject(operIds);
		return AjaxResult.success();
	}

	@Log(title = "操作日誌", businessType = BusinessType.CLEAN)
	@PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
	@DeleteMapping("/clean")
	public AjaxResult clean() {
		operLogService.cleanOperLog();
		return AjaxResult.success();
	}
}
