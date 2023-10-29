package com.eipulse.generator.dao;

import com.eipulse.generator.domain.GenTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenTableDao extends JpaRepository<GenTable, Long>, JpaSpecificationExecutor<GenTable>, GenTableDaoCustom {

    Integer deleteByTableIdIn(List<Long> ids);

    Optional<GenTable> findByTableName(String tableName);

}
