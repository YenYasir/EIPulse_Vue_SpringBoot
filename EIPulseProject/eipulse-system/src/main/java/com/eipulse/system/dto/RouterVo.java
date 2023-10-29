package com.eipulse.system.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * 路由配置資訊
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouterVo {

	/**
	 * 路由名字
	 */
	private String name;

	/**
	 * 路由位址
	 */
	private String path;

	/**
	 * 是否隱藏路由，當設置 true 的時候該路由不會再側邊欄出現
	 */
	private boolean hidden;

	/**
	 * 重定向位址，當設置 noRedirect 的時候該路由在麵包屑導航中不可被點擊
	 */
	private String redirect;

	/**
	 * 組件位址
	 */
	private String component;

	/**
	 * 當你一個路由下面的 children 聲明的路由大於1個時，自動會變成嵌套的模式--如組件頁面
	 */
	private Boolean alwaysShow;

	/**
	 * 其他元素
	 */
	private MetaVo meta;

	/**
	 * 子路由
	 */
	private List<RouterVo> children;

}
