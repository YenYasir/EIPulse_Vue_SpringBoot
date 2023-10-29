package com.eipulse.generator.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.eipulse.generator.domain.GenTable;

/**
 * 業務 服務層
 */
public interface IGenTableService {
	/**
	 * 查詢業務列表
	 *
	 * @param genTable 業務資訊
	 * @return 業務集合
	 */
	Page<GenTable> selectGenTableList(GenTable genTable);

	/**
	 * 查詢據庫列表
	 *
	 * @param genTable 業務資訊
	 * @return 資料庫表集合
	 */
	Page<GenTable> selectDbTableList(GenTable genTable);

	/**
	 * 查詢據庫列表
	 *
	 * @param tableNames 表名稱組
	 * @return 資料庫表集合
	 */
	List<GenTable> selectDbTableListByNames(String[] tableNames);

	/**
	 * 查詢業務資訊
	 *
	 * @param id 業務ID
	 * @return 業務資訊
	 */
	GenTable selectGenTableById(Long id);

	/**
	 * 修改業務
	 *
	 * @param genTable 業務資訊
	 * @return 結果
	 */
	void updateGenTable(GenTable genTable);

	/**
	 * 刪除業務資訊
	 *
	 * @param tableIds 需要刪除的表數據ID
	 * @return 結果
	 */
	void deleteGenTableByIds(Long[] tableIds);

	/**
	 * 導入表結構
	 *
	 * @param tableList 導入表列表
	 */
	void importGenTable(List<GenTable> tableList);

	/**
	 * 預覽代碼
	 *
	 * @param tableId 表編號
	 * @return 預覽數據列表
	 */
	Map<String, String> previewCode(Long tableId);

	/**
	 * 生成代碼（下載方式）
	 *
	 * @param tableName 表名稱
	 * @return 數據
	 */
	byte[] downloadCode(String tableName);

	/**
	 * 生成代碼（自訂路徑）
	 *
	 * @param tableName 表名稱
	 * @return 數據
	 */
	void generatorCode(String tableName);

	/**
	 * 同步資料庫
	 *
	 * @param tableName 表名稱
	 */
	void synchDb(String tableName);

	/**
	 * 批次生成代碼（下載方式）
	 *
	 * @param tableNames 表數組
	 * @return 數據
	 */
	byte[] downloadCode(String[] tableNames);

	/**
	 * 修改保存參數校驗
	 *
	 * @param genTable 業務資訊
	 */
	void validateEdit(GenTable genTable);
}
