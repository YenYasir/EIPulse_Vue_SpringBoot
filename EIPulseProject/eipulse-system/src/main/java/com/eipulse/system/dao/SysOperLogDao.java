package com.eipulse.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eipulse.system.domain.SysOperLog;

/**
 * 操作日誌 數據層
 */
@Repository
public interface SysOperLogDao
		extends JpaRepository<SysOperLog, Long>, JpaSpecificationExecutor<SysOperLog>, SysMenuDaoCustom {

	void deleteByOperIdIn(List<Long> ids);

	@Modifying
	@Query(value = "truncate table sys_oper_log", nativeQuery = true)
	void cleanOperLog();

}
