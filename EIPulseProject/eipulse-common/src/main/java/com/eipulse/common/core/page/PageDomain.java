package com.eipulse.common.core.page;

import com.eipulse.common.utils.StringUtils;

/**
 * 分頁數據
 */
public class PageDomain {

	public final static int DEFAULT_PAGE_NO = 0;
	public final static int DEFAULT_PAGE_SIZE = 10;

	/**
	 * 當前記錄起始索引
	 */
	private Integer pageNum = DEFAULT_PAGE_NO;

	/**
	 * 每頁顯示記錄數
	 */
	private Integer pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * 排序列
	 */
	private String orderByColumn;

	/**
	 * 排序的方向desc或者asc
	 */
	private String isAsc = "asc";

	public String getOrderBy() {
		if (StringUtils.isEmpty(orderByColumn)) {
			return "";
		}
		return StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public Integer getPageNo() {
		return this.pageNum == null || this.pageNum <= 0 ? DEFAULT_PAGE_NO : this.pageNum - 1;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderByColumn() {
		return orderByColumn;
	}

	public void setOrderByColumn(String orderByColumn) {
		this.orderByColumn = orderByColumn;
	}

	public String getIsAsc() {
		return isAsc;
	}

	public void setIsAsc(String isAsc) {
		this.isAsc = isAsc;
	}
}
