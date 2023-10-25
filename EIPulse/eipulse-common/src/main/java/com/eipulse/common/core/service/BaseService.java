package com.eipulse.common.core.service;

import java.io.Serializable;
import java.util.List;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.page.Page;

/**
 * 基礎服務類
 * 
 * @author eipulse 實現基礎的服務操作功能，包括物件獲取、分頁獲取、新增、批量刪除、修改等操作
 */

public abstract class BaseService<T, ID extends Serializable> {
	protected abstract BaseDao<T, ID> getDao();

	/**
	 * 獲取model物件
	 * 
	 * @param id id
	 * @return model物件
	 * 
	 */
	public Object getObject(String id) {
		return getDao().getObject(id);

	}

	/**
	 * 獲取model物件
	 * 
	 * @param id id
	 * @return model物件
	 * 
	 */
	public Object getObject(long id) {
		return getDao().getObject(id);
	}

	/**
	 * 獲取model物件
	 * 
	 * @param id id
	 * @return model物件
	 * 
	 */
	public Object getObject(int id) {
		return getDao().getObject(id);
	}

	/**
	 * 添加model物件
	 * 
	 * @param modelObject model物件
	 * 
	 */
	public void addObject(Object modelObject) {
		getDao().addObject(modelObject);
	}

	/**
	 * 保存model物件
	 * 
	 * @param modelObject model物件
	 * 
	 */
	public void saveObject(Object modelObject) {
		getDao().saveObject(modelObject);
	}

	/**
	 * 更新model物件
	 * 
	 * @param modelObject model物件
	 * 
	 */
	public void updateObject(Object modelObject) {
		getDao().updateObject(modelObject);
	}

	/**
	 * 刪除model物件
	 * 
	 * @param id id
	 * 
	 */
	public void deleteObject(String id) {
		getDao().deleteObject(id);
	}

	/**
	 * 刪除model物件
	 * 
	 * @param id id
	 *
	 */
	public void deleteObject(Long id) {
		getDao().deleteObject(id);
	}

	/**
	 * 批量刪除model物件
	 * 
	 * @param ids id
	 * 
	 */
	public void deleteObject(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			getDao().deleteObject(ids[i]);
		}
	}

	/**
	 * 批量刪除model物件
	 * 
	 * @param ids id
	 * 
	 */
	public void deleteObject(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			getDao().deleteObject(ids[i]);
		}
	}

	/**
	 * 獲取model集合
	 * 
	 * @return model集合
	 * 
	 */
	// @Transactional
	public List getObjects() {
		return getDao().getObjects();
	}

	/**
	 * 分布查詢所有數據
	 * 
	 * @param currPage 當前頁
	 * @param pageSize 每頁顯示行數
	 * @return 查詢結果
	 */
	public Page findPage(int currPage, int pageSize) {
		return getDao().findPage(currPage, pageSize);
	}

}
