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
import com.eipulse.common.core.domain.entity.SysMenu;
import com.eipulse.common.enums.BusinessType;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.system.service.SysMenuService;

/**
 * 菜單資訊
 * 
 * @author eipulse
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {
	@Autowired
	private SysMenuService menuService;

	/**
	 * 獲取菜單列表
	 */
	@PreAuthorize("@ss.hasPermi('system:menu:list')")
	@GetMapping("/list")
	public AjaxResult list(SysMenu menu) {
		List<SysMenu> menus = menuService.findMenuList(menu, getUserId());
		return AjaxResult.success(menus);
	}

	/**
	 * 根據菜單編號獲取詳細資訊
	 */
	@PreAuthorize("@ss.hasPermi('system:menu:query')")
	@GetMapping(value = "/{menuId}")
	public AjaxResult getInfo(@PathVariable Long menuId) {
		return AjaxResult.success(menuService.getObject(menuId));
	}

	/**
	 * 獲取菜單下拉樹列表
	 */
	@GetMapping("/treeselect")
	public AjaxResult treeselect(SysMenu menu) {
		List<SysMenu> menus = menuService.findMenuList(menu, getUserId());
		return AjaxResult.success(menuService.buildMenuTreeSelect(menus));
	}

	/**
	 * 加載對應角色菜單列表樹
	 */
	@GetMapping(value = "/roleMenuTreeselect/{roleId}")
	public AjaxResult roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
		List<SysMenu> menus = menuService.findMenuList(getUserId());
		AjaxResult ajax = AjaxResult.success();
		ajax.put("checkedKeys", menuService.findMenuListByRoleId(roleId));
		ajax.put("menus", menuService.buildMenuTreeSelect(menus));
		return ajax;
	}

	/**
	 * 新增菜單
	 */
	@PreAuthorize("@ss.hasPermi('system:menu:add')")
	@Log(title = "菜單管理", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@Validated @RequestBody SysMenu menu) {
		if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
			return AjaxResult.error("新增菜單'" + menu.getMenuName() + "'失敗，菜單名稱已存在");
		} else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
			return AjaxResult.error("新增菜單'" + menu.getMenuName() + "'失敗，地址必須以http(s)://開頭");
		}
		menu.setCreateBy(getUsername());
		menuService.addObject(menu);
		return AjaxResult.success();
	}

	/**
	 * 修改菜單
	 */
	@PreAuthorize("@ss.hasPermi('system:menu:edit')")
	@Log(title = "菜單管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@Validated @RequestBody SysMenu menu) {
		if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
			return AjaxResult.error("修改菜單'" + menu.getMenuName() + "'失敗，菜單名稱已存在");
		} else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
			return AjaxResult.error("修改菜單'" + menu.getMenuName() + "'失敗，地址必須以http(s)://開頭");
		} else if (menu.getMenuId().equals(menu.getParentId())) {
			return AjaxResult.error("修改菜單'" + menu.getMenuName() + "'失敗，上級菜單不能選擇自己");
		}
		menu.setUpdateBy(getUsername());
		menuService.updateObject(menu);
		return AjaxResult.success();
	}

	/**
	 * 刪除菜單
	 */
	@PreAuthorize("@ss.hasPermi('system:menu:remove')")
	@Log(title = "菜單管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{menuId}")
	public AjaxResult remove(@PathVariable("menuId") Long menuId) {
		if (menuService.hasChildByMenuId(menuId)) {
			return AjaxResult.error("存在子菜單,不允許刪除");
		}
		if (menuService.checkMenuExistRole(menuId)) {
			return AjaxResult.error("菜單已分配,不允許刪除");
		}
		menuService.deleteObject(menuId);
		return AjaxResult.success();
	}
}