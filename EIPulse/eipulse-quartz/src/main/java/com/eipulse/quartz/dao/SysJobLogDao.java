package com.eipulse.quartz.dao;

import org.springframework.stereotype.Repository;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.dao.dialect.FormatUtil;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.utils.ValidateUtil;
import com.eipulse.quartz.domain.SysJobLog;

/**
 * 調度任務日誌資訊 數據層
 *
 * @author eipulse
 */
@Repository
public class SysJobLogDao extends BaseDao<SysJobLog, Long> {
	/**
	 * 獲取quartz調度器日誌的計劃任務(分頁)
	 *
	 * @param jobLog 調度日誌資訊
	 * @return 調度任務日誌集合
	 */
	public Page selectJobLogList(SysJobLog jobLog) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		String hql = "from SysJobLog where 1=1 ";
		if (!ValidateUtil.isEmpty(jobLog.getJobName())) {
			hql += " and jobName like '%" + jobLog.getJobName() + "%'";
		}
		if (!ValidateUtil.isEmpty(jobLog.getJobGroup())) {
			hql += " and jobGroup= '" + jobLog.getJobGroup() + "'";
		}
		if (!ValidateUtil.isEmpty(jobLog.getStatus())) {
			hql += " and status= '" + jobLog.getStatus() + "'";
		}
		if (!ValidateUtil.isEmpty(jobLog.getInvokeTarget())) {
			hql += " and invokeTarget like '%" + jobLog.getInvokeTarget() + "%'";
		}
		if (!ValidateUtil.isEmpty((String) jobLog.getParams().get("beginTime"))) {
			hql += " and " + sqlUtil.string2ShortDate(sqlUtil.date2String("createTime"));
			hql += " >= "
					+ sqlUtil.string2ShortDate(FormatUtil.formatStrForDB((String) jobLog.getParams().get("beginTime")));
		}
		if (!ValidateUtil.isEmpty((String) jobLog.getParams().get("endTime"))) {
			hql += " and " + sqlUtil.string2ShortDate(sqlUtil.date2String("createTime"));
			hql += " <= "
					+ sqlUtil.string2ShortDate(FormatUtil.formatStrForDB((String) jobLog.getParams().get("endTime")));
		}
		return this.findPage(hql, pageDomain.getPageNum(), pageDomain.getPageSize().intValue());
	}

	/**
	 * 清空任務日誌
	 */
	public void cleanJobLog() {
		String sql = "truncate table sys_job_log";
		this.executeSql(sql);
	}
}
