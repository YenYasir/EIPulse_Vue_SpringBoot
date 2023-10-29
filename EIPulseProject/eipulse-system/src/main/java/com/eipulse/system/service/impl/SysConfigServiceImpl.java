package com.eipulse.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.common.annotation.DataSource;
import com.eipulse.common.constant.Constants;
import com.eipulse.common.constant.UserConstants;
import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.core.redis.RedisCache;
import com.eipulse.common.core.text.Convert;
import com.eipulse.common.enums.DataSourceType;
import com.eipulse.common.exception.CustomException;
import com.eipulse.common.utils.DateUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.code.BusinessBizCode;
import com.eipulse.common.utils.sql.SqlUtil;
import com.eipulse.system.dao.SysConfigDao;
import com.eipulse.system.domain.SysConfig;
import com.eipulse.system.service.ISysConfigService;

/**
 * 參數配置 服務層實現
 */
@Transactional(readOnly = true)
@Service
public class SysConfigServiceImpl implements ISysConfigService {

	@Autowired
	private SysConfigDao configDao;

	@Autowired
	private RedisCache redisCache;

	/**
	 * 項目啟動時，初始化參數到快取
	 */
	@PostConstruct
	public void init() {
		List<SysConfig> configsList = configDao.findAll();
		for (SysConfig config : configsList) {
			redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
		}
	}

	/**
	 * 查詢參數配置資訊
	 *
	 * @param configId 參數配置ID
	 * @return 參數配置資訊
	 */
	@Override
	@DataSource(DataSourceType.MASTER)
	public SysConfig selectConfigById(Long configId) {
		SysConfig config = new SysConfig();
		config.setConfigId(configId);
		SysConfig sysConfig = configDao.findById(configId).orElse(new SysConfig());
		return sysConfig;
	}

	/**
	 * 根據鍵名查詢參數配置資訊
	 *
	 * @param configKey 參數key
	 * @return 參數鍵值
	 */
	@Override
	public String selectConfigByKey(String configKey) {
		String configValue = Convert.toStr(redisCache.getCacheObject(getCacheKey(configKey)));
		if (StringUtils.isNotEmpty(configValue)) {
			return configValue;
		}
		List<SysConfig> byConfigKey = configDao.findByConfigKey(configKey);
		if (!byConfigKey.isEmpty() && StringUtils.isNotNull(byConfigKey.get(0))) {
			redisCache.setCacheObject(getCacheKey(configKey), byConfigKey.get(0).getConfigValue());
			return byConfigKey.get(0).getConfigValue();
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 查詢參數配置列表
	 *
	 * @param req 參數配置資訊
	 * @return 參數配置集合
	 */
	@Override
	public Page<SysConfig> selectConfigList(SysConfig req) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		if (StringUtils.isNotNull(pageDomain.getPageNum()) && StringUtils.isNotNull(pageDomain.getPageSize())) {
			String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
		}
		Specification<SysConfig> example = new Specification<SysConfig>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<SysConfig> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();

				if (null != req.getConfigId()) {
					Predicate pre = cb.equal(root.get("configId").as(Long.class), req.getConfigId());
					list.add(pre);
				}
				if (StringUtils.isNoneBlank(req.getConfigName())) {
					Predicate pre = cb.like(root.get("configName").as(String.class), "%" + req.getConfigName() + "%");
					list.add(pre);
				}
				if (StringUtils.isNoneBlank(req.getConfigKey())) {
					Predicate pre = cb.equal(root.get("configKey").as(String.class), req.getConfigKey());
					list.add(pre);
				}
				if (StringUtils.isNoneBlank(req.getConfigValue())) {
					Predicate pre = cb.equal(root.get("configValue").as(String.class), req.getConfigValue());
					list.add(pre);
				}
				if (StringUtils.isNoneBlank(req.getConfigType())) {
					Predicate pre = cb.equal(root.get("configType").as(String.class), req.getConfigType());
					list.add(pre);
				}
				if (null != req.getParams().get("beginTime")) {
					Predicate pre = cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class),
							DateUtils.parseDate(req.getParams().get("beginTime")));
					list.add(pre);
				}
				if (null != req.getParams().get("endTime")) {
					Predicate pre = cb.lessThanOrEqualTo(root.get("createTime").as(Date.class),
							DateUtils.parseDate(req.getParams().get("endTime")));
					list.add(pre);
				}
				if (list.isEmpty()) {
					return null;
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		Pageable pageable = PageRequest.of(pageDomain.getPageNo(), pageDomain.getPageSize(), Sort.Direction.DESC,
				Optional.ofNullable(pageDomain.getOrderByColumn()).orElse("createTime"));
		Page<SysConfig> page = configDao.findAll(example, pageable);
		return page;
	}

	/**
	 * 新增參數配置
	 *
	 * @param config 參數配置資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int insertConfig(SysConfig config) {
		config.setCreateTime(new Date());
		SysConfig save = configDao.save(config);
		if (null != save) {
			redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
		}
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 修改參數配置
	 *
	 * @param config 參數配置資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateConfig(SysConfig config) {
		SysConfig save = configDao.save(config);
		if (null != save) {
			redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
		}
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 批次刪除參數資訊
	 *
	 * @param configIds 需要刪除的參數ID
	 * @return 結果
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@Override
	public int deleteConfigByIds(Long[] configIds) {
		for (Long configId : configIds) {
			SysConfig config = selectConfigById(configId);
			if (StringUtils.equals(UserConstants.YES, config.getConfigType())) {
				throw new CustomException(String.format("內建參數【%1$s】不能刪除 ", config.getConfigKey()));
			}
		}
		List<SysConfig> sysConfigs = configDao.findAllById(Arrays.asList(configIds));
		configDao.deleteAll(sysConfigs);
		Collection<String> keys = redisCache.keys(Constants.SYS_CONFIG_KEY + "*");
		redisCache.deleteObject(keys);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 清空快取數據
	 */
	@Override
	public void clearCache() {
		Collection<String> keys = redisCache.keys(Constants.SYS_CONFIG_KEY + "*");
		redisCache.deleteObject(keys);
	}

	/**
	 * 校驗參數鍵名是否唯一
	 *
	 * @param config 參數配置資訊
	 * @return 結果
	 */
	@Override
	public String checkConfigKeyUnique(SysConfig config) {
		Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
		List<SysConfig> byConfigKey = configDao.findByConfigKey(config.getConfigKey());
		if (!byConfigKey.isEmpty() && StringUtils.isNotNull(byConfigKey.get(0))
				&& byConfigKey.get(0).getConfigId().longValue() != configId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	/**
	 * 設置cache key
	 *
	 * @param configKey 參數鍵
	 * @return 快取鍵key
	 */
	private String getCacheKey(String configKey) {
		return Constants.SYS_CONFIG_KEY + configKey;
	}
}
