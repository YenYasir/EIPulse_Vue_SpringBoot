package com.eipulse.web.controller.system;

import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.multipart.MultipartFile;

import com.eipulse.common.annotation.Log;
import com.eipulse.common.constant.UserConstants;
import com.eipulse.common.core.controller.BaseController;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.core.domain.entity.SysRole;
import com.eipulse.common.core.domain.entity.SysUser;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.page.TableDataInfo;
import com.eipulse.common.enums.BusinessType;
import com.eipulse.common.utils.SecurityUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.poi.ExcelUtil;
import com.eipulse.system.service.SysPostService;
import com.eipulse.system.service.SysRoleService;
import com.eipulse.system.service.SysUserService;

/**
 * 員工資訊
 * 
 * @author eipulse
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
	@Autowired
	private SysUserService userService;

	@Autowired
	private SysRoleService roleService;

	@Autowired
	private SysPostService postService;

	/**
	 * 獲取員工列表
	 */
	@PreAuthorize("@ss.hasPermi('system:user:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysUser user) {
		Page page = userService.findUserList(user);
		return getDataTable(page);
	}

	@Log(title = "員工管理", businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasPermi('system:user:export')")
	@GetMapping("/export")
	public AjaxResult export(SysUser user) {
		Page page = userService.findUserList(user);
		ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
		return util.exportExcel(page.getItems(), "員工數據");
	}

	@Log(title = "員工管理", businessType = BusinessType.IMPORT)
	@PreAuthorize("@ss.hasPermi('system:user:import')")
	@PostMapping("/importData")
	public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
		ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
		List<SysUser> userList = util.importExcel(file.getInputStream());
		String operName = getUsername();
		String message = userService.importUser(userList, updateSupport, operName);
		return AjaxResult.success(message);
	}

	@GetMapping("/importTemplate")
	public AjaxResult importTemplate() {
		ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
		return util.importTemplateExcel("員工數據");
	}

	/**
	 * 根據員工編號獲取詳細資訊
	 */
	@PreAuthorize("@ss.hasPermi('system:user:query')")
	@GetMapping(value = { "/", "/{userId}" })
	public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
		userService.checkUserDataScope(userId);
		AjaxResult ajax = AjaxResult.success();
		List<SysRole> roles = roleService.findRoleAll();
		ajax.put("roles", SysUser.isAdmin(userId) ? roles
				: roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
		ajax.put("posts", postService.getObjects());
		if (StringUtils.isNotNull(userId)) {
			ajax.put(AjaxResult.DATA_TAG, userService.getObject(userId));
			ajax.put("postIds", postService.findPostListByUserId(userId));
			ajax.put("roleIds", roleService.findRoleListByUserId(userId));
		}
		return ajax;
	}

	/**
	 * 新增員工
	 */
	@PreAuthorize("@ss.hasPermi('system:user:add')")
	@Log(title = "員工管理", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@Validated @RequestBody SysUser user) {
		if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
			return AjaxResult.error("新增員工'" + user.getUserName() + "'失敗，登錄賬號已存在");
		} else if (StringUtils.isNotEmpty(user.getPhonenumber())
				&& UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
			return AjaxResult.error("新增員工'" + user.getUserName() + "'失敗，手機號碼已存在");
		} else if (StringUtils.isNotEmpty(user.getEmail())
				&& UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
			return AjaxResult.error("新增員工'" + user.getUserName() + "'失敗，郵箱賬號已存在");
		}
		user.setCreateBy(getUsername());
		user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
		userService.insertUser(user);
		return AjaxResult.success();
	}

	/**
	 * 修改員工
	 */
	@PreAuthorize("@ss.hasPermi('system:user:edit')")
	@Log(title = "員工管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@Validated @RequestBody SysUser user) {
		userService.checkUserAllowed(user);
		if (StringUtils.isNotEmpty(user.getPhonenumber())
				&& UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
			return AjaxResult.error("修改員工'" + user.getUserName() + "'失敗，手機號碼已存在");
		} else if (StringUtils.isNotEmpty(user.getEmail())
				&& UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
			return AjaxResult.error("修改員工'" + user.getUserName() + "'失敗，郵箱賬號已存在");
		}
		user.setUpdateBy(getUsername());
		userService.updateUser(user);
		return AjaxResult.success();
	}

	/**
	 * 刪除員工
	 */
	@PreAuthorize("@ss.hasPermi('system:user:remove')")
	@Log(title = "員工管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
	public AjaxResult remove(@PathVariable Long[] userIds) {
		if (ArrayUtils.contains(userIds, getUserId())) {
			return error("當前員工不能刪除");
		}
		userService.deleteUserByIds(userIds);
		return AjaxResult.success();
	}

	/**
	 * 重置密碼
	 */
	@PreAuthorize("@ss.hasPermi('system:user:resetPwd')")
	@Log(title = "員工管理", businessType = BusinessType.UPDATE)
	@PutMapping("/resetPwd")
	public AjaxResult resetPwd(@RequestBody SysUser user) {
		userService.checkUserAllowed(user);
		user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
		user.setUpdateBy(getUsername());
		userService.resetUserPwd(user.getUserName(), user.getPassword());
		return AjaxResult.success();
	}

	/**
	 * 狀態修改
	 */
	@PreAuthorize("@ss.hasPermi('system:user:edit')")
	@Log(title = "員工管理", businessType = BusinessType.UPDATE)
	@PutMapping("/changeStatus")
	public AjaxResult changeStatus(@RequestBody SysUser user) {
		userService.checkUserAllowed(user);
		user.setUpdateBy(getUsername());
		userService.updateUserStatus(user);
		return AjaxResult.success();
	}

	/**
	 * 根據員工編號獲取授權角色
	 */
	@PreAuthorize("@ss.hasPermi('system:user:query')")
	@GetMapping("/authRole/{userId}")
	public AjaxResult authRole(@PathVariable("userId") Long userId) {
		AjaxResult ajax = AjaxResult.success();
		SysUser user = (SysUser) userService.getObject(userId);
		List<SysRole> roles = roleService.findRolesByUserId(userId);
		ajax.put("user", user);
		ajax.put("roles", SysUser.isAdmin(userId) ? roles
				: roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
		return ajax;
	}

	/**
	 * 員工授權角色
	 */
	@PreAuthorize("@ss.hasPermi('system:user:edit')")
	@Log(title = "員工管理", businessType = BusinessType.GRANT)
	@PutMapping("/authRole")
	public AjaxResult insertAuthRole(Long userId, Long[] roleIds) {
		userService.insertUserAuth(userId, roleIds);
		return success();
	}
}
