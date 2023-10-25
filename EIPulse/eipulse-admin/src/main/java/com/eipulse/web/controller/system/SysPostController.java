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
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.page.TableDataInfo;
import com.eipulse.common.enums.BusinessType;
import com.eipulse.common.utils.poi.ExcelUtil;
import com.eipulse.system.domain.SysPost;
import com.eipulse.system.service.SysPostService;

/**
 * 職位資訊操作處理
 * 
 * @author eipulse
 */
@RestController
@RequestMapping("/system/post")
public class SysPostController extends BaseController {
	@Autowired
	private SysPostService postService;

	/**
	 * 獲取職位列表
	 */
	@PreAuthorize("@ss.hasPermi('system:post:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysPost post) {
		Page page = postService.findPostList(post);
		return getDataTable(page);
	}

	@Log(title = "職位管理", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('system:post:export')")
	@GetMapping("/export")
	public AjaxResult export(SysPost post) {
		Page page = postService.findPostList(post);
		ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
		return util.exportExcel(page.getItems(), "職位數據");
	}

	/**
	 * 根據職位編號獲取詳細資訊
	 */
	@PreAuthorize("@ss.hasPermi('system:post:query')")
	@GetMapping(value = "/{postId}")
	public AjaxResult getInfo(@PathVariable Long postId) {
		return AjaxResult.success(postService.getObject(postId));
	}

	/**
	 * 新增職位
	 */
	@PreAuthorize("@ss.hasPermi('system:post:add')")
	@Log(title = "職位管理", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@Validated @RequestBody SysPost post) {
		if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
			return AjaxResult.error("新增職位'" + post.getPostName() + "'失敗，職位名稱已存在");
		} else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
			return AjaxResult.error("新增職位'" + post.getPostName() + "'失敗，職位編碼已存在");
		}
		post.setCreateBy(getUsername());
		postService.addObject(post);
		return AjaxResult.success();
	}

	/**
	 * 修改職位
	 */
	@PreAuthorize("@ss.hasPermi('system:post:edit')")
	@Log(title = "職位管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@Validated @RequestBody SysPost post) {
		if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
			return AjaxResult.error("修改職位'" + post.getPostName() + "'失敗，職位名稱已存在");
		} else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
			return AjaxResult.error("修改職位'" + post.getPostName() + "'失敗，職位編碼已存在");
		}
		post.setUpdateBy(getUsername());
		postService.updateObject(post);
		return AjaxResult.success();
	}

	/**
	 * 刪除職位
	 */
	@PreAuthorize("@ss.hasPermi('system:post:remove')")
	@Log(title = "職位管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{postIds}")
	public AjaxResult remove(@PathVariable Long[] postIds) {
		postService.deletePostByIds(postIds);
		return AjaxResult.success();
	}

	/**
	 * 獲取職位選擇框列表
	 */
	@GetMapping("/optionselect")
	public AjaxResult optionselect() {
		List<SysPost> posts = postService.getObjects();
		return AjaxResult.success(posts);
	}
}
