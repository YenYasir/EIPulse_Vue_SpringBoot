package com.eipulse.common.core.dao.dialect;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;

/**
 * SQL Server特有函數適配類
 * 
 * @author eipulse
 */
public class SQLServerStyleFunction extends DialectAdapter {
	public SQLServerStyleFunction() {
	}

	public SQLServerStyleFunction(SessionFactory session) {
		super(session);
	}

	@Override
	protected void initAdapter() {
		dialect = new SQLServerDialectEx();
	}

	@Override
	public int getCurrentDBType() {
		return MSSQL;
	}

	@Override
	public String string2Num(String str) {
		List params = new ArrayList();
		params.add(str);

		params.add(StandardBasicTypes.DOUBLE.getName());
		return execute(getFunc("convert"), params);
	}

	@Override
	public String string2Date(String str) {
		// Sql server日期作查詢條件可以不用轉換
		if (!isFieldName(str))
			return str;
		List params = new ArrayList();
		params.add(str);

		params.add(StandardBasicTypes.DATE.getName());
		return execute(getFunc("convert"), params);
	}

	@Override
	public String num2String(String str) {
		List params = new ArrayList();
		params.add(str);

		params.add(StandardBasicTypes.STRING.getName());
		return execute(getFunc("convert"), params);
	}

	@Override
	public String string2ShortDate(String str) {
		// Sql server日期作查詢條件可以不用轉換
		if (!isFieldName(str))
			return str;
		List params = new ArrayList();
		params.add(str);

		params.add(StandardBasicTypes.DATE.getName());
		return execute(getFunc("convert"), params);
	}

	@Override
	public String string2Date(String str, String format) {
		// Sql server日期作查詢條件可以不用轉換
		if (!isFieldName(str))
			return str;
		List params = new ArrayList();
		params.add(str);

		params.add(StandardBasicTypes.DATE.getName());
		return execute(getFunc("convert"), params);
	}

	@Override
	public String date2String(String field) {
		// Sql server日期作查詢條件可以不用轉換
		if (!isFieldName(field))
			return field;
		return "convert(char(10)," + field + ",20)";
	}

	@Override
	public String date2String(String field, String format) {
		// Sql server日期作查詢條件可以不用轉換
		if (!isFieldName(field))
			return field;
		List params = new ArrayList();
		params.add(field);

		params.add(StandardBasicTypes.DATE.getName());
		return execute(getFunc("convert"), params);
	}

	@Override
	public String getUUID() {
		return "replace(newid(),'-','')";
	}

	@Override
	public String isNull(String str) {
		return "(" + str + " is null or " + str + "='')";
	}

	@Override
	public String isNotNull(String str) {
		return "(" + str + " is not null or " + str + "<>'')";
	}

	@Override
	public String replaceNull(String field, String val) {
		return "isNull(" + field + "," + val + ")";
	}

}
