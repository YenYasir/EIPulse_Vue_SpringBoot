package com.eipulse.web.controller.system;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
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
import com.eipulse.common.core.domain.entity.SysDept;
import com.eipulse.common.enums.BusinessType;
import com.eipulse.common.utils.SecurityUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.system.service.ISysDeptService;

/**
 * 部門資訊
 */
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {
	@Autowired
	private ISysDeptService deptService;

	/**
	 * 獲取部門列表
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:list')")
	@GetMapping("/list")
	public AjaxResult list(SysDept dept) {
		List<SysDept> depts = deptService.selectDeptList(dept);
		return AjaxResult.success(depts);
	}

	/**
	 * 查詢部門列表（排除節點）
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:list')")
	@GetMapping("/list/exclude/{deptId}")
	public AjaxResult excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
		List<SysDept> depts = deptService.selectDeptList(new SysDept());
		Iterator<SysDept> it = depts.iterator();
		while (it.hasNext()) {
			SysDept d = it.next();
			if (d.getDeptId().intValue() == deptId
					|| ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + "")) {
				it.remove();
			}
		}
		return AjaxResult.success(depts);
	}

	/**
	 * 根據部門編號獲取詳細資訊
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:query')")
	@GetMapping(value = "/{deptId}")
	public AjaxResult getInfo(@PathVariable Long deptId) {
		return AjaxResult.success(deptService.selectDeptById(deptId));
	}

	/**
	 * 獲取部門下拉樹列表
	 */
	@GetMapping("/treeselect")
	public AjaxResult treeselect(SysDept dept) {
		List<SysDept> depts = deptService.selectDeptList(dept);
		return AjaxResult.success(deptService.buildDeptTreeSelect(depts));
	}

	/**
	 * 載入對應角色部門列表樹
	 */
	@GetMapping(value = "/roleDeptTreeselect/{roleId}")
	public AjaxResult roleDeptTreeselect(@PathVariable("roleId") Long roleId) {
		List<SysDept> depts = deptService.selectDeptList(new SysDept());
		AjaxResult ajax = AjaxResult.success();
		ajax.put("checkedKeys", deptService.selectDeptListByRoleId(roleId));
		ajax.put("depts", deptService.buildDeptTreeSelect(depts));
		return ajax;
	}

	/**
	 * 新增部門
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:add')")
	@Log(title = "部門管理", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@Validated @RequestBody SysDept dept) {
		if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
			return AjaxResult.error("新增部門'" + dept.getDeptName() + "'失敗，部門名稱已存在");
		}
		dept.setCreateBy(SecurityUtils.getUsername());
		return toAjax(deptService.insertDept(dept));
	}

	/**
	 * 修改部門
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:edit')")
	@Log(title = "部門管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@Validated @RequestBody SysDept dept) {
		if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
			return AjaxResult.error("修改部門'" + dept.getDeptName() + "'失敗，部門名稱已存在");
		} else if (dept.getParentId().equals(dept.getDeptId())) {
			return AjaxResult.error("修改部門'" + dept.getDeptName() + "'失敗，上級部門不能是自己");
		} else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus())
				&& deptService.selectNormalChildrenDeptById(dept.getDeptId()) > 0) {
			return AjaxResult.error("該部門包含未停用的子部門！");
		}
		dept.setUpdateBy(SecurityUtils.getUsername());
		return toAjax(deptService.updateDept(dept));
	}

	/**
	 * 刪除部門
	 */
	@PreAuthorize("@ss.hasPermi('system:dept:remove')")
	@Log(title = "部門管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{deptId}")
	public AjaxResult remove(@PathVariable Long deptId) {
		if (deptService.hasChildByDeptId(deptId)) {
			return AjaxResult.error("存在下級部門,不允許刪除");
		}
		if (deptService.checkDeptExistUser(deptId)) {
			return AjaxResult.error("部門存在員工,不允許刪除");
		}
		return toAjax(deptService.deleteDeptById(deptId));
	}
}
