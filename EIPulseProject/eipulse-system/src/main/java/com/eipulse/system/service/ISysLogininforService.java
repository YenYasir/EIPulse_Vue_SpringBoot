package com.eipulse.system.service;

import org.springframework.data.domain.Page;

import com.eipulse.system.domain.SysLogininfor;

/**
 * 系統訪問日誌情況資訊 服務層
 */
public interface ISysLogininforService {
	/**
	 * 新增系統登入日誌
	 *
	 * @param logininfor 訪問日誌物件
	 */
	void insertLogininfor(SysLogininfor logininfor);

	/**
	 * 查詢系統登入日誌集合
	 *
	 * @param logininfor 訪問日誌物件
	 * @return 登入記錄集合
	 */
	Page<SysLogininfor> selectLogininforList(SysLogininfor logininfor);

	/**
	 * 批次刪除系統登入日誌
	 *
	 * @param infoIds 需要刪除的登入日誌ID
	 * @return
	 */
	int deleteLogininforByIds(Long[] infoIds);

	/**
	 * 清空系統登入日誌
	 */
	void cleanLogininfor();
}
