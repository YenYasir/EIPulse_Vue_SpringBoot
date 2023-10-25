package com.eipulse.framework.web.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.eipulse.common.constant.Constants;
import com.eipulse.common.core.domain.entity.SysUser;
import com.eipulse.common.core.domain.model.LoginUser;
import com.eipulse.common.core.redis.RedisCache;
import com.eipulse.common.exception.ServiceException;
import com.eipulse.common.exception.user.CaptchaException;
import com.eipulse.common.exception.user.CaptchaExpireException;
import com.eipulse.common.exception.user.UserPasswordNotMatchException;
import com.eipulse.common.utils.DateUtils;
import com.eipulse.common.utils.MessageUtils;
import com.eipulse.common.utils.ServletUtils;
import com.eipulse.common.utils.ip.IpUtils;
import com.eipulse.framework.manager.AsyncManager;
import com.eipulse.framework.manager.factory.AsyncFactory;
import com.eipulse.system.service.SysConfigService;
import com.eipulse.system.service.SysUserService;

/**
 * 登入校驗方法
 * 
 * @author eipulse
 */
@Component
public class SysLoginService {
	@Autowired
	private TokenService tokenService;

	@Resource
	private AuthenticationManager authenticationManager;

	@Autowired
	private RedisCache redisCache;

	@Autowired
	private SysUserService userService;

	@Autowired
	private SysConfigService configService;

	/**
	 * 登入驗證
	 * 
	 * @param username 員工名
	 * @param password 密碼
	 * @param code     驗證碼
	 * @param uuid     唯一標識
	 * @return 結果
	 */
	public String login(String username, String password, String code, String uuid) {
		boolean captchaOnOff = configService.selectCaptchaOnOff();
		// 驗證碼開關
		if (captchaOnOff) {
			validateCaptcha(username, code, uuid);
		}
		// 員工驗證
		Authentication authentication = null;
		try {
			// 該方法會去調用UserDetailsServiceImpl.loadUserByUsername
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (Exception e) {
			if (e instanceof BadCredentialsException) {
				AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
						MessageUtils.message("user.password.not.match")));
				throw new UserPasswordNotMatchException();
			} else {
				AsyncManager.me()
						.execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
				throw new ServiceException(e.getMessage());
			}
		}
		AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS,
				MessageUtils.message("user.login.success")));
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		recordLoginInfo(loginUser.getUserId());
		// 生成token
		return tokenService.createToken(loginUser);
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
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
					MessageUtils.message("user.jcaptcha.expire")));
			throw new CaptchaExpireException();
		}
		if (!code.equalsIgnoreCase(captcha)) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
					MessageUtils.message("user.jcaptcha.error")));
			throw new CaptchaException();
		}
	}

	/**
	 * 記錄登入資訊
	 *
	 * @param userId 員工ID
	 */
	public void recordLoginInfo(Long userId) {
		// SysUser sysUser = new SysUser();
		SysUser sysUser = (SysUser) userService.getObject(userId);
		// sysUser.setUserId(userId);
		sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
		sysUser.setLoginDate(DateUtils.getNowDate());
		userService.updateUserProfile(sysUser);
	}
}
