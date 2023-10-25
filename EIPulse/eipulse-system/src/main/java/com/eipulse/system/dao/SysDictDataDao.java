package com.eipulse.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.dao.dialect.FormatUtil;
import com.eipulse.common.core.domain.entity.SysDictData;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.utils.ValidateUtil;

/**
 * 字典表 數據層
 *
 * @author eipulse
 */
@Repository
public class SysDictDataDao extends BaseDao<SysDictData, Long> {
	/**
	 * 根據條件分頁查詢字典數據
	 *
	 * @param dictData 字典數據訊息
	 * @return 字典數據集合訊息
	 */
	public Page findDictDataList(SysDictData dictData) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		String hql = "from SysDictData where 1=1 ";
		if (!ValidateUtil.isEmpty(dictData.getDictType())) {
			hql += " and dictType= '" + dictData.getDictType() + "'";
		}
		if (!ValidateUtil.isEmpty(dictData.getDictLabel())) {
			hql += " and dictLabel like '%" + dictData.getDictLabel() + "%'";
		}
		if (!ValidateUtil.isEmpty(dictData.getStatus())) {
			hql += " and status= '" + dictData.getStatus() + "'";
		}
		hql += "order by dictSort asc ";
		return this.findPage(hql, pageDomain.getPageNum(), pageDomain.getPageSize().intValue());
	}

	/**
	 * 根據字典類型查詢字典數據
	 *
	 * @param dictType 字典類型
	 * @return 字典數據集合訊息
	 */
	public List<SysDictData> findDictDataByType(String dictType) {
		String hql = "from SysDictData where status = '0' and dictType =?1 order by dictSort asc";
		return find(hql, dictType);
	}

	/**
	 * 根據字典類型和字典鍵值查詢字典數據訊息
	 *
	 * @param dictType  字典類型
	 * @param dictValue 字典鍵值
	 * @return 字典標籤
	 */
	public String selectDictLabel(String dictType, String dictValue) {
		String dictLabel = "";
		String hql = "from SysDictData where dictType =? and dictValue=?";
		List<SysDictData> list = find(hql, new Object[] { dictType, dictValue });
		if (!ValidateUtil.isEmpty(list)) {
			SysDictData sysDictData = list.get(0);
			dictLabel = sysDictData.getDictLabel();
		}
		return dictLabel;
	}

	/**
	 * 查詢字典數據
	 *
	 * @param dictType 字典類型
	 * @return 字典數據
	 */
	public int countDictDataByType(String dictType) {
		String hql = "select count(1) from SysDictData  where dictType = ?1";
		Long total = (Long) this.find(hql, dictType).get(0);
		return total.intValue();
	}

	/**
	 * 同步修改字典類型
	 *
	 * @param oldDictType 舊字典類型
	 * @param newDictType 新舊字典類型
	 * @return 結果
	 */
	public void updateDictDataType(String oldDictType, String newDictType) {
		String hql = "update SysDictData set dictType =" + FormatUtil.formatStrForDB(newDictType);
		hql += " where dictType=" + FormatUtil.formatStrForDB(oldDictType);
		executeHql(hql);
	}
}
