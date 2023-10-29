package com.eipulse.common.core.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity基類
 */
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 搜索值
	 */
	private String searchValue;

	/**
	 * 請求參數
	 */
	private Map<String, Object> params;

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public Map<String, Object> getParams() {
		if (params == null) {
			params = new HashMap<>();
		}
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
