package com.eipulse.common.core.dao.dialect;

import org.hibernate.SessionFactory;

/**
 * MySql特有函數適配類
 * 
 * @author eipulse
 */
public class MySqlStyleFunction extends DialectAdapter {
	public MySqlStyleFunction() {
	}

	public MySqlStyleFunction(SessionFactory session) {
		super(session);
	}

	@Override
	protected void initAdapter() {
		dialect = new MySqlDialectEx();
	}

	@Override
	public int getCurrentDBType() {
		return MYSQL;
	}

	@Override
	public String string2Num(String str) {
		return "convert(" + str + ",DECIMAL)";
	}

	@Override
	public String string2Date(String str) {

		return "str_to_date(" + str + ",'%Y-%m-%d %T')";
	}

	@Override
	public String string2ShortDate(String str) {
		return "str_to_date(" + str + ",'%Y-%m-%d')";
	}

	@Override
	public String string2Date(String str, String format) {
		format = format.replace("yyyy", "%Y").replace("MM", "%m").replace("dd", "%d").replace("HH", "%H")
				.replace("mm", "%i").replace("ss", "%S");
		return "str_to_date(" + str + "," + FormatUtil.formatStrForDB(format) + ")";
	}

	@Override
	public String date2String(String field) {
		return "date_format(" + field + ",'%Y-%m-%d %T')";
	}

	@Override
	public String date2String(String field, String format) {
		format = format.replace("yyyy", "%Y").replace("MM", "%m").replace("dd", "%d").replace("HH", "%H")
				.replace("mm", "%i").replace("ss", "%S");
		return "date_format(" + field + "," + FormatUtil.formatStrForDB(format) + ")";
	}

	@Override
	public String getUUID() {
		return "replace(uuid(),'-','') ";
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
		return "IFNULL(" + field + "," + val + ")";
	}

}
