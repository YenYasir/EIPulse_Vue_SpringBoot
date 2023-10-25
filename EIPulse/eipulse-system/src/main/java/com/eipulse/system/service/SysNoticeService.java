package com.eipulse.system.service;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.service.BaseService;
import com.eipulse.system.dao.SysNoticeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eipulse.system.domain.SysNotice;

/**
 * 公告 服务层实现
 * 
 * @author liuyj
 */
@Service
public class SysNoticeService extends BaseService<SysNotice,Long>
{
    @Autowired
    private SysNoticeDao sysNoticeDao;
    @Override
    protected BaseDao<SysNotice, Long> getDao() {
        return sysNoticeDao;
    }

    /**
     * 查询公告列表(分页)
     * 
     * @param notice 公告信息
     * @return 公告集合
     */
    public Page findNoticeList(SysNotice notice)
    {
        return sysNoticeDao.findNoticeList(notice);
    }


}
