package com.eipulse.common.core.dao.dialect;

import java.util.List;

/**
 * Oralce/DB2/MSSQLServer/MYSQL數據庫函數適配抽象接口，主要處理SQL腳本中常用的函數適配問題</br>
 * 由於函數傳參時有時是常量，有時是字段名稱，因此所有函數的參數都采用字串型參數，返回值也是字串型的。
 * 如果是參數本身就是字串型常量，請使用FormatUtil.formatStrForDB()進行數據格式化，也可以直接用‘號括起來。
 * 
 * @author eipulse
 *
 */
public interface FuncAdapter {

	public final static int IBM_DB2 = 0;
	public final static int ORACLE = 1;
	public final static int MSSQL = 2;
	public final static int MYSQL = 3;

	/**
	 * 獲得當前數據庫類型常量
	 * 
	 * @return 當前數據庫類型常量（int)
	 */
	public int getCurrentDBType();

	/**
	 * 類別：字串串處理</br>
	 * 字串串連接函數
	 * 
	 * @param params 參數個數不限制
	 * @return 對應數據庫類型的字串串
	 */
	public String concat(List params);

	/**
	 * 類別：字串串處理</br>
	 * 兩個字串串連接函數
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public String concat(String str1, String str2);

	/**
	 * 類別：字串串處理</br>
	 * 字串串截取函數
	 * 
	 * @param str   字串串
	 * @param start 開始位置，從1開始計數
	 * @param count 截取數量
	 * @return
	 */
	public String substr(String str, int start, int count);

	/**
	 * 類別：字串串處理</br>
	 * 去除字串串左右兩邊的空格
	 * 
	 * @param str
	 * @return
	 */
	public String trim(String str);

	/**
	 * 類別：類型轉換</br>
	 * 數值型數據轉字串型
	 * 
	 * @param str
	 * @return
	 */
	public String num2String(String str);

	/**
	 * 類別：類型轉換</br>
	 * 字串型數據轉數值型。內部封裝，不暴露給外部，請使用各個數據庫風格的函數調用
	 * 
	 * @param str
	 * @return
	 */
	public String string2Num(String str);

	/**
	 * 類別：類型轉換</br>
	 * 字串型數據轉數值型。等同於str2num,to_number,convert函數
	 * 
	 * @param str
	 * @return
	 */
	public String castString2Num(String str);

	/**
	 * 類別：類型轉換</br>
	 * yyyy-mm-dd hh24:mi:ss字串型數據轉日期型。內部封裝，不暴露給外部，請使用各個數據庫風格的函數調用
	 * 
	 * @param str
	 * @return
	 */
	public String string2Date(String str);

	/**
	 * 短日期格式</br>
	 * yyyy-mm-dd
	 * 
	 * @param str
	 * @return
	 */
	public String string2ShortDate(String str);

	/**
	 * 日期格式由外面控制
	 * 
	 * @param str
	 * @param format
	 * @return
	 */
	public String string2Date(String str, String format);

	/**
	 * 日期轉字串yyyy-mm-dd hh24:mi:ss
	 * 
	 * @param field
	 * @return
	 */
	public String date2String(String field);

	/**
	 * 日期轉字串，格式由調用者控制
	 * 
	 * @param field
	 * @param format
	 * @return
	 */
	public String date2String(String field, String format);

	/**
	 * 判斷一個字段數據是否為null或者''
	 * 
	 * @param str
	 * @return
	 */
	public String isNull(String str);

	/**
	 * 判斷一個字段數據是否不為null或者''
	 * 
	 * @param str
	 * @return
	 */
	public String isNotNull(String str);

	/**
	 * 獲取數據庫主鍵UUID
	 * 
	 * @return
	 */
	public String getUUID();

	/**
	 * 如果field的值為空，用val替換，只能用於Select語句
	 * 
	 * @param field
	 * @param val
	 * @return
	 */
	public String replaceNull(String field, String val);

}
