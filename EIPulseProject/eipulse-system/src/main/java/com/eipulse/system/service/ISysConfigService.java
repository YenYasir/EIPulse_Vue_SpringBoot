package com.eipulse.system.service;

import org.springframework.data.domain.Page;

import com.eipulse.system.domain.SysConfig;

/**
 * 參數配置 服務層
 */
public interface ISysConfigService {
	/**
	 * 查詢參數配置資訊
	 *
	 * @param configId 參數配置ID
	 * @return 參數配置資訊
	 */
	SysConfig selectConfigById(Long configId);

	/**
	 * 根據鍵名查詢參數配置資訊
	 *
	 * @param configKey 參數鍵名
	 * @return 參數鍵值
	 */
	String selectConfigByKey(String configKey);

	/**
	 * 查詢參數配置列表
	 *
	 * @param config 參數配置資訊
	 * @return 參數配置集合
	 */
	Page<SysConfig> selectConfigList(SysConfig config);

	/**
	 * 新增參數配置
	 *
	 * @param config 參數配置資訊
	 * @return 結果
	 */
	int insertConfig(SysConfig config);

	/**
	 * 修改參數配置
	 *
	 * @param config 參數配置資訊
	 * @return 結果
	 */
	int updateConfig(SysConfig config);

	/**
	 * 批次刪除參數資訊
	 *
	 * @param configIds 需要刪除的參數ID
	 * @return 結果
	 */
	int deleteConfigByIds(Long[] configIds);

	/**
	 * 清空快取數據
	 */
	void clearCache();

	/**
	 * 校驗參數鍵名是否唯一
	 *
	 * @param config 參數資訊
	 * @return 結果
	 */
	String checkConfigKeyUnique(SysConfig config);
}
