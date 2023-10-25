package com.eipulse.common.core.dao.dialect;

import org.hibernate.dialect.DB2Dialect;

/**
 * DB2數據庫函數擴展類
 * 
 * @author eipulse 擴展函數列表：</br>
 * 
 */
public class DB2DialectEx extends DB2Dialect {
	public DB2DialectEx() {
		super();
//		registerFunction("abs", new StandardSQLFunction("abs"));
	}
}
