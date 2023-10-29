package com.eipulse.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.eipulse.system.domain.SysNotice;

/**
 * 通知公告表 數據層
 */
@Repository
public interface SysNoticeDao
		extends JpaRepository<SysNotice, Long>, JpaSpecificationExecutor<SysNotice>, SysMenuDaoCustom {

	void deleteByNoticeIdIn(List<Long> ids);

}
