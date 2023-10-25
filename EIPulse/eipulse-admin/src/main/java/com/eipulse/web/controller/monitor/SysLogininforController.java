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
import com.eipulse.system.domain.SysLogininfor;
import com.eipulse.system.service.SysLogininforService;

/**
 * 系統訪問記錄
 * 
 * @author eipulse
 */
@RestController
@RequestMapping("/monitor/logininfor")
public class SysLogininforController extends BaseController {
	@Autowired
	private SysLogininforService logininforService;

	@PreAuthorize("@ss.hasPermi('monitor:logininfor:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysLogininfor logininfor) {
		Page page = logininforService.findLogininforList(logininfor);
		return getDataTable(page);
	}

	@Log(title = "登入日誌", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('monitor:logininfor:export')")
	@GetMapping("/export")
	public AjaxResult export(SysLogininfor logininfor) {
		Page page = logininforService.findLogininforList(logininfor);
		ExcelUtil<SysLogininfor> util = new ExcelUtil<SysLogininfor>(SysLogininfor.class);
		return util.exportExcel(page.getItems(), "登入日誌");
	}

	@PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
	@Log(title = "登入日誌", businessType = BusinessType.DELETE)
	@DeleteMapping("/{infoIds}")
	public AjaxResult remove(@PathVariable Long[] infoIds) {
		logininforService.deleteObject(infoIds);
		return AjaxResult.success();
	}

	@PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
	@Log(title = "登入日誌", businessType = BusinessType.CLEAN)
	@DeleteMapping("/clean")
	public AjaxResult clean() {
		logininforService.cleanLogininfor();
		return AjaxResult.success();
	}
}
