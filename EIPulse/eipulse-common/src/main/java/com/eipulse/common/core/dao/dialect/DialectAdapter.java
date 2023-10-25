package com.eipulse.common.core.dao.dialect;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.DB2Dialect;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.Oracle8iDialect;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.jdbc.dialect.spi.DatabaseMetaDataDialectResolutionInfoAdapter;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 數據庫方言適配器抽象類，實現了DB2/Oracle/Mssqlserver三種數據庫的公共函數，存在差別的函數在對應的子類中實現
 * 
 * @author eipulse
 */
public abstract class DialectAdapter implements FuncAdapter {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	protected SessionFactory sessionFactory; // 數據庫連接會話Factory
	protected Dialect dialect; // 當前數據庫方言

	public DialectAdapter() {
		initAdapter();
	}

	public DialectAdapter(SessionFactory session) {
		this.sessionFactory = session;
		initAdapter();
	}

	/**
	 * 初始化適配器，根據數據庫類型建立對應的方言解析器，子類覆蓋這個方法可以直接實例化對應的方言
	 */
	protected void initAdapter() {
		// con = ((SessionFactoryImpl) session).getConnectionProvider().getConnection();
		Session session = sessionFactory.getCurrentSession();
		session.doWork(connection -> {
			dialect = new HSDialectResolver()
					.resolveDialect(new DatabaseMetaDataDialectResolutionInfoAdapter(connection.getMetaData()));
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tso.db.dialect.FuncAdapter#getCurrentDBType()
	 */
	@Override
	public int getCurrentDBType() {
		if (dialect == null)
			return -1; // 如果對應數據庫方言沒有生成，返回-1
		if (dialect instanceof Oracle8iDialect)
			return ORACLE;
		else if (dialect instanceof SQLServerDialect)
			return MSSQL;
		else if (dialect instanceof DB2Dialect)
			return IBM_DB2;
		else if (dialect instanceof MySQLDialect) {
			return MYSQL;
		}
		return -1;
	}

	/**
	 * 從函數表中查找指定函數（DB2\Oracle\MSSQL）三種數據庫函數名都統一的情況
	 * 
	 * @param funcName 函數名稱
	 * @return 函數對象，可以直接渲染生成對應的SQL Script
	 */
	protected SQLFunction getFunc(String funcName) {
		return dialect.getFunctions().get(funcName);

	}

	/**
	 * 方便代碼閱讀，統一腳本渲染執行過程，私有方法
	 * 
	 * @param func   數據庫函數對象
	 * @param params 函數參數
	 * @return
	 */
	protected String execute(SQLFunction func, List params) {
		if (func == null)
			return "";
		return func.render(StringType.INSTANCE, params, (SessionFactoryImplementor) sessionFactory);
	}

	/**
	 * 通過判斷傳入的參數是否被單引號包括，來判斷是否是對字段應用函數，還是對常量應用函數
	 * 
	 * @param str
	 * @return
	 */
	protected boolean isFieldName(String str) {
		str = str.trim();
		if (str == null || str.charAt(0) == '\'')
			return false;
		else
			return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tso.db.dialect.FuncAdapter#concat(java.util.List)
	 */
	@Override
	public String concat(List params) {
		return execute(getFunc("concat"), params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tso.db.dialect.FuncAdapter#concat(java.lang.String, java.lang.String)
	 */
	@Override
	public String concat(String str1, String str2) {
		List params = new ArrayList();
		params.add(str1);
		params.add(str2);
		return execute(getFunc("concat"), params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tso.db.dialect.FuncAdapter#substr(java.lang.String, int, int)
	 */
	@Override
	public String substr(String str, int start, int count) {
		List params = new ArrayList();
		params.add(str);
		params.add(start);
		params.add(count);
		return execute(getFunc("substring"), params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tso.db.dialect.FuncAdapter#trim(java.lang.String)
	 */
	@Override
	public String trim(String str) {
		List params = new ArrayList();
		params.add(str);
		return execute(getFunc("trim"), params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tso.db.dialect.FuncAdapter#num2String(java.lang.String)
	 */
	@Override
	public String num2String(String str) {
		List params = new ArrayList();
		params.add(str);
		return execute(getFunc("str"), params);
	}

	@Override
	public String castString2Num(String str) {
		List params = new ArrayList();
		params.add(str);
		// 2022-2-10 update Hibernate.DOUBLE已棄用，改用StandardBasicTypes.DOUBLE
		// params.add(Hibernate.DOUBLE.getName());
		params.add(StandardBasicTypes.DOUBLE.getName());
		return execute(getFunc("cast"), params);
	}

	@Override
	public String replaceNull(String field, String val) {
		return field;
	}

	@Override
	public String isNull(String str) {
		return "(" + str + " is null or " + str + "='')";
	}

	@Override
	public String isNotNull(String str) {
		return "(" + str + " is not null or " + str + "<>'')";
	}

}
