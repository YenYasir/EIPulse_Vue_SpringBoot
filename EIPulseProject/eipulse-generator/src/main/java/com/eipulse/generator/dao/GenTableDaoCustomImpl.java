package com.eipulse.generator.dao;

import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.bean.BeanUtils;
import com.eipulse.generator.domain.GenTable;
import com.eipulse.generator.dto.GenTableDto;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GenTableDaoCustomImpl implements GenTableDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<GenTable> findDbTableList(GenTable req, Pageable pageable) {


        ArrayList<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        buildFrom(sql);
        buildWhere(sql, req, params);
        Query totalQuery = entityManager.createNativeQuery("select count(*) " + sql.toString());
        int size = params.size();
        for (int i = 0; i < size; i++) {
            totalQuery.setParameter(i + 1, params.get(i));
        }

        List<?> resultList = totalQuery.getResultList();
        if (resultList.isEmpty()) {
            return Page.empty();
        }
        BigInteger total = (BigInteger) resultList.get(0);
        Query contentQuery = entityManager.createNativeQuery(buildSelect() + sql.toString());
        for (int i = 0; i < size; i++) {
            contentQuery.setParameter(i + 1, params.get(i));
        }
        contentQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(GenTableDto.class));
        contentQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        contentQuery.setMaxResults(pageable.getPageSize());
        return new PageImpl<>(format(contentQuery.getResultList()), pageable, total.intValue());
    }

    @Override
    public List<GenTable> findDbTableListByNames(List<String> tableNames) {
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select table_name as tableName, table_comment as tableComment, create_time as createTime, update_time as updateTime ");
        sql.append(" from information_schema.tables");
        sql.append(" where table_name NOT LIKE 'qrtz_%' and table_name NOT LIKE 'gen_%' and table_schema = (select database()) ");
        if (null != tableNames && !tableNames.isEmpty()) {
            sql.append(" and table_name in (");
            sql.append(tableNames.stream().map(tagCode -> "?").collect(Collectors.joining(",")));
            sql.append(" )");
            params.addAll(tableNames);
        }
        Query contentQuery = entityManager.createNativeQuery(sql.toString());
        for (int i = 0; i < params.size(); i++) {
            contentQuery.setParameter(i + 1, params.get(i));
        }
        contentQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(GenTableDto.class));
        List<GenTableDto> results = contentQuery.getResultList();
        List<GenTable> result = format(results);
        return result;
    }


    private List<GenTable> format(List<GenTableDto> list) {
        List<GenTable> collect = list.stream().map(dto -> {
            GenTable genTable = new GenTable();
            BeanUtils.copyProperties(dto, genTable);
            return genTable;
        }).collect(Collectors.toList());
        return collect;
    }


    private String buildSelect() {
        StringBuffer sql = new StringBuffer();
        sql.append(" select table_name as tableName, table_comment as tableComment, create_time as createTime, update_time as updateTime ");
        return sql.toString();
    }

    private void buildFrom(StringBuilder sql) {
        sql.append(" from information_schema.tables  ");
    }

    private void buildWhere(StringBuilder sql, GenTable req, List<Object> params) {
        sql.append("  where table_schema = (select database())  ");
        sql.append("  AND table_name NOT LIKE 'qrtz_%' AND table_name NOT LIKE 'gen_%'  ");
        sql.append("  AND table_name NOT IN (select table_name from gen_table)  ");
        if (StringUtils.isNotBlank(req.getTableName())) {
            sql.append("   AND lower(table_name) like ? ");
            params.add("%" + req.getTableName().toLowerCase() + "%");
        }
        if (StringUtils.isNotBlank(req.getTableComment())) {
            sql.append("   AND lower(table_comment) like ? ");
            params.add("%" + req.getTableComment().toLowerCase() + "%");
        }
        if (null != req.getParams().get("beginTime")) {
            sql.append("   AND date_format(create_time,'%y%m%d') >= date_format(?,'%y%m%d') ");
            params.add(req.getParams().get("beginTime"));
        }
        if (null != req.getParams().get("endTime")) {
            sql.append("   AND date_format(create_time,'%y%m%d') <= date_format(?,'%y%m%d') ");
            params.add(req.getParams().get("endTime"));
        }
    }


}
