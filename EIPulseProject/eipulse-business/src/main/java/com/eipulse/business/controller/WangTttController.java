package com.eipulse.business.controller;

import java.util.Arrays;
import java.util.List;

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

import com.eipulse.business.domain.WangTtt;
import com.eipulse.business.service.IWangTttService;
import com.eipulse.common.annotation.Log;
import com.eipulse.common.core.controller.BaseController;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.core.page.TableDataInfo;
import com.eipulse.common.enums.BusinessType;
import com.eipulse.common.utils.poi.ExcelUtil;

/**
 * 測試程式碼生成器Controller
 */
@RestController
@RequestMapping("/business/ttt")
public class WangTttController extends BaseController {
	@Autowired
	private IWangTttService wangTttService;

	/**
	 * 查詢測試程式碼生成器列表
	 */
	@PreAuthorize("@ss.hasPermi('business:ttt:list')")
	@GetMapping("/list")
	public TableDataInfo list(WangTtt wangTtt) {
		Page<WangTtt> page = wangTttService.findWangTttPage(wangTtt);
		return getDataTable(page);
	}

	/**
	 * 導出測試程式碼生成器列表
	 */
	@PreAuthorize("@ss.hasPermi('business:ttt:export')")
	@Log(title = "測試程式碼生成器", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	public AjaxResult export(WangTtt wangTtt) {
		List<WangTtt> list = wangTttService.findWangTttList(wangTtt);
		ExcelUtil<WangTtt> util = new ExcelUtil<>(WangTtt.class);
		return util.exportExcel(list, "ttt");
	}

	/**
	 * 獲取測試程式碼生成器詳細資訊
	 */
	@PreAuthorize("@ss.hasPermi('business:ttt:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") String id) {
		return AjaxResult.success(wangTttService.findById(id));
	}

	/**
	 * 新增測試程式碼生成器
	 */
	@PreAuthorize("@ss.hasPermi('business:ttt:add')")
	@Log(title = "測試程式碼生成器", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody WangTtt wangTtt) {
		wangTttService.save(wangTtt);
		return AjaxResult.success();
	}

	/**
	 * 修改測試程式碼生成器
	 */
	@PreAuthorize("@ss.hasPermi('business:ttt:edit')")
	@Log(title = "測試程式碼生成器", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody WangTtt wangTtt) {
		wangTttService.save(wangTtt);
		return AjaxResult.success();
	}

	/**
	 * 刪除測試程式碼生成器
	 */
	@PreAuthorize("@ss.hasPermi('business:ttt:remove')")
	@Log(title = "測試程式碼生成器", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable String[] ids) {
		wangTttService.deleteByIds(Arrays.asList(ids));
		return AjaxResult.success();
	}
}
