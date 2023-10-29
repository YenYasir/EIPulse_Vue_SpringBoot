package com.eipulse.business.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.eipulse.business.domain.WangTtt;

/**
 * 測試程式碼生成器Service介面
 */
public interface IWangTttService {
	/**
	 * 查詢測試程式碼生成器
	 *
	 * @param id 測試程式碼生成器ID
	 * @return 測試程式碼生成器
	 */
	WangTtt findById(String id);

	/**
	 * 分頁查詢測試程式碼生成器列表
	 *
	 * @param req 測試程式碼生成器
	 * @return 測試程式碼生成器集合
	 */
	Page<WangTtt> findWangTttPage(WangTtt req);

	/**
	 * 查詢測試程式碼生成器列表
	 *
	 * @param req 測試程式碼生成器
	 * @return 測試程式碼生成器集合
	 */
	List<WangTtt> findWangTttList(WangTtt req);

	/**
	 * 新增測試程式碼生成器
	 *
	 * @param wangTtt 測試程式碼生成器
	 * @return 結果
	 */
	void save(WangTtt wangTtt);

	/**
	 * 批次刪除測試程式碼生成器
	 *
	 * @param ids 需要刪除的測試程式碼生成器ID
	 * @return 結果
	 */
	void deleteByIds(List<String> ids);

	/**
	 * 刪除測試程式碼生成器資訊
	 *
	 * @param id 測試程式碼生成器ID
	 * @return 結果
	 */
	void deleteWangTttById(String id);
}
