package com.eipulse.common.core.dao.dialect;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

/**
 * Oracle特有函數適配類
 * 
 * @author eipulse
 */
public class OracleStyleFunction extends DialectAdapter {
	public OracleStyleFunction() {
	}

	public OracleStyleFunction(SessionFactory session) {
		super(session);
	}

	@Override
	protected void initAdapter() {
		dialect = new OracleDialectEx();
	}

	@Override
	public int getCurrentDBType() {
		return ORACLE;
	}

	@Override
	public String string2Num(String str) {
		List params = new ArrayList();
		params.add(str);
		return execute(getFunc("to_number"), params);
	}

	@Override
	public String string2Date(String str) {
		List params = new ArrayList();
		params.add(str);
		params.add(FormatUtil.formatStrForDB("yyyy-mm-dd hh24:mi:ss"));
		return execute(getFunc("to_date"), params);
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
	public String string2ShortDate(String str) {
		List params = new ArrayList();
		params.add(str);
		params.add(FormatUtil.formatStrForDB("yyyy-mm-dd"));
		return execute(getFunc("to_date"), params);
	}

	@Override
	public String string2Date(String str, String format) {
		List params = new ArrayList();
		params.add(str);
		params.add(FormatUtil.formatStrForDB(format));
		return execute(getFunc("to_date"), params);
	}

	@Override
	public String date2String(String field) {
		List params = new ArrayList();
		params.add(field);
		params.add(FormatUtil.formatStrForDB("yyyy-mm-dd"));
		return execute(getFunc("to_char"), params);
	}

	@Override
	public String date2String(String field, String format) {
		List params = new ArrayList();
		params.add(field);
		params.add(FormatUtil.formatStrForDB(format));
		return execute(getFunc("to_char"), params);
	}

	@Override
	public String getUUID() {
		return "sys_guid()";
	}

	@Override
	public String replaceNull(String field, String val) {
		return "nvl(" + field + "," + val + ")";
	}
}
