package com.eipulse.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.service.BaseService;
import com.eipulse.system.dao.SysOperLogDao;
import com.eipulse.system.domain.SysOperLog;

/**
 * 操作日誌 服務層處理
 * 
 * @author eipulse
 */
@Service
@Transactional
public class SysOperLogService extends BaseService<SysOperLog, Long> {
	@Autowired
	private SysOperLogDao sysOperLogDao;

	@Override
	protected BaseDao<SysOperLog, Long> getDao() {
		return sysOperLogDao;
	}

	/**
	 * 查詢系統操作日誌集合
	 * 
	 * @param operLog 操作日誌物件
	 * @return 操作日誌集合
	 */
	public Page findOperLogList(SysOperLog operLog) {
		return sysOperLogDao.findOperLogList(operLog);
	}

	/**
	 * 保存日誌
	 * 
	 * @param operLog 操作日誌物件
	 */
	public void savaData(SysOperLog operLog) {
		sysOperLogDao.saveObject(operLog);
	}

	/**
	 * 清空操作日誌
	 */
	public void cleanOperLog() {
		sysOperLogDao.cleanOperLog();
	}

}
