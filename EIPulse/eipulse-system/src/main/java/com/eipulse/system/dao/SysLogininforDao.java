package com.eipulse.system.dao;

import org.springframework.stereotype.Repository;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.dao.dialect.FormatUtil;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.utils.ValidateUtil;
import com.eipulse.system.domain.SysLogininfor;

/**
 * 系統訪問日誌情況資訊 數據層
 *
 * @author eipulse
 */
@Repository
public class SysLogininforDao extends BaseDao<SysLogininfor, Long> {

	/**
	 * 查詢系統登入日誌集合
	 *
	 * @param logininfor 訪問日誌物件
	 * @return 登入記錄集合
	 */
	public Page findLogininforList(SysLogininfor logininfor) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		String hql = "from SysLogininfor where 1=1 ";
		if (!ValidateUtil.isEmpty(logininfor.getIpaddr())) {
			hql += " and ipaddr like '%" + logininfor.getIpaddr() + "%'";
		}
		if (!ValidateUtil.isEmpty(logininfor.getUserName())) {
			hql += " and userName like '%" + logininfor.getUserName() + "%'";
		}
		if (!ValidateUtil.isEmpty(logininfor.getStatus())) {
			hql += " and status= '" + logininfor.getStatus() + "'";
		}
		if (!ValidateUtil.isEmpty((String) logininfor.getParams().get("beginTime"))) {
			hql += " and " + sqlUtil.string2ShortDate(sqlUtil.date2String("loginTime"));
			hql += " >= " + sqlUtil
					.string2ShortDate(FormatUtil.formatStrForDB((String) logininfor.getParams().get("beginTime")));
		}
		if (!ValidateUtil.isEmpty((String) logininfor.getParams().get("endTime"))) {
			hql += " and " + sqlUtil.string2ShortDate(sqlUtil.date2String("loginTime"));
			hql += " <= " + sqlUtil
					.string2ShortDate(FormatUtil.formatStrForDB((String) logininfor.getParams().get("endTime")));
		}
		hql += " order by infoId desc";
		return this.findPage(hql, pageDomain.getPageNum(), pageDomain.getPageSize().intValue());
	}

	/**
	 * 清空系統登入日誌
	 *
	 * @return 結果
	 */
	public int cleanLogininfor() {
		String sql = "truncate table sys_logininfor";
		return this.executeSql(sql);
	}
}
