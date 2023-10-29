package com.eipulse.web.controller.system;

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
import com.eipulse.common.core.controller.BaseController;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.core.page.TableDataInfo;
import com.eipulse.common.enums.BusinessType;
import com.eipulse.common.utils.SecurityUtils;
import com.eipulse.system.domain.SysNotice;
import com.eipulse.system.service.ISysNoticeService;

/**
 * 公告 資訊操作處理
 */
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController {
	@Autowired
	private ISysNoticeService noticeService;

	/**
	 * 獲取通知公告列表
	 */
	@PreAuthorize("@ss.hasPermi('system:notice:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysNotice notice) {
		Page<SysNotice> page = noticeService.selectNoticeList(notice);
		return getDataTable(page);
	}

	/**
	 * 根據通知公告編號獲取詳細資訊
	 */
	@PreAuthorize("@ss.hasPermi('system:notice:query')")
	@GetMapping(value = "/{noticeId}")
	public AjaxResult getInfo(@PathVariable Long noticeId) {
		return AjaxResult.success(noticeService.selectNoticeById(noticeId));
	}

	/**
	 * 新增通知公告
	 */
	@PreAuthorize("@ss.hasPermi('system:notice:add')")
	@Log(title = "通知公告", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@Validated @RequestBody SysNotice notice) {
		notice.setCreateBy(SecurityUtils.getUsername());
		return toAjax(noticeService.insertNotice(notice));
	}

	/**
	 * 修改通知公告
	 */
	@PreAuthorize("@ss.hasPermi('system:notice:edit')")
	@Log(title = "通知公告", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@Validated @RequestBody SysNotice notice) {
		notice.setUpdateBy(SecurityUtils.getUsername());
		return toAjax(noticeService.updateNotice(notice));
	}

	/**
	 * 刪除通知公告
	 */
	@PreAuthorize("@ss.hasPermi('system:notice:remove')")
	@Log(title = "通知公告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{noticeIds}")
	public AjaxResult remove(@PathVariable Long[] noticeIds) {
		return toAjax(noticeService.deleteNoticeByIds(noticeIds));
	}
}
