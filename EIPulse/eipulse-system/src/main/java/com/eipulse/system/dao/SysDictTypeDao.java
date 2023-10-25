package com.eipulse.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.dao.dialect.FormatUtil;
import com.eipulse.common.core.domain.entity.SysDictType;
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
public class SysDictTypeDao extends BaseDao<SysDictType, Long> {
	/**
	 * 根據條件分頁查詢字典類型
	 *
	 * @param dictType 字典類型訊息
	 * @return 字典類型集合訊息
	 */
	public Page findDictTypeList(SysDictType dictType) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		String hql = "from SysDictType where 1=1 ";
		if (!ValidateUtil.isEmpty(dictType.getDictName())) {
			hql += " and dictName like '%" + dictType.getDictName() + "%'";
		}
		if (!ValidateUtil.isEmpty(dictType.getStatus())) {
			hql += " and status= '" + dictType.getStatus() + "'";
		}
		if (!ValidateUtil.isEmpty(dictType.getDictType())) {
			hql += " and dictType like '%" + dictType.getDictType() + "%'";
		}
		if (!ValidateUtil.isEmpty((String) dictType.getParams().get("beginTime"))) {
			hql += " and " + sqlUtil.string2ShortDate(sqlUtil.date2String("createTime"));
			hql += " >= " + sqlUtil
					.string2ShortDate(FormatUtil.formatStrForDB((String) dictType.getParams().get("beginTime")));
		}
		if (!ValidateUtil.isEmpty((String) dictType.getParams().get("endTime"))) {
			hql += " and " + sqlUtil.string2ShortDate(sqlUtil.date2String("createTime"));
			hql += " <= "
					+ sqlUtil.string2ShortDate(FormatUtil.formatStrForDB((String) dictType.getParams().get("endTime")));
		}
		return this.findPage(hql, pageDomain.getPageNum(), pageDomain.getPageSize().intValue());
	}

	/**
	 * 根據字典類型查詢訊息
	 *
	 * @param dictType 字典類型
	 * @return 字典類型
	 */
	public SysDictType selectDictTypeByType(String dictType) {
		String hql = "from SysDictType where dictType=?1 ";
		List<SysDictType> list = find(hql, dictType);
		SysDictType type = null;
		if (!ValidateUtil.isEmpty(list)) {
			type = list.get(0);
		}
		return type;
	}

}
