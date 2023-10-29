package com.eipulse.common.core.page;

import java.io.Serializable;
import java.util.List;

/**
 * 表格分頁數據物件
 */
public class TableDataInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 總記錄數
	 */
	private long total;

	/**
	 * 列表數據
	 */
	private List<?> rows;

	/**
	 * 消息狀態碼
	 */
	private int code;

	/**
	 * 消息內容
	 */
	private String msg;

	/**
	 * 表格數據物件
	 */
	public TableDataInfo() {
	}

	/**
	 * 分頁
	 *
	 * @param list  列表數據
	 * @param total 總記錄數
	 */
	public TableDataInfo(List<?> list, int total) {
		this.rows = list;
		this.total = total;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
