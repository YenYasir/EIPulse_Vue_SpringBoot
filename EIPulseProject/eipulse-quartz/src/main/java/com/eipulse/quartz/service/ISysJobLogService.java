package com.eipulse.quartz.service;

import org.springframework.data.domain.Page;

import com.eipulse.quartz.domain.SysJobLog;

/**
 * 定時任務調度日誌資訊資訊 服務層
 */
public interface ISysJobLogService {
	/**
	 * 獲取quartz調度器日誌的計劃任務
	 *
	 * @param jobLog 調度日誌資訊
	 * @return 調度任務日誌集合
	 */
	Page<SysJobLog> selectJobLogList(SysJobLog jobLog);

	/**
	 * 通過調度任務日誌ID查詢調度資訊
	 *
	 * @param jobLogId 調度任務日誌ID
	 * @return 調度任務日誌物件資訊
	 */
	SysJobLog selectJobLogById(Long jobLogId);

	/**
	 * 新增任務日誌
	 *
	 * @param jobLog 調度日誌資訊
	 */
	void addJobLog(SysJobLog jobLog);

	/**
	 * 批次刪除調度日誌資訊
	 *
	 * @param logIds 需要刪除的日誌ID
	 * @return 結果
	 */
	int deleteJobLogByIds(Long[] logIds);

	/**
	 * 刪除任務日誌
	 *
	 * @param jobId 調度日誌ID
	 * @return 結果
	 */
	int deleteJobLogById(Long jobId);

	/**
	 * 清空任務日誌
	 */
	void cleanJobLog();
}
