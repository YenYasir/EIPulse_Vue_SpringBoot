package com.eipulse.system.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eipulse.common.core.domain.entity.SysUser;

/**
 * 員工表 數據層
 */
public interface SysUserDaoCustom {

	Page<SysUser> findMixPage(SysUser user, Pageable pageable);

}
