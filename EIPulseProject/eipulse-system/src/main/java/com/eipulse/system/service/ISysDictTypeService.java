package com.eipulse.system.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.eipulse.common.core.domain.entity.SysDictData;
import com.eipulse.common.core.domain.entity.SysDictType;

/**
 * 字典 業務層
 */
public interface ISysDictTypeService {
	/**
	 * 根據條件分頁查詢字典類型
	 *
	 * @param dictType 字典類型資訊
	 * @return 字典類型集合資訊
	 */
	Page<SysDictType> selectDictTypeList(SysDictType dictType);

	/**
	 * 根據所有字典類型
	 *
	 * @return 字典類型集合資訊
	 */
	List<SysDictType> selectDictTypeAll();

	/**
	 * 根據字典類型查詢字典數據
	 *
	 * @param dictType 字典類型
	 * @return 字典數據集合資訊
	 */
	List<SysDictData> selectDictDataByType(String dictType);

	/**
	 * 根據字典類型ID查詢資訊
	 *
	 * @param dictId 字典類型ID
	 * @return 字典類型
	 */
	SysDictType selectDictTypeById(Long dictId);

	/**
	 * 根據字典類型查詢資訊
	 *
	 * @param dictType 字典類型
	 * @return 字典類型
	 */
	SysDictType selectDictTypeByType(String dictType);

	/**
	 * 批次刪除字典資訊
	 *
	 * @param dictIds 需要刪除的字典ID
	 * @return 結果
	 */
	int deleteDictTypeByIds(Long[] dictIds);

	/**
	 * 清空快取數據
	 */
	void clearCache();

	/**
	 * 新增保存字典類型資訊
	 *
	 * @param dictType 字典類型資訊
	 * @return 結果
	 */
	int insertDictType(SysDictType dictType);

	/**
	 * 修改保存字典類型資訊
	 *
	 * @param dictType 字典類型資訊
	 * @return 結果
	 */
	int updateDictType(SysDictType dictType);

	/**
	 * 校驗字典類型稱是否唯一
	 *
	 * @param dictType 字典類型
	 * @return 結果
	 */
	String checkDictTypeUnique(SysDictType dictType);
}
