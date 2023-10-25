package com.eipulse.web.controller.system;

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
import com.eipulse.common.core.domain.entity.SysRole;
import com.eipulse.common.core.domain.entity.SysUser;
import com.eipulse.common.core.domain.model.LoginUser;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.page.TableDataInfo;
import com.eipulse.common.enums.BusinessType;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.poi.ExcelUtil;
import com.eipulse.framework.web.service.SysPermissionService;
import com.eipulse.framework.web.service.TokenService;
import com.eipulse.system.domain.SysUserRole;
import com.eipulse.system.service.SysRoleService;
import com.eipulse.system.service.SysUserService;

/**
 * 角色資訊
 * 
 * @author eipulse
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {
	@Autowired
	private SysRoleService roleService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private SysPermissionService permissionService;

	@Autowired
	private SysUserService userService;

	@PreAuthorize("@ss.hasPermi('system:role:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysRole role) {
		Page page = roleService.findRoleList(role);
		return getDataTable(page);
	}

	@Log(title = "角色管理", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('system:role:export')")
	@GetMapping("/export")
	public AjaxResult export(SysRole role) {
		Page page = roleService.findRoleList(role);
		ExcelUtil<SysRole> util = new ExcelUtil<SysRole>(SysRole.class);
		return util.exportExcel(page.getItems(), "角色數據");
	}

	/**
	 * 根據角色編號獲取詳細資訊
	 */
	@PreAuthorize("@ss.hasPermi('system:role:query')")
	@GetMapping(value = "/{roleId}")
	public AjaxResult getInfo(@PathVariable Long roleId) {
		roleService.checkRoleDataScope(roleId);
		return AjaxResult.success(roleService.getObject(roleId));
	}

	/**
	 * 新增角色
	 */
	@PreAuthorize("@ss.hasPermi('system:role:add')")
	@Log(title = "角色管理", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@Validated @RequestBody SysRole role) {
		if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
			return AjaxResult.error("新增角色'" + role.getRoleName() + "'失敗，角色名稱已存在");
		} else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
			return AjaxResult.error("新增角色'" + role.getRoleName() + "'失敗，角色權限已存在");
		}
		role.setCreateBy(getUsername());
		return toAjax(roleService.insertRole(role));

	}

	/**
	 * 修改保存角色
	 */
	@PreAuthorize("@ss.hasPermi('system:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@Validated @RequestBody SysRole role) {
		roleService.checkRoleAllowed(role);
		if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
			return AjaxResult.error("修改角色'" + role.getRoleName() + "'失敗，角色名稱已存在");
		} else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
			return AjaxResult.error("修改角色'" + role.getRoleName() + "'失敗，角色權限已存在");
		}
		role.setUpdateBy(getUsername());

		if (roleService.updateRole(role) > 0) {
			// 更新緩存用戶權限
			LoginUser loginUser = getLoginUser();
			if (StringUtils.isNotNull(loginUser.getUser()) && !loginUser.getUser().isAdmin()) {
				loginUser.setPermissions(permissionService.getMenuPermission(loginUser.getUser()));
				loginUser.setUser(userService.selectUserByUserName(loginUser.getUser().getUserName()));
				tokenService.setLoginUser(loginUser);
			}
			return AjaxResult.success();
		}
		return AjaxResult.error("修改角色'" + role.getRoleName() + "'失敗，請聯繫管理員");
	}

	/**
	 * 修改保存數據權限
	 */
	@PreAuthorize("@ss.hasPermi('system:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.UPDATE)
	@PutMapping("/dataScope")
	public AjaxResult dataScope(@RequestBody SysRole role) {
		roleService.checkRoleAllowed(role);
		roleService.authDataScope(role);
		return AjaxResult.success();
	}

	/**
	 * 狀態修改
	 */
	@PreAuthorize("@ss.hasPermi('system:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.UPDATE)
	@PutMapping("/changeStatus")
	public AjaxResult changeStatus(@RequestBody SysRole role) {
		roleService.checkRoleAllowed(role);
		role.setUpdateBy(getUsername());
		roleService.updateRoleStatus(role);
		return AjaxResult.success();
	}

	/**
	 * 刪除角色
	 */
	@PreAuthorize("@ss.hasPermi('system:role:remove')")
	@Log(title = "角色管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{roleIds}")
	public AjaxResult remove(@PathVariable Long[] roleIds) {
		roleService.deleteRoleByIds(roleIds);
		return AjaxResult.success();
	}

	/**
	 * 獲取角色選擇框列表
	 */
	@PreAuthorize("@ss.hasPermi('system:role:query')")
	@GetMapping("/optionselect")
	public AjaxResult optionselect() {
		return AjaxResult.success(roleService.findRoleAll());
	}

	/**
	 * 查詢已分配用戶角色列表
	 */
	@PreAuthorize("@ss.hasPermi('system:role:list')")
	@GetMapping("/authUser/allocatedList")
	public TableDataInfo allocatedList(SysUser user) {
		Page page = userService.findAllocatedList(user);
		return getDataTable(page);
	}

	/**
	 * 查詢未分配用戶角色列表
	 */
	@PreAuthorize("@ss.hasPermi('system:role:list')")
	@GetMapping("/authUser/unallocatedList")
	public TableDataInfo unallocatedList(SysUser user) {
		Page page = userService.findUnallocatedList(user);
		return getDataTable(page);
	}

	/**
	 * 取消授權用戶
	 */
	@PreAuthorize("@ss.hasPermi('system:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.GRANT)
	@PutMapping("/authUser/cancel")
	public AjaxResult cancelAuthUser(@RequestBody SysUserRole userRole) {
		return toAjax(roleService.deleteAuthUser(userRole));
	}

	/**
	 * 批量取消授權用戶
	 */
	@PreAuthorize("@ss.hasPermi('system:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.GRANT)
	@PutMapping("/authUser/cancelAll")
	public AjaxResult cancelAuthUserAll(Long roleId, Long[] userIds) {
		return toAjax(roleService.deleteAuthUsers(roleId, userIds));
	}

	/**
	 * 批量選擇用戶授權
	 */
	@PreAuthorize("@ss.hasPermi('system:role:edit')")
	@Log(title = "角色管理", businessType = BusinessType.GRANT)
	@PutMapping("/authUser/selectAll")
	public AjaxResult selectAuthUserAll(Long roleId, Long[] userIds) {
		roleService.insertAuthUsers(roleId, userIds);
		return AjaxResult.success();
	}
}
