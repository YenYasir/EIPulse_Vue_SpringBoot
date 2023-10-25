package com.eipulse.generator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.service.BaseService;
import com.eipulse.common.core.text.Convert;
import com.eipulse.generator.dao.GenTableColumnDao;
import com.eipulse.generator.domain.GenTableColumn;

/**
 * 業務欄位 服務層實現
 * 
 * @author eipulse
 */
@Service
public class GenTableColumnService extends BaseService<GenTableColumn, Long> {
	@Autowired
	private GenTableColumnDao genTableColumnDao;

	@Override
	protected BaseDao<GenTableColumn, Long> getDao() {
		return null;
	}

	/**
	 * 查詢業務欄位列表
	 * 
	 * @param tableId 業務欄位編號
	 * @return 業務欄位集合
	 */
	public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId) {
		return genTableColumnDao.selectGenTableColumnListByTableId(tableId);
	}

	/**
	 * 新增業務欄位
	 * 
	 * @param genTableColumn 業務欄位信息
	 * @return 結果
	 */
	public void insertGenTableColumn(GenTableColumn genTableColumn) {
		genTableColumnDao.addObject(genTableColumn);
	}

	/**
	 * 修改業務欄位
	 * 
	 * @param genTableColumn 業務欄位信息
	 * @return 結果
	 */
	public void updateGenTableColumn(GenTableColumn genTableColumn) {
		genTableColumnDao.updateObject(genTableColumn);
	}

	/**
	 * 刪除業務欄位物件
	 * 
	 * @param ids 需要刪除的數據ID
	 * @return 結果
	 */
	public int deleteGenTableColumnByIds(String ids) {
		return genTableColumnDao.deleteGenTableColumnByIds(Convert.toLongArray(ids));
	}

}
