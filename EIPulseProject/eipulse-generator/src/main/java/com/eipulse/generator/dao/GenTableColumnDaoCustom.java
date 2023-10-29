package com.eipulse.generator.dao;

import com.eipulse.generator.domain.GenTableColumn;

import java.util.List;

public interface GenTableColumnDaoCustom {

    List<GenTableColumn> findDbTableColumnsByName(String tableName);

}
