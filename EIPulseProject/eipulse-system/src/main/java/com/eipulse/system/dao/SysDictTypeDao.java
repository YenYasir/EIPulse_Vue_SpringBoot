package com.eipulse.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.eipulse.common.core.domain.entity.SysDictType;

/**
 * 字典表 數據層
 */
@Repository
public interface SysDictTypeDao extends JpaRepository<SysDictType, Long>, JpaSpecificationExecutor<SysDictType> {

	List<SysDictType> findByDictType(String dictType);

}
