package com.eipulse.quartz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.eipulse.quartz.domain.SysJob;

/**
 * 定時任務調度表 sys_job
 */
@Repository
public interface SysJobDao extends JpaRepository<SysJob, Long>, JpaSpecificationExecutor<SysJob> {

}
