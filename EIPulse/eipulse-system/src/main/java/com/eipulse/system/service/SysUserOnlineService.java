package com.eipulse.system.service;

import org.springframework.stereotype.Service;

import com.eipulse.common.core.domain.model.LoginUser;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.system.domain.SysUserOnline;

/**
 * 在線員工 服務層處理
 * 
 * @author eipulse
 */
@Service
public class SysUserOnlineService {
	/**
	 * 通過登入地址查詢資訊
	 * 
	 * @param ipaddr 登入地址
	 * @param user   員工資訊
	 * @return 在線員工資訊
	 */
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
	public SysUserOnline selectOnlineByUserName(String userName, LoginUser user) {
		if (StringUtils.equals(userName, user.getUsername())) {
			return loginUserToUserOnline(user);
		}
		return null;
	}

	/**
	 * 通過登入地址/員工名稱查詢資訊
	 * 
	 * @param ipaddr   登入地址
	 * @param userName 員工名稱
	 * @param user     員工資訊
	 * @return 在線員工資訊
	 */
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
