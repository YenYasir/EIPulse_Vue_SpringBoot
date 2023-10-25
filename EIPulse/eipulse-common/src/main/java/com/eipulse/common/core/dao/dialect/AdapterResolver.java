package com.eipulse.common.core.dao.dialect;

import java.sql.Connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根據數據庫連接類型創建不同的函數適配器，通過分析數據庫會話屬性來判斷數據庫類型。 經過比較IBM
 * DB2/ORACLE/MSSQLSERVER目前各種版本中的函數庫沒有存在差異， 因此不再區分版本了，特別編寫了一個測試用例來簡單判斷這些差異。
 */
public class AdapterResolver {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 返回會話對應的數據庫方言函數適配器實例
	 * 
	 * @param sessionFactory
	 * @return
	 */
	public FuncAdapter resolveAdapter(SessionFactory sessionFactory) {
		Connection con = null;
		FuncAdapter res = new MySqlStyleFunction(sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		if (session != null) {
			res = session.doReturningWork(connection -> {
				String databaseName = connection.getMetaData().getDatabaseProductName();
				if (databaseName.startsWith("DB2/")) {
					return new DB2StyleFunction(sessionFactory);
				}
				if ("Oracle".equals(databaseName)) {
					return new OracleStyleFunction(sessionFactory);
				}
				if (databaseName.startsWith("Microsoft SQL Server")) {
					return new SQLServerStyleFunction(sessionFactory);
				}
				if ("MySQL".equals(databaseName)) {
					return new MySqlStyleFunction(sessionFactory);
				}
				return null;
			});
			// session.close();
		}
		return res;
	}

	/**
	 * 返回會話對應的數據庫方言函數適配器實例
	 * 
	 * @param dbType 數據庫類型 db2/oracle/sql_server/mysql
	 * @return
	 */
	public FuncAdapter resolveAdapter(String dbType) {
		switch (dbType.toUpperCase()) {
		case "DB2":// DB2數據庫
			return new DB2StyleFunction();

		case "ORACLE":// ORACLE數據庫
			return new OracleStyleFunction();

		case "SQL_SERVER":// SQL SERVER數據庫
			return new SQLServerStyleFunction();

		case "MYSQL":// MYSQL數據庫
			return new MySqlStyleFunction();

		default:
			return new MySqlStyleFunction();

		}
	}
}
