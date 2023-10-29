package com.eipulse.system.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.eipulse.system.domain.SysPost;

/**
 * 職位資訊 服務層
 */
public interface ISysPostService {
	/**
	 * 查詢職位資訊集合
	 *
	 * @param post 職位資訊
	 * @return 職位列表
	 */
	Page<SysPost> selectPostList(SysPost post);

	/**
	 * 查詢所有職位
	 *
	 * @return 職位列表
	 */
	List<SysPost> selectPostAll();

	/**
	 * 通過職位ID查詢職位資訊
	 *
	 * @param postId 職位ID
	 * @return 角色物件資訊
	 */
	SysPost selectPostById(Long postId);

	/**
	 * 根據員工ID獲取職位選擇框列表
	 *
	 * @param userId 員工ID
	 * @return 選中職位ID列表
	 */
	List<Long> selectPostListByUserId(Long userId);

	/**
	 * 校驗職位名稱
	 *
	 * @param post 職位資訊
	 * @return 結果
	 */
	String checkPostNameUnique(SysPost post);

	/**
	 * 校驗職位編碼
	 *
	 * @param post 職位資訊
	 * @return 結果
	 */
	String checkPostCodeUnique(SysPost post);

	/**
	 * 透過職位ID查詢職位使用數量
	 *
	 * @param postId 職位ID
	 * @return 結果
	 */
	int countUserPostById(Long postId);

	/**
	 * 刪除職位資訊
	 *
	 * @param postId 職位ID
	 * @return 結果
	 */
	int deletePostById(Long postId);

	/**
	 * 批次刪除職位資訊
	 *
	 * @param postIds 需要刪除的職位ID
	 * @return 結果
	 * @throws Exception 異常
	 */
	int deletePostByIds(Long[] postIds);

	/**
	 * 新增保存職位資訊
	 *
	 * @param post 職位資訊
	 * @return 結果
	 */
	int insertPost(SysPost post);

	/**
	 * 修改保存職位資訊
	 *
	 * @param post 職位資訊
	 * @return 結果
	 */
	int updatePost(SysPost post);
}
