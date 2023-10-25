package com.eipulse.common.core.dao.dialect;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根據數據庫連接判斷當前的數據庫方言
 * 
 * @author eipulse
 */
public class HSDialectResolver implements DialectResolver {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public Dialect resolveDialect(DialectResolutionInfo info) {
		String databaseName = info.getDatabaseName();
		if (databaseName.startsWith("DB2/")) {
			return new DB2DialectEx();
		}
		if ("Oracle".equals(databaseName)) {
			return new OracleDialectEx();
		}
		if (databaseName.startsWith("Microsoft SQL Server")) {
			return new SQLServerDialectEx();
		}
		if ("MySQL".equals(databaseName)) {
			return new MySqlDialectEx();
		}
		return null;
	}

}
