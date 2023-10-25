package com.eipulse.web.controller.system;

import java.util.ArrayList;
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
import com.eipulse.common.core.controller.BaseController;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.core.domain.entity.SysDictData;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.page.TableDataInfo;
import com.eipulse.common.enums.BusinessType;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.poi.ExcelUtil;
import com.eipulse.system.service.SysDictDataService;
import com.eipulse.system.service.SysDictTypeService;

/**
 * 數據字典資訊
 * 
 * @author eipulse
 */
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {
	@Autowired
	private SysDictDataService dictDataService;

	@Autowired
	private SysDictTypeService dictTypeService;

	@PreAuthorize("@ss.hasPermi('system:dict:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysDictData dictData) {
		Page page = dictDataService.findDictDataList(dictData);
		return getDataTable(page);
	}

	@Log(title = "字典數據", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('system:dict:export')")
	@GetMapping("/export")
	public AjaxResult export(SysDictData dictData) {
		Page page = dictDataService.findDictDataList(dictData);
		ExcelUtil<SysDictData> util = new ExcelUtil<SysDictData>(SysDictData.class);
		return util.exportExcel(page.getItems(), "字典數據");
	}

	/**
	 * 查詢字典數據詳細
	 */
	@PreAuthorize("@ss.hasPermi('system:dict:query')")
	@GetMapping(value = "/{dictCode}")
	public AjaxResult getInfo(@PathVariable Long dictCode) {
		return AjaxResult.success(dictDataService.getObject(dictCode));
	}

	/**
	 * 根據字典類型查詢字典數據信息
	 */
	@GetMapping(value = "/type/{dictType}")
	public AjaxResult dictType(@PathVariable String dictType) {
		List<SysDictData> data = dictTypeService.findDictDataByType(dictType);
		if (StringUtils.isNull(data)) {
			data = new ArrayList<SysDictData>();
		}
		return AjaxResult.success(data);
	}

	/**
	 * 新增字典類型
	 */
	@PreAuthorize("@ss.hasPermi('system:dict:add')")
	@Log(title = "字典數據", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@Validated @RequestBody SysDictData dict) {
		dict.setCreateBy(getUsername());
		dictDataService.insertDictData(dict);
		return AjaxResult.success();
	}

	/**
	 * 修改保存字典類型
	 */
	@PreAuthorize("@ss.hasPermi('system:dict:edit')")
	@Log(title = "字典數據", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@Validated @RequestBody SysDictData dict) {
		dict.setUpdateBy(getUsername());
		dictDataService.updateDictData(dict);
		return AjaxResult.success();
	}

	/**
	 * 刪除字典類型
	 */
	@PreAuthorize("@ss.hasPermi('system:dict:remove')")
	@Log(title = "字典類型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dictCodes}")
	public AjaxResult remove(@PathVariable Long[] dictCodes) {
		dictDataService.deleteDictDataByIds(dictCodes);
		return success();
	}
}
