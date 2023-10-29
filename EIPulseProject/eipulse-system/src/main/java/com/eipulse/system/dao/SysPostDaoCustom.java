package com.eipulse.system.dao;

import java.util.List;

/**
 * 職位資訊 數據層
 */
public interface SysPostDaoCustom {

	List<Long> findPostListByUserId(Long userId);

}
