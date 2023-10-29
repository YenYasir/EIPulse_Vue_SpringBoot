package com.eipulse.generator.dao;

import com.eipulse.generator.domain.GenTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenTableDaoCustom {

    Page<GenTable> findDbTableList(GenTable req, Pageable pageable);

    List<GenTable> findDbTableListByNames(List<String> tableNames);

}
