package com.eipulse.system.dao;

import org.springframework.stereotype.Repository;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.dao.dialect.FormatUtil;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.utils.ValidateUtil;
import com.eipulse.system.domain.SysOperLog;

/**
 * 操作日誌 數據層
 *
 * @author eipulse
 */
@Repository
public class SysOperLogDao extends BaseDao<SysOperLog, Long> {
	/**
	 * 查詢系統操作日誌集合
	 *
	 * @param operLog 操作日誌對象
	 * @return 操作日誌集合
	 */
	public Page findOperLogList(SysOperLog operLog) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		String hql = "from SysOperLog where 1=1 ";
		if (!ValidateUtil.isEmpty(operLog.getTitle())) {
			hql += " and title like '%" + operLog.getTitle() + "%'";
		}
		if (operLog.getBusinessType() != null && operLog.getBusinessType() != 0) {
			hql += " and businessType= '" + operLog.getBusinessType() + "'";
		}
		if (operLog.getStatus() != null) {
			hql += " and status= " + operLog.getStatus();
		}
		if (!ValidateUtil.isEmpty(operLog.getOperName())) {
			hql += " and operName like '%" + operLog.getOperName() + "%'";
		}
		if (!ValidateUtil.isEmpty((String) operLog.getParams().get("beginTime"))) {
			hql += " and " + sqlUtil.string2ShortDate(sqlUtil.date2String("operTime"));
			hql += " >= " + sqlUtil
					.string2ShortDate(FormatUtil.formatStrForDB((String) operLog.getParams().get("beginTime")));
		}
		if (!ValidateUtil.isEmpty((String) operLog.getParams().get("endTime"))) {
			hql += " and " + sqlUtil.string2ShortDate(sqlUtil.date2String("operTime"));
			hql += " <= "
					+ sqlUtil.string2ShortDate(FormatUtil.formatStrForDB((String) operLog.getParams().get("endTime")));
		}
		return this.findPage(hql, pageDomain.getPageNum(), pageDomain.getPageSize().intValue());
	}

	/**
	 * 清空操作日誌
	 */
	public void cleanOperLog() {
		String sql = "truncate table sys_oper_log";
		executeSql(sql);
	}
}
