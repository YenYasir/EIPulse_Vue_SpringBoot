package com.eipulse.generator.dao;

import com.eipulse.generator.domain.GenTableColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenTableColumnDao extends JpaRepository<GenTableColumn, Long>, JpaSpecificationExecutor<GenTableColumn>, GenTableColumnDaoCustom {

    List<GenTableColumn> findByTableId(Long tableId);

    Integer deleteByTableIdIn(List<Long> ids);

    Integer deleteByColumnIdIn(List<Long> ids);

}
