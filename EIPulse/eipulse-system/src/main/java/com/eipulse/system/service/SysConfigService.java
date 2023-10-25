package com.eipulse.system.service;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.common.annotation.DataSource;
import com.eipulse.common.constant.Constants;
import com.eipulse.common.constant.UserConstants;
import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.redis.RedisCache;
import com.eipulse.common.core.service.BaseService;
import com.eipulse.common.core.text.Convert;
import com.eipulse.common.enums.DataSourceType;
import com.eipulse.common.exception.ServiceException;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.ValidateUtil;
import com.eipulse.system.dao.SysConfigDao;
import com.eipulse.system.domain.SysConfig;

/**
 * 參數配置 服務層實現
 * 
 * @author eipulse
 */
@Service
@Transactional
public class SysConfigService extends BaseService<SysConfig, Long> {
	@Autowired
	private SysConfigDao sysConfigDao;

	@Autowired
	private RedisCache redisCache;

	@Override
	protected BaseDao<SysConfig, Long> getDao() {
		return sysConfigDao;
	}

	/**
	 * 項目啟動時，初始化參數到緩存
	 */
	@PostConstruct
	public void init() {
		loadingConfigCache();
	}

	/**
	 * 查詢參數配置資訊
	 * 
	 * @param configId 參數配置ID
	 * @return 參數配置資訊
	 */
	// @Override
	@DataSource(DataSourceType.MASTER)
	public SysConfig selectConfigById(Long configId) {
		SysConfig config = new SysConfig();
		config.setConfigId(configId);
		return sysConfigDao.selectConfig(config);
	}

	/**
	 * 根據鍵名查詢參數配置資訊
	 * 
	 * @param configKey 參數key
	 * @return 參數鍵值
	 */
	// @Override
	public String selectConfigByKey(String configKey) {
		String configValue = Convert.toStr(redisCache.getCacheObject(getCacheKey(configKey)));
		if (StringUtils.isNotEmpty(configValue)) {
			return configValue;
		}
		SysConfig config = new SysConfig();
		config.setConfigKey(configKey);
		SysConfig retConfig = sysConfigDao.selectConfig(config);
		if (StringUtils.isNotNull(retConfig)) {
			redisCache.setCacheObject(getCacheKey(configKey), retConfig.getConfigValue());
			return retConfig.getConfigValue();
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 獲取驗證碼開關
	 * 
	 * @return true開啟，false關閉
	 */
	// @Override
	public boolean selectCaptchaOnOff() {
		String captchaOnOff = selectConfigByKey("sys.account.captchaOnOff");
		if (StringUtils.isEmpty(captchaOnOff)) {
			return true;
		}
		return Convert.toBool(captchaOnOff);
	}

	/**
	 * 查詢參數配置列表
	 * 
	 * @param config 參數配置資訊
	 * @return 參數配置集合
	 */
	// @Override
	public Page findConfigList(SysConfig config) {
		return sysConfigDao.findConfigList(config);
	}

	/**
	 * 新增參數配置
	 * 
	 * @param config 參數配置資訊
	 * @return 結果
	 */
	// @Override
	public void insertConfig(SysConfig config) {
		sysConfigDao.addObject(config);
		if (!ValidateUtil.isEmpty(config.getConfigId().toString())) {
			redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
		}
	}

	/**
	 * 修改參數配置
	 * 
	 * @param config 參數配置資訊
	 * @return 結果
	 */
	// @Override
	public void updateConfig(SysConfig config) {
		sysConfigDao.updateObject(config);
		if (!ValidateUtil.isEmpty(config.getConfigId().toString())) {
			redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
		}
	}

	/**
	 * 批量刪除參數資訊
	 * 
	 * @param configIds 需要刪除的參數ID
	 * @return 結果
	 */
	// @Override
	public void deleteConfigByIds(Long[] configIds) {
		for (Long configId : configIds) {
			SysConfig config = selectConfigById(configId);
			if (StringUtils.equals(UserConstants.YES, config.getConfigType())) {
				throw new ServiceException(String.format("內置參數【%1$s】不能刪除 ", config.getConfigKey()));
			}
			sysConfigDao.deleteObject(configId);
			redisCache.deleteObject(getCacheKey(config.getConfigKey()));
		}
	}

	/**
	 * 加載參數緩存數據
	 */
	// @Override
	@Transactional
	public void loadingConfigCache() {
		List<SysConfig> configsList = getObjects();
		for (SysConfig config : configsList) {
			redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
		}
	}

	/**
	 * 清空參數緩存數據
	 */
	// @Override
	public void clearConfigCache() {
		Collection<String> keys = redisCache.keys(Constants.SYS_CONFIG_KEY + "*");
		redisCache.deleteObject(keys);
	}

	/**
	 * 重置參數緩存數據
	 */
	// @Override
	public void resetConfigCache() {
		clearConfigCache();
		loadingConfigCache();
	}

	/**
	 * 校驗參數鍵名是否唯一
	 * 
	 * @param config 參數配置資訊
	 * @return 結果
	 */
	// @Override
	public String checkConfigKeyUnique(SysConfig config) {
		Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
		SysConfig info = null;
		List<SysConfig> configList = sysConfigDao.findByConfigKey(config.getConfigKey());
		if (!ValidateUtil.isEmpty(configList)) {
			info = configList.get(0);
		}
		if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 設置cache key
	 * 
	 * @param configKey 參數鍵
	 * @return 緩存鍵key
	 */
	private String getCacheKey(String configKey) {
		return Constants.SYS_CONFIG_KEY + configKey;
	}
}
