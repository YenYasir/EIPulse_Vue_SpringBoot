package com.eipulse.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.eipulse.system.domain.SysConfig;

/**
 * 參數配置 數據層
 */
@Repository
public interface SysConfigDao extends JpaRepository<SysConfig, Long>, JpaSpecificationExecutor<SysConfig> {

	List<SysConfig> findByConfigKey(String configKey);

}
