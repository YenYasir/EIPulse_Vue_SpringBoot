package com.eipulse.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.service.BaseService;
import com.eipulse.system.dao.SysLogininforDao;
import com.eipulse.system.domain.SysLogininfor;

/**
 * 系統訪問日誌情況資訊 服務層處理
 * 
 * @author eipulse
 */
@Service
@Transactional
public class SysLogininforService extends BaseService<SysLogininfor, Long> {

	@Autowired
	private SysLogininforDao sysLogininforDao;

	@Override
	protected BaseDao<SysLogininfor, Long> getDao() {
		return sysLogininforDao;
	}

	/**
	 * 查詢系統登入日誌集合（分頁）
	 * 
	 * @param logininfor 訪問日誌物件
	 * @return 登入記錄集合
	 */
	public Page findLogininforList(SysLogininfor logininfor) {
		return sysLogininforDao.findLogininforList(logininfor);
	}

	/**
	 * 保存登入日誌
	 * 
	 * @param logininfor 日誌物件
	 */
	public void saveData(SysLogininfor logininfor) {
		sysLogininforDao.saveObject(logininfor);
	}

	/**
	 * 清空系統登入日誌
	 */
	public void cleanLogininfor() {
		sysLogininforDao.cleanLogininfor();
	}

}
