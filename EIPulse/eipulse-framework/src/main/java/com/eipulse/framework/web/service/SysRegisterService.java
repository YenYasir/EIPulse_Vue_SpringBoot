package com.eipulse.framework.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eipulse.common.constant.Constants;
import com.eipulse.common.constant.UserConstants;
import com.eipulse.common.core.domain.entity.SysUser;
import com.eipulse.common.core.domain.model.RegisterBody;
import com.eipulse.common.core.redis.RedisCache;
import com.eipulse.common.exception.user.CaptchaException;
import com.eipulse.common.exception.user.CaptchaExpireException;
import com.eipulse.common.utils.MessageUtils;
import com.eipulse.common.utils.SecurityUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.framework.manager.AsyncManager;
import com.eipulse.framework.manager.factory.AsyncFactory;
import com.eipulse.system.service.SysConfigService;
import com.eipulse.system.service.SysUserService;

/**
 * 註冊校驗方法
 * 
 * @author eipulse
 */
@Component
public class SysRegisterService {
	@Autowired
	private SysUserService userService;

	@Autowired
	private SysConfigService configService;

	@Autowired
	private RedisCache redisCache;

	/**
	 * 註冊
	 */
	public String register(RegisterBody registerBody) {
		String msg = "", username = registerBody.getUsername(), password = registerBody.getPassword();

		boolean captchaOnOff = configService.selectCaptchaOnOff();
		// 驗證碼開關
		if (captchaOnOff) {
			validateCaptcha(username, registerBody.getCode(), registerBody.getUuid());
		}

		if (StringUtils.isEmpty(username)) {
			msg = "員工名不能為空";
		} else if (StringUtils.isEmpty(password)) {
			msg = "員工密碼不能為空";
		} else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
				|| username.length() > UserConstants.USERNAME_MAX_LENGTH) {
			msg = "帳戶長度必須在2到20個字符之間";
		} else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
				|| password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
			msg = "密碼長度必須在5到20個字符之間";
		} else if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(username))) {
			msg = "保存員工'" + username + "'失敗，註冊帳號已存在";
		} else {
			SysUser sysUser = new SysUser();
			sysUser.setUserName(username);
			sysUser.setNickName(username);
			sysUser.setPassword(SecurityUtils.encryptPassword(registerBody.getPassword()));
			boolean regFlag = userService.registerUser(sysUser);
			if (!regFlag) {
				msg = "註冊失敗,請聯繫系統管理人員";
			} else {
				AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.REGISTER,
						MessageUtils.message("user.register.success")));
			}
		}
		return msg;
	}

	/**
	 * 校驗驗證碼
	 * 
	 * @param username 員工名
	 * @param code     驗證碼
	 * @param uuid     唯一標識
	 * @return 結果
	 */
	public void validateCaptcha(String username, String code, String uuid) {
		String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
		String captcha = redisCache.getCacheObject(verifyKey);
		redisCache.deleteObject(verifyKey);
		if (captcha == null) {
			throw new CaptchaExpireException();
		}
		if (!code.equalsIgnoreCase(captcha)) {
			throw new CaptchaException();
		}
	}
}
