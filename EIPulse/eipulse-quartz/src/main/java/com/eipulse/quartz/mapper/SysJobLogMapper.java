package com.eipulse.quartz.mapper;

import java.util.List;

import com.eipulse.quartz.domain.SysJobLog;

/**
 * 調度任務日誌資訊 數據層
 * 
 * @author eipulse
 */
public interface SysJobLogMapper {
	/**
	 * 獲取quartz調度器日誌的計劃任務
	 * 
	 * @param jobLog 調度日誌資訊
	 * @return 調度任務日誌集合
	 */
	public List<SysJobLog> selectJobLogList(SysJobLog jobLog);

	/**
	 * 查詢所有調度任務日誌
	 *
	 * @return 調度任務日誌列表
	 */
	public List<SysJobLog> selectJobLogAll();

	/**
	 * 通過調度任務日誌ID查詢調度資訊
	 * 
	 * @param jobLogId 調度任務日誌ID
	 * @return 調度任務日誌對象資訊
	 */
	public SysJobLog selectJobLogById(Long jobLogId);

	/**
	 * 新增任務日誌
	 * 
	 * @param jobLog 調度日誌資訊
	 * @return 結果
	 */
	public int insertJobLog(SysJobLog jobLog);

	/**
	 * 批量刪除調度日誌資訊
	 * 
	 * @param logIds 需要刪除的數據ID
	 * @return 結果
	 */
	public int deleteJobLogByIds(Long[] logIds);

	/**
	 * 刪除任務日誌
	 * 
	 * @param jobId 調度日誌ID
	 * @return 結果
	 */
	public int deleteJobLogById(Long jobId);

	/**
	 * 清空任務日誌
	 */
	public void cleanJobLog();
}
