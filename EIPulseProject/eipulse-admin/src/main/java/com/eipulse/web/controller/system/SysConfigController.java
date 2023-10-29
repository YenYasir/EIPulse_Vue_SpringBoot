package com.eipulse.web.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.eipulse.common.annotation.RepeatSubmit;
import com.eipulse.common.constant.UserConstants;
import com.eipulse.common.core.controller.BaseController;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.core.page.TableDataInfo;
import com.eipulse.common.enums.BusinessType;
import com.eipulse.common.utils.SecurityUtils;
import com.eipulse.common.utils.poi.ExcelUtil;
import com.eipulse.system.domain.SysConfig;
import com.eipulse.system.service.ISysConfigService;

/**
 * 參數配置 資訊操作處理
 */
@RestController
@RequestMapping("/system/config")
public class SysConfigController extends BaseController {
	@Autowired
	private ISysConfigService configService;

	/**
	 * 獲取參數配置列表
	 */
	@PreAuthorize("@ss.hasPermi('system:config:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysConfig config) {
		Page<SysConfig> page = configService.selectConfigList(config);
		return getDataTable(page);
	}

	@Log(title = "參數管理", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('system:config:export')")
	@GetMapping("/export")
	public AjaxResult export(SysConfig config) {
		Page<SysConfig> page = configService.selectConfigList(config);
		List<SysConfig> list = page.getContent();
		ExcelUtil<SysConfig> util = new ExcelUtil<>(SysConfig.class);
		return util.exportExcel(list, "參數數據");
	}

	/**
	 * 根據參數編號獲取詳細資訊
	 */
	@PreAuthorize("@ss.hasPermi('system:config:query')")
	@GetMapping(value = "/{configId}")
	public AjaxResult getInfo(@PathVariable Long configId) {
		return AjaxResult.success(configService.selectConfigById(configId));
	}

	/**
	 * 根據參數鍵名查詢參數值
	 */
	@GetMapping(value = "/configKey/{configKey}")
	public AjaxResult getConfigKey(@PathVariable String configKey) {
		return AjaxResult.success(configService.selectConfigByKey(configKey));
	}

	/**
	 * 新增參數配置
	 */
	@PreAuthorize("@ss.hasPermi('system:config:add')")
	@Log(title = "參數管理", businessType = BusinessType.INSERT)
	@PostMapping
	@RepeatSubmit
	public AjaxResult add(@Validated @RequestBody SysConfig config) {
		if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
			return AjaxResult.error("新增參數'" + config.getConfigName() + "'失敗，參數鍵名已存在");
		}
		config.setCreateBy(SecurityUtils.getUsername());
		return toAjax(configService.insertConfig(config));
	}

	/**
	 * 修改參數配置
	 */
	@PreAuthorize("@ss.hasPermi('system:config:edit')")
	@Log(title = "參數管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@Validated @RequestBody SysConfig config) {
		if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
			return AjaxResult.error("修改參數'" + config.getConfigName() + "'失敗，參數鍵名已存在");
		}
		config.setUpdateBy(SecurityUtils.getUsername());
		return toAjax(configService.updateConfig(config));
	}

	/**
	 * 刪除參數配置
	 */
	@PreAuthorize("@ss.hasPermi('system:config:remove')")
	@Log(title = "參數管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{configIds}")
	public AjaxResult remove(@PathVariable Long[] configIds) {
		return toAjax(configService.deleteConfigByIds(configIds));
	}

	/**
	 * 清空快取
	 */
	@PreAuthorize("@ss.hasPermi('system:config:remove')")
	@Log(title = "參數管理", businessType = BusinessType.CLEAN)
	@DeleteMapping("/clearCache")
	public AjaxResult clearCache() {
		configService.clearCache();
		return AjaxResult.success();
	}

}
