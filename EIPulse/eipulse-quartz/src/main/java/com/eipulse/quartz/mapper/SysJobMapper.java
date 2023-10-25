package com.eipulse.quartz.mapper;

import java.util.List;

import com.eipulse.quartz.domain.SysJob;

/**
 * 調度任務資訊 數據層
 * 
 * @author eipulse
 */
public interface SysJobMapper {
	/**
	 * 查詢調度任務日誌集合
	 * 
	 * @param job 調度資訊
	 * @return 操作日誌集合
	 */
	public List<SysJob> selectJobList(SysJob job);

	/**
	 * 查詢所有調度任務
	 * 
	 * @return 調度任務列表
	 */
	public List<SysJob> selectJobAll();

	/**
	 * 通過調度ID查詢調度任務資訊
	 * 
	 * @param jobId 調度ID
	 * @return 角色對象資訊
	 */
	public SysJob selectJobById(Long jobId);

	/**
	 * 通過調度ID刪除調度任務資訊
	 * 
	 * @param jobId 調度ID
	 * @return 結果
	 */
	public int deleteJobById(Long jobId);

	/**
	 * 批量刪除調度任務資訊
	 * 
	 * @param ids 需要刪除的數據ID
	 * @return 結果
	 */
	public int deleteJobByIds(Long[] ids);

	/**
	 * 修改調度任務資訊
	 * 
	 * @param job 調度任務資訊
	 * @return 結果
	 */
	public int updateJob(SysJob job);

	/**
	 * 新增調度任務資訊
	 * 
	 * @param job 調度任務資訊
	 * @return 結果
	 */
	public int insertJob(SysJob job);
}
