package com.eipulse.system.service;

import com.eipulse.common.core.domain.model.LoginUser;
import com.eipulse.system.dto.SysUserOnline;

/**
 * 在線員工 服務層
 */
public interface ISysUserOnlineService {
	/**
	 * 通過登入位址查詢資訊
	 *
	 * @param ipaddr 登入位址
	 * @param user   員工資訊
	 * @return 在線員工資訊
	 */
	SysUserOnline selectOnlineByIpaddr(String ipaddr, LoginUser user);

	/**
	 * 通過員工名稱查詢資訊
	 *
	 * @param userName 員工名稱
	 * @param user     員工資訊
	 * @return 在線員工資訊
	 */
	SysUserOnline selectOnlineByUserName(String userName, LoginUser user);

	/**
	 * 通過登入位址/員工名稱查詢資訊
	 *
	 * @param ipaddr   登入位址
	 * @param userName 員工名稱
	 * @param user     員工資訊
	 * @return 在線員工資訊
	 */
	SysUserOnline selectOnlineByInfo(String ipaddr, String userName, LoginUser user);

	/**
	 * 設置在線員工資訊
	 *
	 * @param user 員工資訊
	 * @return 在線員工
	 */
	SysUserOnline loginUserToUserOnline(LoginUser user);
}
