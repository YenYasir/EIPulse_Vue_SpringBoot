package com.eipulse.generator.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.dao.dialect.FormatUtil;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.utils.ConvertUtil;
import com.eipulse.common.utils.ValidateUtil;
import com.eipulse.generator.domain.GenTable;

/**
 * 業務 數據層
 *
 * @author eipulse
 */
@Repository
public class GenTableDao extends BaseDao<GenTable, Long> {
	/**
	 * 查詢業務列表
	 *
	 * @param genTable 業務資訊
	 * @return 業務集合
	 */
	public Page selectGenTableList(GenTable genTable) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		String hql = "from GenTable where 1=1 ";
		if (!ValidateUtil.isEmpty(genTable.getTableName())) {
			hql += " and tableName like '%" + genTable.getTableName() + "%'";
		}
		if (!ValidateUtil.isEmpty(genTable.getTableComment())) {
			hql += " and tableComment like '%" + genTable.getTableComment() + "%'";
		}
		if (!ValidateUtil.isEmpty((String) genTable.getParams().get("beginTime"))) {
			hql += " and " + sqlUtil.string2ShortDate(sqlUtil.date2String("createTime"));
			hql += " >= " + sqlUtil
					.string2ShortDate(FormatUtil.formatStrForDB((String) genTable.getParams().get("beginTime")));
		}
		if (!ValidateUtil.isEmpty((String) genTable.getParams().get("endTime"))) {
			hql += " and " + sqlUtil.string2ShortDate(sqlUtil.date2String("createTime"));
			hql += " <= "
					+ sqlUtil.string2ShortDate(FormatUtil.formatStrForDB((String) genTable.getParams().get("endTime")));
		}
		return this.findPage(hql, pageDomain.getPageNum(), pageDomain.getPageSize().intValue());
	}

	/**
	 * 查詢據庫列表
	 *
	 * @param genTable 業務資訊
	 * @return 數據庫表集合
	 */
	public Page selectDbTableList(GenTable genTable) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		StringBuilder sql = new StringBuilder();
		sql.append(
				" select table_name as tableName, table_comment as tableComment, create_time as createTime, update_time as updateTime ");
		sql.append(" from information_schema.tables");
		sql.append("  where table_schema = (select database())  ");
		sql.append("  AND table_name NOT LIKE 'qrtz_%' AND table_name NOT LIKE 'gen_%'  ");
		sql.append("  AND table_name NOT IN (select table_name from gen_table)  ");
		if (!ValidateUtil.isEmpty(genTable.getTableName())) {
			sql.append(" AND lower(table_name) like '%");
			sql.append(genTable.getTableName().toLowerCase() + "%'");
		}
		if (!ValidateUtil.isEmpty(genTable.getTableComment())) {
			sql.append(" AND lower(table_comment) like '%");
			sql.append(genTable.getTableComment().toLowerCase() + "%'");
		}
		if (!ValidateUtil.isEmpty((String) genTable.getParams().get("beginTime"))) {
			sql.append(" and " + sqlUtil.string2ShortDate(sqlUtil.date2String("createTime")));
			sql.append(" >= " + sqlUtil
					.string2ShortDate(FormatUtil.formatStrForDB((String) genTable.getParams().get("beginTime"))));
		}
		if (!ValidateUtil.isEmpty((String) genTable.getParams().get("endTime"))) {
			sql.append(" and " + sqlUtil.string2ShortDate(sqlUtil.date2String("createTime")));
			sql.append(" <= " + sqlUtil
					.string2ShortDate(FormatUtil.formatStrForDB((String) genTable.getParams().get("endTime"))));
		}
		sql.append(" order by create_time desc");
		return this.findPageBySQL(sql.toString(), pageDomain.getPageNum(), pageDomain.getPageSize().intValue());
	}

	/**
	 * 查詢據庫列表
	 *
	 * @param tableNames 表名稱組
	 * @return 數據庫表集合
	 */
	public List<GenTable> selectDbTableListByNames(String[] tableNames) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				" select table_name as tableName, table_comment as tableComment, create_time as createTime, update_time as updateTime ");
		sql.append(" from information_schema.tables");
		sql.append(
				" where table_name NOT LIKE 'qrtz_%' and table_name NOT LIKE 'gen_%' and table_schema = (select database()) ");
		if (!ValidateUtil.isEmpty(tableNames)) {
			sql.append(" and table_name in ");
			sql.append(ConvertUtil.toDbString(tableNames));
		}
		return this.findBySQL(sql.toString());
	}

	/**
	 * 查詢所有表資訊
	 *
	 * @return 表資訊集合
	 */
	public List<GenTable> selectGenTableAll() {
		return null;
	}

	/**
	 * 查詢表名稱業務資訊
	 *
	 * @param tableName 表名稱
	 * @return 業務資訊
	 */
	public GenTable selectGenTableByName(String tableName) {
		GenTable genTable = null;
		String hql = "from GenTable where tableName=?";
		List<GenTable> list = this.find(hql, tableName);
		if (!ValidateUtil.isEmpty(list)) {
			genTable = list.get(0);
		}
		return genTable;
	}
}
