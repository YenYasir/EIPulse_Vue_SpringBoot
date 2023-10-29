package com.eipulse.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.common.core.domain.entity.SysDictData;
import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.utils.DictUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.code.BusinessBizCode;
import com.eipulse.common.utils.sql.SqlUtil;
import com.eipulse.system.dao.SysDictDataDao;
import com.eipulse.system.service.ISysDictDataService;

/**
 * 字典 業務層處理
 */
@Transactional(readOnly = true)
@Service
public class SysDictDataServiceImpl implements ISysDictDataService {

	@Autowired
	private SysDictDataDao dictDataDao;

	/**
	 * 根據條件分頁查詢字典數據
	 *
	 * @param req 字典數據資訊
	 * @return 字典數據集合資訊
	 */
	@Override
	public Page<SysDictData> selectDictDataList(SysDictData req) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		if (StringUtils.isNotNull(pageDomain.getPageNum()) && StringUtils.isNotNull(pageDomain.getPageSize())) {
			String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
		}
		Specification<SysDictData> example = new Specification<SysDictData>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<SysDictData> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (StringUtils.isNoneBlank(req.getDictLabel())) {
					Predicate pre = cb.like(root.get("dictLabel").as(String.class), "%" + req.getDictLabel() + "%");
					list.add(pre);
				}
				if (StringUtils.isNoneBlank(req.getDictType())) {
					Predicate pre = cb.equal(root.get("dictType").as(String.class), req.getDictType());
					list.add(pre);
				}
				if (StringUtils.isNoneBlank(req.getStatus())) {
					Predicate pre = cb.equal(root.get("status").as(String.class), req.getStatus());
					list.add(pre);
				}
				if (list.isEmpty()) {
					return null;
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		Pageable pageable = PageRequest.of(pageDomain.getPageNo(), pageDomain.getPageSize(), Sort.Direction.DESC,
				Optional.ofNullable(pageDomain.getOrderByColumn()).orElse("createTime"));
		Page<SysDictData> page = dictDataDao.findAll(example, pageable);
		return page;
	}

	/**
	 * 根據字典類型和字典鍵值查詢字典數據資訊
	 *
	 * @param dictType  字典類型
	 * @param dictValue 字典鍵值
	 * @return 字典標籤
	 */
	@Override
	public String selectDictLabel(String dictType, String dictValue) {
		SysDictData sysDictData = dictDataDao.findByDictTypeAndDictValue(dictType, dictValue).orElse(new SysDictData());
		return sysDictData.getDictLabel();
	}

	/**
	 * 根據字典數據ID查詢資訊
	 *
	 * @param dictCode 字典數據ID
	 * @return 字典數據
	 */
	@Override
	public SysDictData selectDictDataById(Long dictCode) {
		SysDictData sysDictData = dictDataDao.findById(dictCode).orElse(new SysDictData());
		return sysDictData;
	}

	/**
	 * 批次刪除字典數據資訊
	 *
	 * @param dictCodes 需要刪除的字典數據ID
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteDictDataByIds(Long[] dictCodes) {
		List<SysDictData> sysDictDataList = dictDataDao.findByDictCodeIn(Arrays.asList(dictCodes));
		dictDataDao.deleteAll(sysDictDataList);
		DictUtils.clearDictCache();
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 新增保存字典數據資訊
	 *
	 * @param dictData 字典數據資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int insertDictData(SysDictData dictData) {
		dictData.setCreateTime(new Date());
		SysDictData save = dictDataDao.save(dictData);
		if (null != save) {
			DictUtils.clearDictCache();
		}
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 修改保存字典數據資訊
	 *
	 * @param dictData 字典數據資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateDictData(SysDictData dictData) {
		SysDictData save = dictDataDao.save(dictData);
		if (null != save) {
			DictUtils.clearDictCache();
		}
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}
}
