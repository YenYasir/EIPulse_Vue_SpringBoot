package com.eipulse.common.core.dao.dialect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 數據庫類型 db2/oracle/sql_server/mysql
 * 
 * @author eipulse
 */
@Component
public class DBProperties {
	@Value("${spring.jpa.database}")
	private String database;

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}
}
