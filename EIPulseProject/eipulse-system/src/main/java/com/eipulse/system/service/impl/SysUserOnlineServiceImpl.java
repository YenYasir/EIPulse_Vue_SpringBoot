package com.eipulse.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.common.core.domain.model.LoginUser;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.system.dto.SysUserOnline;
import com.eipulse.system.service.ISysUserOnlineService;

/**
 * 在線員工 服務層處理
 */
@Transactional(readOnly = true)
@Service
public class SysUserOnlineServiceImpl implements ISysUserOnlineService {
	/**
	 * 通過登入位址查詢資訊
	 *
	 * @param ipaddr 登入位址
	 * @param user   員工資訊
	 * @return 在線員工資訊
	 */
	@Override
	public SysUserOnline selectOnlineByIpaddr(String ipaddr, LoginUser user) {
		if (StringUtils.equals(ipaddr, user.getIpaddr())) {
			return loginUserToUserOnline(user);
		}
		return null;
	}

	/**
	 * 通過員工名稱查詢資訊
	 *
	 * @param userName 員工名稱
	 * @param user     員工資訊
	 * @return 在線員工資訊
	 */
	@Override
	public SysUserOnline selectOnlineByUserName(String userName, LoginUser user) {
		if (StringUtils.equals(userName, user.getUsername())) {
			return loginUserToUserOnline(user);
		}
		return null;
	}

	/**
	 * 通過登入位址/員工名稱查詢資訊
	 *
	 * @param ipaddr   登入位址
	 * @param userName 員工名稱
	 * @param user     員工資訊
	 * @return 在線員工資訊
	 */
	@Override
	public SysUserOnline selectOnlineByInfo(String ipaddr, String userName, LoginUser user) {
		if (StringUtils.equals(ipaddr, user.getIpaddr()) && StringUtils.equals(userName, user.getUsername())) {
			return loginUserToUserOnline(user);
		}
		return null;
	}

	/**
	 * 設置在線員工資訊
	 *
	 * @param user 員工資訊
	 * @return 在線員工
	 */
	@Override
	public SysUserOnline loginUserToUserOnline(LoginUser user) {
		if (StringUtils.isNull(user) || StringUtils.isNull(user.getUser())) {
			return null;
		}
		SysUserOnline sysUserOnline = new SysUserOnline();
		sysUserOnline.setTokenId(user.getToken());
		sysUserOnline.setUserName(user.getUsername());
		sysUserOnline.setIpaddr(user.getIpaddr());
		sysUserOnline.setLoginLocation(user.getLoginLocation());
		sysUserOnline.setBrowser(user.getBrowser());
		sysUserOnline.setOs(user.getOs());
		sysUserOnline.setLoginTime(user.getLoginTime());
		if (StringUtils.isNotNull(user.getUser().getDept())) {
			sysUserOnline.setDeptName(user.getUser().getDept().getDeptName());
		}
		return sysUserOnline;
	}
}
