package com.eipulse.common.utils.poi;

/**
 * Excel數據格式處理適配器
 * 
 * @author eipulse
 */
public interface ExcelHandlerAdapter {
	/**
	 * 格式化
	 * 
	 * @param value 單元格數據值
	 * @param args  excel註解args參數組
	 *
	 * @return 處理後的值
	 */
	Object format(Object value, String[] args);
}
