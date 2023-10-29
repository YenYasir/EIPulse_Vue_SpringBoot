package com.eipulse.system.service;

import org.springframework.data.domain.Page;

import com.eipulse.system.domain.SysNotice;

/**
 * 公告 服務層
 */
public interface ISysNoticeService {
	/**
	 * 查詢公告資訊
	 *
	 * @param noticeId 公告ID
	 * @return 公告資訊
	 */
	SysNotice selectNoticeById(Long noticeId);

	/**
	 * 查詢公告列表
	 *
	 * @param notice 公告資訊
	 * @return 公告集合
	 */
	Page<SysNotice> selectNoticeList(SysNotice notice);

	/**
	 * 新增公告
	 *
	 * @param notice 公告資訊
	 * @return 結果
	 */
	int insertNotice(SysNotice notice);

	/**
	 * 修改公告
	 *
	 * @param notice 公告資訊
	 * @return 結果
	 */
	int updateNotice(SysNotice notice);

	/**
	 * 刪除公告資訊
	 *
	 * @param noticeId 公告ID
	 * @return 結果
	 */
	int deleteNoticeById(Long noticeId);

	/**
	 * 批次刪除公告資訊
	 *
	 * @param noticeIds 需要刪除的公告ID
	 * @return 結果
	 */
	int deleteNoticeByIds(Long[] noticeIds);
}
