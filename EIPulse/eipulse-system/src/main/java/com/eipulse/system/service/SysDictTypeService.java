package com.eipulse.system.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.common.constant.UserConstants;
import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.domain.entity.SysDictData;
import com.eipulse.common.core.domain.entity.SysDictType;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.service.BaseService;
import com.eipulse.common.exception.ServiceException;
import com.eipulse.common.utils.DictUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.system.dao.SysDictDataDao;
import com.eipulse.system.dao.SysDictTypeDao;

/**
 * 字典 業務層處理
 * 
 * @author eipulse
 */
@Service
public class SysDictTypeService extends BaseService<SysDictType, Long> {
	@Autowired
	private SysDictTypeDao sysDictTypeDao;

	@Autowired
	private SysDictDataDao sysDictDataDao;

	@Override
	protected BaseDao<SysDictType, Long> getDao() {
		return sysDictTypeDao;
	}

	/**
	 * 項目啟動時，初始化字典到緩存
	 */
	@PostConstruct
	public void init() {
		loadingDictCache();
	}

	/**
	 * 根據條件分頁查詢字典類型
	 * 
	 * @param dictType 字典類型資訊
	 * @return 字典類型集合資訊
	 */

	public Page findDictTypeList(SysDictType dictType) {
		return sysDictTypeDao.findDictTypeList(dictType);
	}

	/**
	 * 根據字典類型查詢字典數據
	 * 
	 * @param dictType 字典類型
	 * @return 字典數據集合資訊
	 */

	public List<SysDictData> findDictDataByType(String dictType) {
		List<SysDictData> dictDatas = DictUtils.getDictCache(dictType);
		if (StringUtils.isNotEmpty(dictDatas)) {
			return dictDatas;
		}
		dictDatas = sysDictDataDao.findDictDataByType(dictType);
		if (StringUtils.isNotEmpty(dictDatas)) {
			DictUtils.setDictCache(dictType, dictDatas);
			return dictDatas;
		}
		return null;
	}

	/**
	 * 根據字典類型查詢資訊
	 * 
	 * @param dictType 字典類型
	 * @return 字典類型
	 */

	public SysDictType selectDictTypeByType(String dictType) {
		return sysDictTypeDao.selectDictTypeByType(dictType);
	}

	/**
	 * 批量刪除字典類型資訊
	 * 
	 * @param dictIds 需要刪除的字典ID
	 * @return 結果
	 */

	public void deleteDictTypeByIds(Long[] dictIds) {
		for (Long dictId : dictIds) {
			SysDictType dictType = (SysDictType) getObject(dictId);
			if (sysDictDataDao.countDictDataByType(dictType.getDictType()) > 0) {
				throw new ServiceException(String.format("%1$s已分配,不能刪除", dictType.getDictName()));
			}
			sysDictTypeDao.deleteObject(dictId);
			DictUtils.removeDictCache(dictType.getDictType());
		}
	}

	/**
	 * 加載字典緩存數據
	 */

	public void loadingDictCache() {
		List<SysDictType> dictTypeList = sysDictTypeDao.getObjects();
		for (SysDictType dictType : dictTypeList) {
			List<SysDictData> dictDatas = sysDictDataDao.findDictDataByType(dictType.getDictType());
			DictUtils.setDictCache(dictType.getDictType(), dictDatas);
		}
	}

	/**
	 * 清空字典緩存數據
	 */

	public void clearDictCache() {
		DictUtils.clearDictCache();
	}

	/**
	 * 重置字典緩存數據
	 */

	public void resetDictCache() {
		clearDictCache();
		loadingDictCache();
	}

	/**
	 * 新增保存字典類型資訊
	 * 
	 * @param dict 字典類型資訊
	 * @return 結果
	 */

	public void insertDictType(SysDictType dict) {
		sysDictTypeDao.addObject(dict);
		DictUtils.setDictCache(dict.getDictType(), null);
	}

	/**
	 * 修改保存字典類型資訊
	 * 
	 * @param dict 字典類型資訊
	 * @return 結果
	 */

	@Transactional
	public void updateDictType(SysDictType dict) {
		SysDictType oldDict = (SysDictType) sysDictTypeDao.getObject(dict.getDictId());
		sysDictDataDao.updateDictDataType(oldDict.getDictType(), dict.getDictType());
		sysDictTypeDao.updateObject(dict);
		List<SysDictData> dictDatas = sysDictDataDao.findDictDataByType(dict.getDictType());
		DictUtils.setDictCache(dict.getDictType(), dictDatas);
	}

	/**
	 * 校驗字典類型稱是否唯一
	 * 
	 * @param dict 字典類型
	 * @return 結果
	 */

	public String checkDictTypeUnique(SysDictType dict) {
		Long dictId = StringUtils.isNull(dict.getDictId()) ? -1L : dict.getDictId();
		SysDictType dictType = sysDictTypeDao.selectDictTypeByType(dict.getDictType());
		if (StringUtils.isNotNull(dictType) && dictType.getDictId().longValue() != dictId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}
}
