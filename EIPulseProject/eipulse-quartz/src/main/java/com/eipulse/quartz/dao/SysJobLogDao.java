package com.eipulse.quartz.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eipulse.quartz.domain.SysJobLog;

/**
 * 定時任務調度日誌表 sys_job_log
 */
@Repository
public interface SysJobLogDao extends JpaRepository<SysJobLog, Long>, JpaSpecificationExecutor<SysJobLog> {

	Integer deleteByJobLogIdIn(List<Long> ids);

	@Modifying
	@Query(value = "truncate table sys_job_log", nativeQuery = true)
	void cleanJobLog();

}
