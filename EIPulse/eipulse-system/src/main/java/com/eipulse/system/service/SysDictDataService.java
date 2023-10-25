package com.eipulse.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.domain.entity.SysDictData;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.service.BaseService;
import com.eipulse.common.utils.DictUtils;
import com.eipulse.system.dao.SysDictDataDao;

/**
 * 字典 業務層處理
 * 
 * @author eipulse
 */
@Service
public class SysDictDataService extends BaseService<SysDictData, Long> {
	@Autowired
	private SysDictDataDao sysDictDataDao;

	@Override
	protected BaseDao<SysDictData, Long> getDao() {
		return sysDictDataDao;
	}

	/**
	 * 根據條件分頁查詢字典數據
	 * 
	 * @param dictData 字典數據資訊
	 * @return 字典數據集合資訊
	 */
	public Page findDictDataList(SysDictData dictData) {
		return sysDictDataDao.findDictDataList(dictData);
	}

	/**
	 * 根據字典類型和字典鍵值查詢字典數據資訊
	 * 
	 * @param dictType  字典類型
	 * @param dictValue 字典鍵值
	 * @return 字典標簽
	 */
	public String selectDictLabel(String dictType, String dictValue) {
		return sysDictDataDao.selectDictLabel(dictType, dictValue);
	}

	/**
	 * 批量刪除字典數據資訊
	 * 
	 * @param dictCodes 需要刪除的字典數據ID
	 * @return 結果
	 */
	public void deleteDictDataByIds(Long[] dictCodes) {
		for (Long dictCode : dictCodes) {
			SysDictData data = (SysDictData) this.getObject(dictCode);
			sysDictDataDao.deleteObject(dictCode);
			List<SysDictData> dictDatas = sysDictDataDao.findDictDataByType(data.getDictType());
			DictUtils.setDictCache(data.getDictType(), dictDatas);
		}
	}

	/**
	 * 新增保存字典數據資訊
	 * 
	 * @param data 字典數據資訊
	 * @return 結果
	 */
	public void insertDictData(SysDictData data) {
		sysDictDataDao.addObject(data);
		List<SysDictData> dictDatas = sysDictDataDao.findDictDataByType(data.getDictType());
		DictUtils.setDictCache(data.getDictType(), dictDatas);
	}

	/**
	 * 修改保存字典數據資訊
	 * 
	 * @param data 字典數據資訊
	 * @return 結果
	 */
	public void updateDictData(SysDictData data) {
		sysDictDataDao.updateObject(data);
		List<SysDictData> dictDatas = sysDictDataDao.findDictDataByType(data.getDictType());
		DictUtils.setDictCache(data.getDictType(), dictDatas);
	}

}
