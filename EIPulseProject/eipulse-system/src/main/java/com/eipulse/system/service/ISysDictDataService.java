package com.eipulse.system.service;

import org.springframework.data.domain.Page;

import com.eipulse.common.core.domain.entity.SysDictData;

/**
 * 字典 業務層
 */
public interface ISysDictDataService {
	/**
	 * 根據條件分頁查詢字典數據
	 *
	 * @param dictData 字典數據資訊
	 * @return 字典數據集合資訊
	 */
	Page<SysDictData> selectDictDataList(SysDictData dictData);

	/**
	 * 根據字典類型和字典鍵值查詢字典數據資訊
	 *
	 * @param dictType  字典類型
	 * @param dictValue 字典鍵值
	 * @return 字典標籤
	 */
	String selectDictLabel(String dictType, String dictValue);

	/**
	 * 根據字典數據ID查詢資訊
	 *
	 * @param dictCode 字典數據ID
	 * @return 字典數據
	 */
	SysDictData selectDictDataById(Long dictCode);

	/**
	 * 批次刪除字典數據資訊
	 *
	 * @param dictCodes 需要刪除的字典數據ID
	 * @return 結果
	 */
	int deleteDictDataByIds(Long[] dictCodes);

	/**
	 * 新增保存字典數據資訊
	 *
	 * @param dictData 字典數據資訊
	 * @return 結果
	 */
	int insertDictData(SysDictData dictData);

	/**
	 * 修改保存字典數據資訊
	 *
	 * @param dictData 字典數據資訊
	 * @return 結果
	 */
	int updateDictData(SysDictData dictData);
}
