package com.eipulse.common.core.dto;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

/**
 * 菜單權限表 sys_menu
 */
@Data
public class SysMenuDto {

	/**
	 * 菜單ID
	 */
	private BigInteger menuId;

	/**
	 * 菜單名稱
	 */
	private String menuName;

	/**
	 * 父菜單ID
	 */
	private BigInteger parentId;

	/**
	 * 顯示順序
	 */
	private Integer orderNum;

	/**
	 * 路由位址
	 */
	private String path;

	/**
	 * 組件路徑
	 */
	private String component;

	/**
	 * 是否為外鏈（0是 1否）
	 */
	private Integer isFrame;

	/**
	 * 是否快取（0快取 1不快取）
	 */
	private Integer isCache;

	/**
	 * 類型（M目錄 C菜單 F按鈕）
	 */
	private Character menuType;

	/**
	 * 顯示狀態（0顯示 1隱藏）
	 */
	private Character visible;

	/**
	 * 菜單狀態（0顯示 1隱藏）
	 */
	private Character status;

	/**
	 * 權限字串
	 */
	private String perms;

	/**
	 * 菜單圖示
	 */
	private String icon;

	/**
	 * 創建者
	 */
	private String createBy;

	/**
	 * 創建時間
	 */
	private Date createTime;

	/**
	 * 更新者
	 */
	private String updateBy;

	/**
	 * 更新時間
	 */
	private Date updateTime;

	/**
	 * 備註
	 */
	private String remark;

}
