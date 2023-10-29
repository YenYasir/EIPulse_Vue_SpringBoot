package com.eipulse.generator.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.common.utils.code.BusinessBizCode;
import com.eipulse.generator.dao.GenTableColumnDao;
import com.eipulse.generator.domain.GenTableColumn;

/**
 * 業務欄位 服務層實現
 */
@Transactional(readOnly = true)
@Service
public class GenTableColumnServiceImpl implements IGenTableColumnService {

	@Autowired
	private GenTableColumnDao genTableColumnDao;

	/**
	 * 查詢業務欄位列表
	 *
	 * @param tableId 業務欄位編號
	 * @return 業務欄位集合
	 */
	@Override
	public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId) {
		List<GenTableColumn> genTableColumns = genTableColumnDao.findByTableId(tableId);
		return genTableColumns;
	}

	/**
	 * 新增業務欄位
	 *
	 * @param genTableColumn 業務欄位資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int insertGenTableColumn(GenTableColumn genTableColumn) {
		genTableColumn.setCreateTime(new Date());
		genTableColumnDao.save(genTableColumn);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 修改業務欄位
	 *
	 * @param genTableColumn 業務欄位資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateGenTableColumn(GenTableColumn genTableColumn) {
		genTableColumn.setUpdateTime(new Date());
		genTableColumnDao.save(genTableColumn);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 刪除業務欄位物件
	 *
	 * @param ids 需要刪除的數據ID
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteGenTableColumnByIds(String ids) {
		List<Long> listIds = Arrays.asList(ids.split(",")).stream().map(s -> Long.parseLong(s.trim()))
				.collect(Collectors.toList());
		genTableColumnDao.deleteByTableIdIn(listIds);
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}
}
