package com.eipulse.system.dao;

import org.springframework.stereotype.Repository;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.utils.ValidateUtil;
import com.eipulse.system.domain.SysNotice;

/**
 * 通知公告表 數據層
 *
 * @author eipulse
 */
@Repository
public class SysNoticeDao extends BaseDao<SysNotice, Long> {
	/**
	 * 查詢公告列表(分頁)
	 *
	 * @param notice 公告資訊
	 * @return 公告集合
	 */
	public Page findNoticeList(SysNotice notice) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		String hql = "from SysNotice where 1=1 ";
		if (!ValidateUtil.isEmpty(notice.getNoticeTitle())) {
			hql += " and noticeTitle like '%" + notice.getNoticeTitle() + "%'";
		}
		if (!ValidateUtil.isEmpty(notice.getNoticeType())) {
			hql += " and noticeType= '" + notice.getNoticeType() + "'";
		}
		if (!ValidateUtil.isEmpty(notice.getCreateBy())) {
			hql += " and createBy like '%" + notice.getCreateBy() + "%'";
		}
		return this.findPage(hql, pageDomain.getPageNum(), pageDomain.getPageSize().intValue());
	}
}
