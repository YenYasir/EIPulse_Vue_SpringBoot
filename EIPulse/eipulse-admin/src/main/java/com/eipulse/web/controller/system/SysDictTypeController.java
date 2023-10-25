package com.eipulse.web.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.common.annotation.Log;
import com.eipulse.common.constant.UserConstants;
import com.eipulse.common.core.controller.BaseController;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.core.domain.entity.SysDictType;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.page.TableDataInfo;
import com.eipulse.common.enums.BusinessType;
import com.eipulse.common.utils.poi.ExcelUtil;
import com.eipulse.system.service.SysDictTypeService;

/**
 * 數據字典資訊
 * 
 * @author eipulse
 */
@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController {
	@Autowired
	private SysDictTypeService dictTypeService;

	@PreAuthorize("@ss.hasPermi('system:dict:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysDictType dictType) {
		Page page = dictTypeService.findDictTypeList(dictType);
		return getDataTable(page);
	}

	@Log(title = "字典類型", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('system:dict:export')")
	@GetMapping("/export")
	public AjaxResult export(SysDictType dictType) {
		Page page = dictTypeService.findDictTypeList(dictType);
		ExcelUtil<SysDictType> util = new ExcelUtil<SysDictType>(SysDictType.class);
		return util.exportExcel(page.getItems(), "字典類型");
	}

	/**
	 * 查詢字典類型詳細
	 */
	@PreAuthorize("@ss.hasPermi('system:dict:query')")
	@GetMapping(value = "/{dictId}")
	public AjaxResult getInfo(@PathVariable Long dictId) {
		return AjaxResult.success(dictTypeService.getObject(dictId));
	}

	/**
	 * 新增字典類型
	 */
	@PreAuthorize("@ss.hasPermi('system:dict:add')")
	@Log(title = "字典類型", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@Validated @RequestBody SysDictType dict) {
		if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
			return AjaxResult.error("新增字典'" + dict.getDictName() + "'失敗，字典類型已存在");
		}
		dict.setCreateBy(getUsername());
		dictTypeService.insertDictType(dict);
		return AjaxResult.success();
	}

	/**
	 * 修改字典類型
	 */
	@PreAuthorize("@ss.hasPermi('system:dict:edit')")
	@Log(title = "字典類型", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@Validated @RequestBody SysDictType dict) {
		if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
			return AjaxResult.error("修改字典'" + dict.getDictName() + "'失敗，字典類型已存在");
		}
		dict.setUpdateBy(getUsername());
		dictTypeService.updateDictType(dict);
		return AjaxResult.success();
	}

	/**
	 * 刪除字典類型
	 */
	@PreAuthorize("@ss.hasPermi('system:dict:remove')")
	@Log(title = "字典類型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dictIds}")
	public AjaxResult remove(@PathVariable Long[] dictIds) {
		dictTypeService.deleteDictTypeByIds(dictIds);
		return success();
	}

	/**
	 * 刷新字典緩存
	 */
	@PreAuthorize("@ss.hasPermi('system:dict:remove')")
	@Log(title = "字典類型", businessType = BusinessType.CLEAN)
	@DeleteMapping("/refreshCache")
	public AjaxResult refreshCache() {
		dictTypeService.resetDictCache();
		return AjaxResult.success();
	}

	/**
	 * 獲取字典選擇框列表
	 */
	@GetMapping("/optionselect")
	public AjaxResult optionselect() {
		List<SysDictType> dictTypes = dictTypeService.getObjects();
		return AjaxResult.success(dictTypes);
	}
}
