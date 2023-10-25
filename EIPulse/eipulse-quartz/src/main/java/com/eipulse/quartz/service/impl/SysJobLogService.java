package com.eipulse.quartz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.service.BaseService;
import com.eipulse.quartz.dao.SysJobLogDao;
import com.eipulse.quartz.domain.SysJobLog;

/**
 * 定時任務調度日誌資訊 服務層
 * 
 * @author eipulse
 */
@Service
public class SysJobLogService extends BaseService<SysJobLog, Long> {
	@Autowired
	private SysJobLogDao sysJobLogDao;

	@Override
	protected BaseDao<SysJobLog, Long> getDao() {
		return sysJobLogDao;
	}

	/**
	 * 獲取quartz調度器日誌的計劃任務
	 * 
	 * @param jobLog 調度日誌資訊
	 * @return 調度任務日誌集合
	 */
	public Page selectJobLogList(SysJobLog jobLog) {
		return sysJobLogDao.selectJobLogList(jobLog);
	}

	/**
	 * 清空任務日誌
	 */
	public void cleanJobLog() {
		sysJobLogDao.cleanJobLog();
	}

}
