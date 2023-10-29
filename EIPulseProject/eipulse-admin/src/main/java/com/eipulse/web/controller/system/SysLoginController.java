package com.eipulse.web.controller.system;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.common.constant.Constants;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.core.domain.entity.SysMenu;
import com.eipulse.common.core.domain.entity.SysUser;
import com.eipulse.common.core.domain.model.LoginBody;
import com.eipulse.common.core.domain.model.LoginUser;
import com.eipulse.common.utils.ServletUtils;
import com.eipulse.framework.web.service.SysLoginService;
import com.eipulse.framework.web.service.SysPermissionService;
import com.eipulse.framework.web.service.TokenService;
import com.eipulse.system.dto.RouterVo;
import com.eipulse.system.service.ISysMenuService;

/**
 * 登入驗證
 */
@RestController
public class SysLoginController {
	@Autowired
	private SysLoginService loginService;

	@Autowired
	private ISysMenuService menuService;

	@Autowired
	private SysPermissionService permissionService;

	@Autowired
	private TokenService tokenService;

	/**
	 * 登入方法
	 *
	 * @param loginBody 登入資訊
	 * @return 結果
	 */
	@PostMapping("/login")
	public AjaxResult login(@RequestBody LoginBody loginBody) {
		AjaxResult ajax = AjaxResult.success();
		// 生成令牌
		String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
				loginBody.getUuid());
		ajax.put(Constants.TOKEN, token);
		return ajax;
	}

	/**
	 * 獲取員工資訊
	 *
	 * @return 員工資訊
	 */
	@GetMapping("getInfo")
	public AjaxResult getInfo() {
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		SysUser user = loginUser.getUser();
		// 角色集合
		Set<String> roles = permissionService.getRolePermission(user);
		// 權限集合
		Set<String> permissions = permissionService.getMenuPermission(user);
		AjaxResult ajax = AjaxResult.success();
		ajax.put("user", user);
		ajax.put("roles", roles);
		ajax.put("permissions", permissions);
		return ajax;
	}

	/**
	 * 獲取路由資訊
	 *
	 * @return 路由資訊
	 */
	@GetMapping("getRouters")
	public AjaxResult getRouters() {
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		// 員工資訊
		SysUser user = loginUser.getUser();
		List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId());
		List<RouterVo> routerVos = menuService.buildMenus(menus);
		return AjaxResult.success(routerVos);
	}
}
