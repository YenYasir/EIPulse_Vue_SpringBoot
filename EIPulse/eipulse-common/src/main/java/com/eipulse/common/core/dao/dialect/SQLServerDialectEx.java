package com.eipulse.common.core.dao.dialect;

import org.hibernate.dialect.SQLServerDialect;

/**
 * 數據庫函數擴展類
 * 
 * @author eipulse 擴展函數列表：</br>
 *         convert：轉換函數
 */
public class SQLServerDialectEx extends SQLServerDialect {
	public SQLServerDialectEx() {
		super();
		// registerFunction("convert", new ConvertFunction());
	}

}
