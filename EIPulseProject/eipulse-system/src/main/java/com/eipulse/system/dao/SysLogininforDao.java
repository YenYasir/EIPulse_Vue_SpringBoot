package com.eipulse.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eipulse.system.domain.SysLogininfor;

/**
 * 系統訪問日誌情況資訊 數據層
 */
@Repository
public interface SysLogininforDao extends JpaRepository<SysLogininfor, Long>, JpaSpecificationExecutor<SysLogininfor> {

	void deleteAllByInfoIdIn(List<Long> ids);

	@Modifying
	@Query(value = "truncate table sys_logininfor", nativeQuery = true)
	void truncateTable();

}
