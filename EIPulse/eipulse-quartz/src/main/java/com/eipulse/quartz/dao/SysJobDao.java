package com.eipulse.quartz.dao;

import org.springframework.stereotype.Repository;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.utils.ValidateUtil;
import com.eipulse.quartz.domain.SysJob;

/**
 * 調度任務資訊 數據層
 *
 * @author eipulse
 */
@Repository
public class SysJobDao extends BaseDao<SysJob, Long> {
	/**
	 * 查詢調度任務日誌集合(分頁)
	 *
	 * @param job 調度資訊
	 * @return 操作日誌集合
	 */
	public Page selectJobList(SysJob job) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		String hql = "from SysJob where 1=1 ";
		if (!ValidateUtil.isEmpty(job.getJobName())) {
			hql += " and jobName like '%" + job.getJobName() + "%'";
		}
		if (!ValidateUtil.isEmpty(job.getJobGroup())) {
			hql += " and jobGroup= '" + job.getJobGroup() + "'";
		}
		if (!ValidateUtil.isEmpty(job.getStatus())) {
			hql += " and status= '" + job.getStatus() + "'";
		}
		if (!ValidateUtil.isEmpty(job.getInvokeTarget())) {
			hql += " and invokeTarget like '%" + job.getInvokeTarget() + "%'";
		}
		return this.findPage(hql, pageDomain.getPageNum(), pageDomain.getPageSize().intValue());
	}

}
