package com.eipulse.common.core.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分頁類
 * 
 * @author eipulse 實現列表的分頁封裝，包括返回集合、總數量等
 */
public class Page implements Serializable {

	private static final long serialVersionUID = 1L;
	private int totalCount = 0;
	private List items = new ArrayList();

	public Page(List items, int totalCount) {
		this.totalCount = totalCount;
		this.items = items;
	}

	/**
	 * 獲取查詢結果集合
	 * 
	 * @return 查詢結果集合
	 */
	public List getItems() {
		return items;
	}

	/**
	 * 獲取數據總數
	 * 
	 * @return
	 */
	public int getTotalCount() {
		return totalCount;
	}

}
