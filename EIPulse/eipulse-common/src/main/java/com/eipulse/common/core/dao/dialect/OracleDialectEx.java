package com.eipulse.common.core.dao.dialect;

import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

/**
 * ORACLE數據庫函數擴展類
 * 
 * @author eipulse 擴展函數列表：</br>
 *         to_number：字符轉數字（double)</br>
 */
public class OracleDialectEx extends Oracle10gDialect {
	@Override
	protected void registerFunctions() {
		super.registerFunctions();
		registerFunction("to_number", new StandardSQLFunction("to_number", StandardBasicTypes.DOUBLE));
		// registerFunction("to_number", new StandardSQLFunction("to_number",
		// DoubleType.INSTANCE));
	}
}
