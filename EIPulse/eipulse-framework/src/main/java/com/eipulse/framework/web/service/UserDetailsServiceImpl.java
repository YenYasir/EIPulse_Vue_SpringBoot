package com.eipulse.framework.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eipulse.common.core.domain.entity.SysUser;
import com.eipulse.common.core.domain.model.LoginUser;
import com.eipulse.common.enums.UserStatus;
import com.eipulse.common.exception.ServiceException;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.system.service.SysUserService;

/**
 * 員工驗證處理
 *
 * @author eipulse
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private SysUserService userService;

	@Autowired
	private SysPermissionService permissionService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser user = userService.selectUserByUserName(username);
		if (StringUtils.isNull(user)) {
			log.info("登入員工：{} 不存在.", username);
			throw new ServiceException("登入員工：" + username + " 不存在");
		} else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
			log.info("登入員工：{} 已被刪除.", username);
			throw new ServiceException("對不起，您的帳號：" + username + " 已被刪除");
		} else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
			log.info("登入員工：{} 已被停用.", username);
			throw new ServiceException("對不起，您的帳號：" + username + " 已停用");
		}

		return createLoginUser(user);
	}

	public UserDetails createLoginUser(SysUser user) {
		return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
	}
}
