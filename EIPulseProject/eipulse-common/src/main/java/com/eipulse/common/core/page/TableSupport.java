package com.eipulse.common.core.page;

import com.eipulse.common.utils.ServletUtils;

/**
 * 表格數據處理
 */
public class TableSupport {
	/**
	 * 當前記錄起始索引
	 */
	public static final String PAGE_NUM = "pageNum";

	/**
	 * 每頁顯示記錄數
	 */
	public static final String PAGE_SIZE = "pageSize";

	/**
	 * 排序列
	 */
	public static final String ORDER_BY_COLUMN = "orderByColumn";

	/**
	 * 排序的方向 "desc" 或者 "asc".
	 */
	public static final String IS_ASC = "isAsc";

	/**
	 * 封裝分頁物件
	 */
	public static PageDomain getPageDomain() {
		PageDomain pageDomain = new PageDomain();
		pageDomain.setPageNum(ServletUtils.getParameterToInt(PAGE_NUM));
		pageDomain.setPageSize(ServletUtils.getParameterToInt(PAGE_SIZE));
		pageDomain.setOrderByColumn(ServletUtils.getParameter(ORDER_BY_COLUMN));
		pageDomain.setIsAsc(ServletUtils.getParameter(IS_ASC));
		return pageDomain;
	}

	public static PageDomain buildPageRequest() {
		return getPageDomain();
	}
}
