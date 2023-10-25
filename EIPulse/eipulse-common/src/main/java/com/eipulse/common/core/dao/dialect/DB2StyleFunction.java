package com.eipulse.common.core.dao.dialect;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;

/**
 * DB2特有函數適配類
 * 
 * @author eipulse
 */
public class DB2StyleFunction extends DialectAdapter {
	public DB2StyleFunction() {
	}

	public DB2StyleFunction(SessionFactory session) {
		super(session);
	}

	@Override
	protected void initAdapter() {
		dialect = new DB2DialectEx();
	}

	@Override
	public int getCurrentDBType() {
		return IBM_DB2;
	}

	@Override
	public String string2Num(String str) {
		List params = new ArrayList();
		params.add(str);

		params.add(StandardBasicTypes.DOUBLE.getName());
		return execute(getFunc("cast"), params);
	}

	@Override
	public String string2Date(String str) {
		// DB2日期作為查詢條件可以不用轉換
		if (!isFieldName(str))
			return str;
		List params = new ArrayList();
		params.add(str);

		params.add(StandardBasicTypes.DATE.getName());
		return execute(getFunc("cast"), params);
	}

	@Override
	public String string2ShortDate(String str) {
		// DB2日期作為查詢條件可以不用轉換
		if (!isFieldName(str))
			return str;
		List params = new ArrayList();
		params.add(str);

		params.add(StandardBasicTypes.DATE.getName());
		return execute(getFunc("cast"), params);
	}

	@Override
	public String string2Date(String str, String format) {
		// DB2日期作為查詢條件可以不用轉換
		if (!isFieldName(str))
			return str;
		List params = new ArrayList();
		params.add(str);

		params.add(StandardBasicTypes.DATE.getName());
		return execute(getFunc("cast"), params);
	}

	@Override
	public String date2String(String field) {
		// DB2日期作為查詢條件可以不用轉換
		if (!isFieldName(field))
			return field;
		List params = new ArrayList();
		params.add(field);

		params.add(StandardBasicTypes.DATE.getName());
		return execute(getFunc("cast"), params);
	}

	@Override
	public String date2String(String field, String format) {
		// DB2日期作為查詢條件可以不用轉換
		if (!isFieldName(field))
			return field;
		List params = new ArrayList();
		params.add(field);

		params.add(StandardBasicTypes.DATE.getName());
		return execute(getFunc("cast"), params);
	}

	@Override
	public String getUUID() {
		return "sys_guid()";
	}
}
