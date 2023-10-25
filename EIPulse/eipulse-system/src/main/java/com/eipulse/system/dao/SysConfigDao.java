package com.eipulse.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.dao.dialect.FormatUtil;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.utils.ValidateUtil;
import com.eipulse.system.domain.SysConfig;

/**
 * 參數配置DAO層
 *
 * @author eipulse
 */
@Repository
public class SysConfigDao extends BaseDao<SysConfig, Long> {
	public SysConfigDao() {
		modelClass = SysConfig.class;
		super.defaultOrder = "";
	}

	/**
	 * 查詢參數配置訊息
	 *
	 * @param config 參數配置訊息
	 * @return 參數配置訊息
	 */
	public SysConfig selectConfig(SysConfig config) {
		String hql = "from SysConfig where 1=1 ";
		if (config.getConfigId() != null) {
			hql += " and configId= " + config.getConfigId();
		}
		if (!ValidateUtil.isEmpty(config.getConfigKey())) {
			hql += " and configKey= '" + config.getConfigKey() + "'";
		}
		List<SysConfig> list = find(hql);
		if (!ValidateUtil.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 查詢參數配置列表(分頁)
	 *
	 * @param config 參數配置訊息
	 * @return 參數配置集合
	 */
	public Page findConfigList(SysConfig config) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		String hql = "from SysConfig where 1=1 ";
		if (!ValidateUtil.isEmpty(config.getConfigName())) {
			hql += " and configName like '%" + config.getConfigName() + "%'";
		}
		if (!ValidateUtil.isEmpty(config.getConfigType())) {
			hql += " and configType= '" + config.getConfigType() + "'";
		}
		if (!ValidateUtil.isEmpty(config.getConfigKey())) {
			hql += " and configKey like '%" + config.getConfigKey() + "%'";
		}
		if (!ValidateUtil.isEmpty((String) config.getParams().get("beginTime"))) {
			hql += " and " + sqlUtil.string2ShortDate(sqlUtil.date2String("createTime"));
			hql += " >= "
					+ sqlUtil.string2ShortDate(FormatUtil.formatStrForDB((String) config.getParams().get("beginTime")));
		}
		if (!ValidateUtil.isEmpty((String) config.getParams().get("endTime"))) {
			hql += " and " + sqlUtil.string2ShortDate(sqlUtil.date2String("createTime"));
			hql += " <= "
					+ sqlUtil.string2ShortDate(FormatUtil.formatStrForDB((String) config.getParams().get("endTime")));
		}
		return this.findPage(hql, pageDomain.getPageNum(), pageDomain.getPageSize().intValue());
	}

	/**
	 * 根據鍵名查詢參數配置訊息
	 *
	 * @param configKey 參數鍵名
	 * @return 參數配置List
	 */
	public List<SysConfig> findByConfigKey(String configKey) {
		String hql = "from SysConfig where configKey=?1 ";
		List<SysConfig> list = find(hql, configKey);
		return list;
	}
}
