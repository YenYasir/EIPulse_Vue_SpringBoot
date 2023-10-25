package com.eipulse.common.core.dao.dialect;

import org.hibernate.SessionFactory;

import com.eipulse.common.utils.spring.SpringUtils;

/**
 * 數據庫適配器抽象工廠類,該工廠不允許創建實例,但可以被子工廠繼承，以產生新的適配器實例來提供適配服務。
 * 
 * @author eipulse
 * 
 */
public abstract class DBFuctionFactroy {
	protected static FuncAdapter instance; // 子類工廠創建的適配器實例必須實現FuncAdapter接口,保證應用的向下兼容性

	/**
	 * 通過該靜態方法可以獲得對應的數據庫方言適配器，<b>如果在同一個應用中存在連接多種數據庫，
	 * 需要傳入不同的SessionFactory來獲取不同的數據庫方言函數適配器實例。然後分別處理不同的SQL腳本。</b>
	 * 
	 * @param sessionFactory 數據庫會話連接工廠，通過它來分析連接那種數據庫，然後產生對應方言的函數適配器
	 * @return 對應的數據庫方言函數適配器實例
	 */
	public static FuncAdapter getFuncAdapter(SessionFactory sessionFactory) {
		if (instance == null)
			instance = new AdapterResolver().resolveAdapter(sessionFactory);
		return instance;
	}

	/**
	 * 通過該靜態方法可以獲得對應的數據庫方言適配器，<b>如果在同一個應用中存在連接多種數據庫， 然後分別處理不同的SQL腳本 *
	 * 
	 * @return 對應的數據庫方言函數適配器實例
	 */
	public static FuncAdapter getFuncAdapter() {
		DBProperties dbProperties = SpringUtils.getBean(DBProperties.class);
		if (instance == null)
			instance = new AdapterResolver().resolveAdapter(dbProperties.getDatabase());
		return instance;
	}

}
