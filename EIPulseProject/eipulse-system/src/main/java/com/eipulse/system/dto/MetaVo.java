package com.eipulse.system.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 路由顯示資訊
 */
@Getter
@Setter
@ToString
public class MetaVo {
	/**
	 * 設置該路由在側邊欄和麵包屑中展示的名字
	 */
	private String title;

	/**
	 * 設置該路由的圖示，對應路徑src/assets/icons/svg
	 */
	private String icon;

	/**
	 * 設置為true，則不會被 <keep-alive>快取
	 */
	private boolean noCache;

	public MetaVo() {
	}

	public MetaVo(String title, String icon) {
		this.title = title;
		this.icon = icon;
	}

	public MetaVo(String title, String icon, boolean noCache) {
		this.title = title;
		this.icon = icon;
		this.noCache = noCache;
	}

}
