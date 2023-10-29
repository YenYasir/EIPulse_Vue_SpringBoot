package com.eipulse.common.core.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.eipulse.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 菜單權限表 sys_menu
 */
@Entity
@Table(name = "sys_menu")
@Data
public class SysMenu extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 菜單ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "menu_id")
	private Long menuId;

	/**
	 * 菜單名稱
	 */
	@NotBlank(message = "菜單名稱不能為空")
	@Size(min = 0, max = 50, message = "菜單名稱長度不能超過50個字元")
	@Column(name = "menu_name")
	private String menuName;

	/**
	 * 父菜單名稱
	 */
	@Transient
	private String parentName;

	/**
	 * 父菜單ID
	 */
	@Column(name = "parent_id")
	private Long parentId;

	/**
	 * 顯示順序
	 */
	@NotNull(message = "顯示順序不能為空")
	@Column(name = "order_num")
	private Integer orderNum;

	/**
	 * 路由位址
	 */
	@Size(min = 0, max = 200, message = "路由位址不能超過200個字元")
	@Column(name = "path")
	private String path;

	/**
	 * 組件路徑
	 */
	@Size(min = 0, max = 200, message = "組件路徑不能超過255個字元")
	@Column(name = "component")
	private String component;

	/**
	 * 是否為外鏈（0是 1否）
	 */
	@Column(name = "is_frame")
	private Integer isFrame;

	/**
	 * 是否快取（0快取 1不快取）
	 */
	@Column(name = "is_cache")
	private Integer isCache;

	/**
	 * 類型（M目錄 C菜單 F按鈕）
	 */
	@NotBlank(message = "菜單類型不能為空")
	@Column(name = "menu_type")
	private String menuType;

	/**
	 * 顯示狀態（0顯示 1隱藏）
	 */
	@Column(name = "visible")
	private String visible;

	/**
	 * 菜單狀態（0顯示 1隱藏）
	 */
	@Column(name = "status")
	private String status;

	/**
	 * 權限字串
	 */
	@Size(min = 0, max = 100, message = "權限標識長度不能超過100個字元")
	@Column(name = "perms")
	private String perms;

	/**
	 * 菜單圖示
	 */
	@Column(name = "icon")
	private String icon;

	/**
	 * 創建者
	 */
	@Column(name = "create_by")
	private String createBy;

	/**
	 * 創建時間
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "create_time")
	private Date createTime;

	/**
	 * 更新者
	 */
	@Column(name = "update_by")
	private String updateBy;

	/**
	 * 更新時間
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "update_time")
	private Date updateTime;

	/**
	 * 備註
	 */
	@Column(name = "remark")
	private String remark;

	/**
	 * 子菜單
	 */
	@Transient
	private List<SysMenu> children = new ArrayList<>();

}
