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

/**
 * 菜單權限表 sys_menu
 *
 * @author eipulse
 */
@Entity
@Table(name = "sys_menu")
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
	 * 路由地址
	 */
	@Size(min = 0, max = 200, message = "路由地址不能超過200個字元")
	@Column(name = "path")
	private String path;

	/**
	 * 組件路徑
	 */
	@Size(min = 0, max = 200, message = "組件路徑不能超過255個字元")
	@Column(name = "component")
	private String component;

	/**
	 * 路由參數
	 */
	@Size(min = 0, max = 200, message = "路由參數不能超過255個字元")
	@Column(name = "query")
	private String query;

	/**
	 * 是否為外鏈（0是 1否）
	 */
	@Column(name = "is_frame")
	private Integer isFrame;

	/**
	 * 是否緩存（0緩存 1不緩存）
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
	 * 權限字元串
	 */
	@Size(min = 0, max = 100, message = "權限標識長度不能超過100個字元")
	@Column(name = "perms")
	private String perms;

	/**
	 * 菜單圖標
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

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Integer getIsFrame() {
		return isFrame;
	}

	public void setIsFrame(Integer isFrame) {
		this.isFrame = isFrame;
	}

	public Integer getIsCache() {
		return isCache;
	}

	public void setIsCache(Integer isCache) {
		this.isCache = isCache;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<SysMenu> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenu> children) {
		this.children = children;
	}
}