package com.eipulse.generator.dao;

import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.bean.BeanUtils;
import com.eipulse.generator.domain.GenTableColumn;
import com.eipulse.generator.dto.GenTableColumnDto;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GenTableColumnDaoCustomImpl implements GenTableColumnDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GenTableColumn> findDbTableColumnsByName(String tableName) {
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select column_name columnName, (case when (is_nullable = 'no' && column_key != 'PRI') then '1' else null end) as isRequired ");
        sql.append(" , (case when column_key = 'PRI' then '1' else '0' end) as isPk, ordinal_position as sort, column_comment as columnComment ");
        sql.append(" , (case when extra = 'auto_increment' then '1' else '0' end) as isIncrement, column_type as columnType");
        sql.append(" from information_schema.columns ");
        sql.append(" where table_schema = (select database()) ");
        if (StringUtils.isNotBlank(tableName)) {
            sql.append(" and table_name = ? ");
            params.add(tableName);
        }
        sql.append(" order by ordinal_position ");
        Query contentQuery = entityManager.createNativeQuery(sql.toString());
        for (int i = 0; i < params.size(); i++) {
            contentQuery.setParameter(i + 1, params.get(i));
        }
        contentQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(GenTableColumnDto.class));
        List<GenTableColumnDto> results = contentQuery.getResultList();
        List<GenTableColumn> result = format(results);
        return result;
    }


    private List<GenTableColumn> format(List<GenTableColumnDto> list) {
        List<GenTableColumn> collect = list.stream().map(dto -> {
            GenTableColumn genTable = new GenTableColumn();
            BeanUtils.copyProperties(dto, genTable);
            return genTable;
        }).collect(Collectors.toList());
        return collect;
    }

}
