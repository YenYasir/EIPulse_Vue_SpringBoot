package com.eipulse.web.controller.system;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eipulse.common.annotation.Log;
import com.eipulse.common.config.EipulseConfig;
import com.eipulse.common.core.controller.BaseController;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.core.domain.entity.SysUser;
import com.eipulse.common.core.domain.model.LoginUser;
import com.eipulse.common.enums.BusinessType;
import com.eipulse.common.utils.SecurityUtils;
import com.eipulse.common.utils.ServletUtils;
import com.eipulse.common.utils.file.FileUploadUtils;
import com.eipulse.framework.web.service.TokenService;
import com.eipulse.system.service.ISysUserService;

/**
 * 個人資訊 業務處理
 */
@RestController
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController {
	@Autowired
	private ISysUserService userService;

	@Autowired
	private TokenService tokenService;

	/**
	 * 個人資訊
	 */
	@GetMapping
	public AjaxResult profile() {
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		SysUser user = loginUser.getUser();
		AjaxResult ajax = AjaxResult.success(user);
		ajax.put("roleGroup", userService.selectUserRoleGroup(loginUser.getUsername()));
		ajax.put("postGroup", userService.selectUserPostGroup(loginUser.getUsername()));
		return ajax;
	}

	/**
	 * 修改員工
	 */
	@Log(title = "個人資訊", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult updateProfile(@RequestBody SysUser user) {
		if (userService.updateUserProfile(user) > 0) {
			LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
			// 更新快取員工資訊
			loginUser.getUser().setNickName(user.getNickName());
			loginUser.getUser().setPhonenumber(user.getPhonenumber());
			loginUser.getUser().setEmail(user.getEmail());
			loginUser.getUser().setSex(user.getSex());
			tokenService.setLoginUser(loginUser);
			return AjaxResult.success();
		}
		return AjaxResult.error("修改個人資訊異常，請聯絡管理員");
	}

	/**
	 * 重設密碼
	 */
	@Log(title = "個人資訊", businessType = BusinessType.UPDATE)
	@PutMapping("/updatePwd")
	public AjaxResult updatePwd(String oldPassword, String newPassword) {
		LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
		String userName = loginUser.getUsername();
		String password = loginUser.getPassword();
		if (!SecurityUtils.matchesPassword(oldPassword, password)) {
			return AjaxResult.error("修改密碼失敗，舊密碼錯誤");
		}
		if (SecurityUtils.matchesPassword(newPassword, password)) {
			return AjaxResult.error("新密碼不能與舊密碼相同");
		}
		if (userService.resetUserPwd(userName, SecurityUtils.encryptPassword(newPassword)) > 0) {
			// 更新快取員工密碼
			loginUser.getUser().setPassword(SecurityUtils.encryptPassword(newPassword));
			tokenService.setLoginUser(loginUser);
			return AjaxResult.success();
		}
		return AjaxResult.error("修改密碼異常，請聯絡管理員");
	}

	/**
	 * 頭像上傳
	 */
	@Log(title = "員工頭像", businessType = BusinessType.UPDATE)
	@PostMapping("/avatar")
	public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
			String avatar = FileUploadUtils.upload(EipulseConfig.getAvatarPath(), file);
			if (userService.updateUserAvatar(loginUser.getUsername(), avatar)) {
				AjaxResult ajax = AjaxResult.success();
				ajax.put("imgUrl", avatar);
				// 更新快取員工頭像
				loginUser.getUser().setAvatar(avatar);
				tokenService.setLoginUser(loginUser);
				return ajax;
			}
		}
		return AjaxResult.error("上傳圖片異常，請聯絡管理員");
	}
}
