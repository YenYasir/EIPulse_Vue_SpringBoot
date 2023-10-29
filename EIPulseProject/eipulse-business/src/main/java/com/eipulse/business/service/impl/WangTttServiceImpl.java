package com.eipulse.business.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.business.dao.WangTttDao;
import com.eipulse.business.domain.WangTtt;
import com.eipulse.business.service.IWangTttService;
import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.utils.DateUtils;

/**
 * 測試程式碼生成器Service業務層處理
 */
@Transactional(readOnly = true)
@Service
public class WangTttServiceImpl implements IWangTttService {

	@Autowired
	private WangTttDao wangTttDao;

	/**
	 * 查詢測試程式碼生成器
	 *
	 * @param id 測試程式碼生成器ID
	 * @return 測試程式碼生成器
	 */
	@Override
	public WangTtt findById(String id) {
		return wangTttDao.findById(id).get();
	}

	/**
	 * 分頁查詢測試程式碼生成器列表
	 *
	 * @param req 測試程式碼生成器
	 * @return 測試程式碼生成器
	 */
	@Override
	public Page<WangTtt> findWangTttPage(WangTtt req) {
		Specification<WangTtt> example = formatQueryParams(req);
		PageDomain pageDomain = TableSupport.buildPageRequest();
		Pageable pageable = PageRequest.of(pageDomain.getPageNo(),
				Optional.ofNullable(pageDomain.getPageSize()).orElse(PageDomain.DEFAULT_PAGE_SIZE), Sort.Direction.DESC,
				Optional.ofNullable(pageDomain.getOrderByColumn()).orElse("createTime"));
		Page<WangTtt> page = wangTttDao.findAll(example, pageable);
		return page;
	}

	/**
	 * 分頁查詢測試程式碼生成器列表
	 *
	 * @param req 測試程式碼生成器
	 * @return 測試程式碼生成器
	 */
	@Override
	public List<WangTtt> findWangTttList(WangTtt req) {
		Specification<WangTtt> example = formatQueryParams(req);
		List<WangTtt> list = wangTttDao.findAll(example, Sort.by(Sort.Direction.DESC, "createTime"));
		return list;
	}

	private Specification<WangTtt> formatQueryParams(WangTtt req) {
		Specification<WangTtt> example = new Specification<WangTtt>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<WangTtt> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (StringUtils.isNotBlank(req.getColumnA())) {
					Predicate pre = cb.equal(root.get("columnA").as(String.class), req.getColumnA());
					list.add(pre);
				}
				if (null != req.getColumnB()) {
					Predicate pre = cb.greaterThan(root.get("columnB").as(Long.class), req.getColumnB());
					list.add(pre);
				}
				if (null != req.getColumnC()) {
					Predicate pre = cb.lessThanOrEqualTo(root.get("columnC").as(BigDecimal.class), req.getColumnC());
					list.add(pre);
				}
				if (StringUtils.isNotBlank(req.getCreateBy())) {
					Predicate pre = cb.like(root.get("createBy").as(String.class), "%" + req.getCreateBy() + "%");
					list.add(pre);
				}
				if (null != req.getParams().get("beginUpdateTime") && null != req.getParams().get("endUpdateTime")) {
					Predicate pre = cb.between(root.get("updateTime").as(Date.class), req.getUpdateTime(),
							req.getUpdateTime());
					list.add(pre);
				}
				if (list.isEmpty()) {
					return null;
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		return example;
	}

	/**
	 * 保存（新增/修改）測試程式碼生成器
	 *
	 * @param wangTtt 測試程式碼生成器
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void save(WangTtt wangTtt) {
		wangTtt.setCreateTime(DateUtils.getNowDate());
		wangTttDao.save(wangTtt);
	}

	/**
	 * 批次刪除測試程式碼生成器
	 *
	 * @param ids 需要刪除的測試程式碼生成器ID
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteByIds(List<String> ids) {
		List<WangTtt> existBeans = wangTttDao.findAllById(ids);
		if (!existBeans.isEmpty()) {
			wangTttDao.deleteAll(existBeans);
		}
	}

	/**
	 * 刪除測試程式碼生成器資訊
	 *
	 * @param id 測試程式碼生成器ID
	 * @return 結果
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteWangTttById(String id) {
		wangTttDao.deleteById(id);
	}
}
