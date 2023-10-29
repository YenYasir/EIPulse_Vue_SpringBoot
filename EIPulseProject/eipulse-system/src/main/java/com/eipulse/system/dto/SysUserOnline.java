package com.eipulse.system.dto;

import lombok.Data;

/**
 * 當前在線會話
 */

@Data
public class SysUserOnline {

	/**
	 * 會話編號
	 */
	private String tokenId;

	/**
	 * 部門名稱
	 */
	private String deptName;

	/**
	 * 員工名稱
	 */
	private String userName;

	/**
	 * 登入IP位址
	 */
	private String ipaddr;

	/**
	 * 登入位址
	 */
	private String loginLocation;

	/**
	 * 瀏覽器類型
	 */
	private String browser;

	/**
	 * 操作系統
	 */
	private String os;

	/**
	 * 登入時間
	 */
	private Long loginTime;

}
