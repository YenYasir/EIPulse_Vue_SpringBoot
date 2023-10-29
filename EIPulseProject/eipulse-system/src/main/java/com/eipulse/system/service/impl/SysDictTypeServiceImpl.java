package com.eipulse.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
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

import com.eipulse.common.constant.UserConstants;
import com.eipulse.common.core.domain.entity.SysDictData;
import com.eipulse.common.core.domain.entity.SysDictType;
import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.exception.CustomException;
import com.eipulse.common.utils.DictUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.code.BusinessBizCode;
import com.eipulse.common.utils.sql.SqlUtil;
import com.eipulse.system.dao.SysDictDataDao;
import com.eipulse.system.dao.SysDictTypeDao;
import com.eipulse.system.service.ISysDictTypeService;

/**
 * 字典 業務層處理
 */
@Transactional(readOnly = true)
@Service
public class SysDictTypeServiceImpl implements ISysDictTypeService {

	@Autowired
	private SysDictTypeDao dictTypeDao;

	@Autowired
	private SysDictDataDao dictDataDao;

	/**
	 * 項目啟動時，初始化字典到快取
	 */
	@PostConstruct
	public void init() {
		List<SysDictType> dictTypeList = dictTypeDao.findAll();
		for (SysDictType dictType : dictTypeList) {
			List<SysDictData> dictDatas = dictDataDao.findByDictTypeAndStatusOrderByDictSortAsc(dictType.getDictType(),
					"0");
			DictUtils.setDictCache(dictType.getDictType(), dictDatas);
		}
	}

	/**
	 * 根據條件分頁查詢字典類型
	 *
	 * @param req 字典類型資訊
	 * @return 字典類型集合資訊
	 */
	@Override
	public Page<SysDictType> selectDictTypeList(SysDictType req) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		if (StringUtils.isNotNull(pageDomain.getPageNum()) && StringUtils.isNotNull(pageDomain.getPageSize())) {
			String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
		}
		Specification<SysDictType> example = new Specification<SysDictType>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<SysDictType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (StringUtils.isNoneBlank(req.getDictName())) {
					Predicate pre = cb.like(root.get("dictName").as(String.class), "%" + req.getDictName() + "%");
					list.add(pre);
				}
				if (StringUtils.isNoneBlank(req.getDictType())) {
					Predicate pre = cb.like(root.get("dictType").as(String.class), "%" + req.getDictType() + "%");
					list.add(pre);
				}
				if (StringUtils.isNoneBlank(req.getStatus())) {
					Predicate pre = cb.equal(root.get("status").as(String.class), req.getStatus());
					list.add(pre);
				}
				if (null != req.getParams().get("beginTime")) {
					Predicate pre = cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class),
							(Date) req.getParams().get("beginTime"));
					list.add(pre);
				}
				if (null != req.getParams().get("endTime")) {
					Predicate pre = cb.lessThanOrEqualTo(root.get("createTime").as(Date.class),
							(Date) req.getParams().get("endTime"));
					list.add(pre);
				}
				if (list.isEmpty()) {
					return null;
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		Pageable pageable = PageRequest.of(pageDomain.getPageNo(),
				Optional.ofNullable(pageDomain.getPageSize()).orElse(PageDomain.DEFAULT_PAGE_SIZE), Sort.Direction.DESC,
				Optional.ofNullable(pageDomain.getOrderByColumn()).orElse("createTime"));
		Page<SysDictType> page = dictTypeDao.findAll(example, pageable);
		return page;
	}

	/**
	 * 根據所有字典類型
	 *
	 * @return 字典類型集合資訊
	 */
	@Override
	public List<SysDictType> selectDictTypeAll() {
		return dictTypeDao.findAll();
	}

	/**
	 * 根據字典類型查詢字典數據
	 *
	 * @param dictType 字典類型
	 * @return 字典數據集合資訊
	 */
	@Override
	public List<SysDictData> selectDictDataByType(String dictType) {
		List<SysDictData> dictDatas = DictUtils.getDictCache(dictType);
		if (StringUtils.isNotEmpty(dictDatas)) {
			return dictDatas;
		}
		dictDatas = dictDataDao.findByDictTypeAndStatusOrderByDictSortAsc(dictType, "0");
		if (StringUtils.isNotEmpty(dictDatas)) {
			DictUtils.setDictCache(dictType, dictDatas);
			return dictDatas;
		}
		return null;
	}

	/**
	 * 根據字典類型ID查詢資訊
	 *
	 * @param dictId 字典類型ID
	 * @return 字典類型
	 */
	@Override
	public SysDictType selectDictTypeById(Long dictId) {
		return dictTypeDao.findById(dictId).orElse(new SysDictType());
	}

	/**
	 * 根據字典類型查詢資訊
	 *
	 * @param dictType 字典類型
	 * @return 字典類型
	 */
	@Override
	public SysDictType selectDictTypeByType(String dictType) {
		SysDictType sysDictType = dictTypeDao.findByDictType(dictType).get(0);
		return sysDictType;
	}

	/**
	 * 批次刪除字典類型資訊
	 *
	 * @param dictIds 需要刪除的字典ID
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteDictTypeByIds(Long[] dictIds) {
		for (Long dictId : dictIds) {
			SysDictType dictType = selectDictTypeById(dictId);
			int size = dictDataDao.findByDictType(dictType.getDictType()).size();
			if (size > 0) {
				throw new CustomException(String.format("%1$s已分配,不能刪除", dictType.getDictName()));
			}
		}
		List<SysDictType> allById = dictTypeDao.findAllById(Arrays.asList(dictIds));
		dictTypeDao.deleteInBatch(allById);
		DictUtils.clearDictCache();
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 清空快取數據
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void clearCache() {
		DictUtils.clearDictCache();
	}

	/**
	 * 新增保存字典類型資訊
	 *
	 * @param dictType 字典類型資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int insertDictType(SysDictType dictType) {
		dictType.setCreateTime(new Date());
		SysDictType save = dictTypeDao.save(dictType);
		if (null != save) {
			DictUtils.clearDictCache();
		}
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 修改保存字典類型資訊
	 *
	 * @param dictType 字典類型資訊
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateDictType(SysDictType dictType) {
		SysDictType oldDict = dictTypeDao.findById(dictType.getDictId()).orElse(new SysDictType());
		dictDataDao.updateType(oldDict.getDictType(), dictType.getDictType());
		SysDictType save = dictTypeDao.save(dictType);
		if (null != save) {
			DictUtils.clearDictCache();
		}
		return BusinessBizCode.OPTION_SUCCESS.getCode();
	}

	/**
	 * 校驗字典類型稱是否唯一
	 *
	 * @param dict 字典類型
	 * @return 結果
	 */
	@Override
	public String checkDictTypeUnique(SysDictType dict) {
		Long dictId = StringUtils.isNull(dict.getDictId()) ? -1L : dict.getDictId();
		List<SysDictType> sysDictTypes = dictTypeDao.findByDictType(dict.getDictType());
		if (!sysDictTypes.isEmpty() && sysDictTypes.get(0).getDictId().longValue() != dictId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}
}
