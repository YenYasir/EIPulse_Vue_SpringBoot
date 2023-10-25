package com.eipulse.generator.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.utils.ConvertUtil;
import com.eipulse.common.utils.ValidateUtil;
import com.eipulse.generator.domain.GenTableColumn;

/**
 * 業務字段 數據層
 *
 * @author eipulse
 */
@Repository
public class GenTableColumnDao extends BaseDao<GenTableColumn, Long> {
	/**
	 * 根據表名稱查詢列資訊
	 *
	 * @param tableName 表名稱
	 * @return 列資訊
	 */
	public List<GenTableColumn> selectDbTableColumnsByName(String tableName) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				" select column_name columnName, (case when (is_nullable = 'no' && column_key != 'PRI') then '1' else null end) as isRequired ");
		sql.append(
				" , (case when column_key = 'PRI' then '1' else '0' end) as isPk, ordinal_position as sort, column_comment as columnComment ");
		sql.append(
				" , (case when extra = 'auto_increment' then '1' else '0' end) as isIncrement, column_type as columnType");
		sql.append(" from information_schema.columns ");
		sql.append(" where table_schema = (select database()) ");
		if (!ValidateUtil.isEmpty(tableName)) {
			sql.append(" and table_name ='" + tableName + "'");
		}
		sql.append(" order by ordinal_position ");
		return this.findBySQL(sql.toString());
	}

	/**
	 * 查詢業務字段列表
	 *
	 * @param tableId 業務字段編號
	 * @return 業務字段集合
	 */
	public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId) {
		String hql = "from GenTableColumn where tableId=" + tableId;
		hql += " order by sort";
		return this.find(hql);
	}

	/**
	 * 批量刪除業務字段
	 *
	 * @param ids 需要刪除的數據ID
	 * @return 結果
	 */
	public int deleteGenTableColumnByIds(Long[] ids) {
		String hql = "delete GenTableColumn where tableId in " + ConvertUtil.toDbString(ids);
		return this.executeHql(hql);
	}
}
