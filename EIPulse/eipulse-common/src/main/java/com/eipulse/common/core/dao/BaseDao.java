package com.eipulse.common.core.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.eipulse.common.core.dao.dialect.DBFuctionFactroy;
import com.eipulse.common.core.dao.dialect.FuncAdapter;
import com.eipulse.common.core.page.Page;

/**
 * Dao基礎類
 * 
 * @author eipulse 實現Dao的基礎操作封裝，包括增加、刪除、修改、獲取、查詢等方法
 */
@Transactional
public class BaseDao<T, ID extends Serializable> {
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	protected String defaultOrder = "";
	protected Class modelClass;
	protected FuncAdapter sqlUtil;

	public SessionFactory getSessionFactory() {
		return entityManagerFactory.unwrap(SessionFactory.class);
	}

	private HibernateTemplate getHibernateTemplate() {
		return new HibernateTemplate(getSessionFactory());
	}

	@Transactional
	public Session openSession() {
		return entityManagerFactory.unwrap(SessionFactory.class).openSession();
	}

	@Transactional
	public Session getCurrentSession() {
		return entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();
		// return getSessionFactory().getCurrentSession();
	}

	protected Class entityClazz;

	public BaseDao() {
		// 獲取當前Dao所指定的Entity的Class
		Class clazz = this.getClass();
		Type genericSuperclass = clazz.getGenericSuperclass();
		if (genericSuperclass instanceof ParameterizedType) {
			Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
			if (actualTypeArguments != null && actualTypeArguments.length > 0) {
				entityClazz = (Class<?>) actualTypeArguments[0];
			}
		}
		this.sqlUtil = DBFuctionFactroy.getFuncAdapter();
	}

	public Class<T> getEntityClass() {
		return entityClazz;
	}

	/**
	 * 獲取model物件
	 * 
	 * @param id id
	 * @return model物件
	 * 
	 */
	public final Object getObject(long id) {
		Serializable idTemp = new Long(id);
		return getObject(idTemp);
	}

	/**
	 * 獲取model物件
	 * 
	 * @param id id
	 * @return model物件
	 * 
	 */
	public final Object getObject(int id) {
		Serializable idTemp = new Long(id);
		return getObject(idTemp);
	}

	/**
	 * 獲取model物件
	 * 
	 * @param id id
	 * @return model物件
	 * 
	 */
	public final Object getObject(String id) {
		Serializable idTemp = new String(id);
		return getObject(idTemp);
	}

	/**
	 * 獲取model物件
	 * 
	 * @param id id
	 * @return model物件
	 * 
	 */
	public final Object getObject(Serializable id) {
		return getHibernateTemplate().get(getEntityClass(), id);
	}

	/**
	 * 添加model物件
	 * 
	 * @param modelObject model物件
	 * 
	 */
	public final void addObject(Object modelObject) {
		// getHibernateTemplate().save(modelObject);
		getCurrentSession().save(modelObject);
	}

	/**
	 * 保存model物件
	 * 
	 * @param modelObject model物件
	 * 
	 */
	public final void saveObject(Object modelObject) {
		getCurrentSession().saveOrUpdate(modelObject);
		// getHibernateTemplate().saveOrUpdate(modelObject);
	}

	/**
	 * 更新model物件
	 * 
	 * @param modelObject model物件
	 * 
	 */
	public final void updateObject(Object modelObject) {
		getCurrentSession().merge(modelObject);
		// getHibernateTemplate().update(modelObject);
	}

	/**
	 * 刪除model物件
	 * 
	 * @param id id
	 * 
	 */
	public final void deleteObject(long id) {
		Serializable idTemp = new Long(id);
		deleteObject(idTemp);
	}

	/**
	 * 刪除model物件
	 * 
	 * @param id id
	 * 
	 */
	public final void deleteObject(int id) {
		Serializable idTemp = new Long(id);
		deleteObject(idTemp);
	}

	/**
	 * 刪除model物件
	 * 
	 * @param id id
	 * 
	 */
	public final void deleteObject(String id) {
		Serializable idTemp = new String(id);
		deleteObject(idTemp);
	}

	/**
	 * 刪除model物件
	 * 
	 * @param id id
	 * 
	 */
	public final void deleteObject(Serializable id) {
		Object object = this.getObject(id);
		// getHibernateTemplate().delete(object);
		getCurrentSession().delete(object);
	}

	/**
	 * 獲取model集合
	 * 
	 * @return model集合
	 * 
	 */
	@Transactional
	public List getObjects() {
		String queryString = "";
		System.out.println("name=" + getEntityClass().getSimpleName());
		queryString = "from " + getEntityClass().getSimpleName() + getDefaultOrder();
		return find(queryString);
	}

	/**
	 * 保存model集合
	 * 
	 */
	public final void saveList(final List modelList) {
		getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				int count = 0;
				for (Object obj : modelList) {
					session.saveOrUpdate(obj);
					count++;
					if (count == 100) {
						count = 0;
						session.flush();
					}
				}
				session.flush();
				return null;
			}
		});

	}

	/**
	 * 查找hql語句
	 * 
	 * @param queryString hql語句
	 * @return 查詢結果
	 */
	@Transactional
	protected List find(String queryString) {

		return getCurrentSession().createQuery(queryString).list();
		// return
		// getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(queryString).list();

	}

	/**
	 * 查找hql語句
	 * 
	 * @param queryString hql語句
	 * @param value       查詢值
	 * @return 查詢結果
	 */
	protected final List find(String queryString, String value) {
		return getCurrentSession().createQuery(queryString).setParameter(1, value).list();
		// return getHibernateTemplate().find(queryString, value);

	}

	/**
	 * 查找hql語句
	 * 
	 * @param queryString hql語句
	 * @param values      查詢值組
	 * @return 查詢結果
	 */
	protected final List find(String queryString, Object[] values) {
		Query query = getCurrentSession().createQuery(queryString);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i + 1, values[i]);
		}
		return query.list();
		// return getHibernateTemplate().find(queryString, values);

	}

	/**
	 * 分頁查詢所有數據
	 * 
	 * @param currPage 當前頁
	 * @param pageSize 每頁顯示行數
	 * @return 查詢結果
	 */
	public final Page findPage(int currPage, int pageSize) {
		String queryString = "";

		queryString = "from " + entityClazz.getSimpleName() + getDefaultOrder();
		Session session = getCurrentSession();
		int totalCount = countDataTotal(queryString, session);

		int startRow = (currPage - 1) * pageSize;
		if (startRow < 0)
			startRow = 0;
		Query query = session.createQuery(queryString).setFirstResult(startRow).setMaxResults(pageSize);

		List modelList = query.list();
		return new Page(modelList, totalCount);

	}

	/**
	 * 查詢hql語句
	 * 
	 * @param queryString hql語句
	 * @param currPage    當前頁
	 * @param pageSize    每頁顯示行數
	 * @return 查詢結果
	 */
	protected final Page findPage(String queryString, int currPage, int pageSize) {
		Session session = getCurrentSession();
		int totalCount = countDataTotal(queryString, session);
		int startRow = (currPage - 1) * pageSize;
		if (startRow < 0)
			startRow = 0;
		Query query = session.createQuery(queryString).setFirstResult(startRow).setMaxResults(pageSize);
		List modelList = query.list();
		return new Page(modelList, totalCount);
	}

	/**
	 * 統計記錄總數
	 * 
	 * @param queryString 查詢hql語句
	 * @param session     hibernate的Session物件
	 * @return 記錄總數 2012-3-12 gavin lee 修改方法為保護類型的，允許子類能夠覆蓋這個方法，處理比較特殊的sql腳本查詢
	 */
	protected int countDataTotal(String queryString, Session session) {

		int totalCount = 0;
		String lowerHql = queryString.trim().toLowerCase();
		int fromPos = 0;
		int orderPos = lowerHql.length();
		if (!lowerHql.startsWith("from "))
			fromPos = lowerHql.indexOf(" from ") + 1;
		orderPos = lowerHql.indexOf(" order by ") + 1;

		String countHql;
		if (orderPos != 0)
			countHql = "select count(*) " + queryString.substring(fromPos, orderPos);
		else
			countHql = "select count(*) " + queryString.substring(fromPos);

		int distinctPos = lowerHql.indexOf(" distinct ");

		if (distinctPos >= 0 && distinctPos < fromPos) {
			totalCount = session.createQuery(queryString).list().size();
		} else {
			Object value = session.createQuery(countHql).uniqueResult();
			if (value == null) {
				totalCount = 0;
			} else {
				totalCount = ((Long) value).intValue();
			}
		}
		return totalCount;

	}

	/**
	 * 查詢sql語句
	 * 
	 * @param sql sql語句
	 * @return 查詢結果
	 */
	protected final List findBySQL(final String sql) {
		Query query = getCurrentSession().createSQLQuery(sql).addEntity(getEntityClass());
		return query.list();
	}

	/**
	 * 查詢sql語句
	 * 
	 * @param sql        sql語句
	 * @param paramArray 參數列表
	 * @return 查詢結果
	 */
	protected final List findBySQL(final String sql, final Object[] paramArray) {
		Query sqlQuery = getCurrentSession().createNativeQuery(sql, entityClazz);
		for (int i = 0; i < paramArray.length; i++) {
			sqlQuery.setParameter(i, paramArray[i]);
		}
		return sqlQuery.list();
	}

	/**
	 * 查詢sql語句
	 * 
	 * @param sqlBuilder sql構造物件，包含了sql和參數列表
	 * @return 查詢結果
	 */
	protected final List findBySQL(ParamSqlBuilder sqlBuilder) {
		return findBySql(sqlBuilder, true);
	}

	/**
	 * 內部查詢sql，使用sql構造器進行查詢，註意判斷參數類型來設置參數
	 * 
	 * @param sqlBuilder    sql構造器
	 * @param isBuildEntity 是否構造為實體
	 * @return
	 */
	private List findBySql(ParamSqlBuilder sqlBuilder, final boolean isBuildEntity) {
		final String sql = sqlBuilder.getSql();
		final Map<String, Object> paramMap = sqlBuilder.getParamMap();
		final String paramType = sqlBuilder.getParamType();
		Object[] keys = paramMap.keySet().toArray();
		Arrays.sort(keys);
		StringBuilder sb = new StringBuilder();
		for (Object key : keys) {
			sb.append("param").append(key).append("=").append(paramMap.get(key)).append(",");
		}
		// log.debug("sql= [{}],paramList= [{}]", sql, sb.toString());
		Query sqlQuery;
		if (isBuildEntity) {
			sqlQuery = getCurrentSession().createNativeQuery(sql, entityClazz);
		} else {
			sqlQuery = getCurrentSession().createNativeQuery(sql);
		}
		for (String key : paramMap.keySet()) {
			if (ParamSqlBuilder.INDEXPARAM.equals(paramType)) {
				sqlQuery.setParameter(Integer.parseInt(key), paramMap.get(key));
			} else {
				sqlQuery.setParameter(key, paramMap.get(key));
			}
		}
		return sqlQuery.list();

	}

	/**
	 * 查詢靈活的sql語句
	 * 
	 * @param sqlBuilder sql構造器，包含了sql和參數
	 * @return 查詢結果
	 */
	protected final List findByFreeSQL(ParamSqlBuilder sqlBuilder) {
		return findBySql(sqlBuilder, false);
	}

	/**
	 * 查詢靈活的sql語句
	 * 
	 * @param sql        靈活的sql語句（不受model影響）
	 * @param paramArray 參數列表
	 * @return 查詢結果
	 */
	protected final List findByFreeSQL(final String sql, final Object[] paramArray) {
		Query query = getCurrentSession().createNativeQuery(sql);
		for (int i = 0; i < paramArray.length; i++) {
			query.setParameter(i, paramArray[i]);
		}
		return query.list();
	}

	/**
	 * 查詢靈活的sql語句(返回指定的實體類)
	 * 
	 * @param sql           靈活的sql語句
	 * @param paramArray    參數列表
	 * @param outModelClass 指定外部實體類
	 * @return 查詢結果
	 */
	protected final List findByFreeSQL(final String sql, final Object[] paramArray, final Class outModelClass) {
		Query sqlQuery = getCurrentSession().createNativeQuery(sql, outModelClass);
		for (int i = 0; i < paramArray.length; i++) {
			sqlQuery.setParameter(i, paramArray[i]);
		}
		return sqlQuery.list();
	}

	/**
	 * 查詢靈活的sql語句
	 * 
	 * @param sql 靈活的sql語句（不受model影響）
	 * @return 查詢結果
	 */
	protected final List findByFreeSQL(final String sql) {
		Query sqlQuery = getCurrentSession().createSQLQuery(sql);
		return sqlQuery.list();
	}

	/**
	 * 查詢靈活的sql語句(返回指定的實體類)
	 * 
	 * @param sql           靈活的sql語句
	 * @param outModelClass 指定外部實體類
	 * @return 查詢結果
	 */
	protected final List findByFreeSQL(final String sql, final Class outModelClass) {
		Query sqlQuery = getCurrentSession().createSQLQuery(sql).addEntity(outModelClass);
		// NativeQuery sqlQuery=getCurrentSession().createNativeQuery(sql,
		// outModelClass);
		return sqlQuery.getResultList();
	}

	/**
	 * 查詢sql語句
	 * 
	 * @param sql      sql語句
	 * @param currPage 當前頁
	 * @param pageSize 每頁顯示行數
	 * @return 查詢結果
	 */
	protected final Page findPageBySQL(String sql, int currPage, int pageSize) {
		Session session = getCurrentSession();
		int totalCount = countDataTotalBySQL(sql, session);

		int startRow = (currPage - 1) * pageSize;
		if (startRow < 0)
			startRow = 0;
		Query query = session.createSQLQuery(sql).addEntity(getEntityClass()).setFirstResult(startRow)
				.setMaxResults(pageSize);

		List modelList = query.list();
		return new Page(modelList, totalCount);

	}

	/**
	 * 查詢靈活的sql語句
	 * 
	 * @param sql      靈活的sql語句（不受model影響）
	 * @param currPage 當前頁
	 * @param pageSize 每頁顯示行數
	 * @return 查詢結果
	 */
	protected final Page findPageByFreeSQL(String sql, int currPage, int pageSize) {
		Session session = getCurrentSession();
		int totalCount = countDataTotalBySQL(sql, session);

		int startRow = (currPage - 1) * pageSize;
		if (startRow < 0)
			startRow = 0;
		Query query = session.createSQLQuery(sql).setFirstResult(startRow).setMaxResults(pageSize);

		List modelList = query.list();
		return new Page(modelList, totalCount);
	}

	/**
	 * 查詢靈活的sql語句(返回指定的實體類)
	 * 
	 * @param sql           靈活的sql語句（不受model影響）
	 * @param currPage      當前頁
	 * @param pageSize      每頁顯示行數
	 * @param outModelClass 指定外部實體類
	 * @return 查詢結果
	 */
	protected final Page findPageByFreeSQL(String sql, int currPage, int pageSize, final Class outModelClass) {

		Session session = getCurrentSession();
		int totalCount = countDataTotalBySQL(sql, session);

		int startRow = (currPage - 1) * pageSize;
		if (startRow < 0)
			startRow = 0;
		Query query = session.createSQLQuery(sql).addEntity(outModelClass).setFirstResult(startRow)
				.setMaxResults(pageSize);

		List modelList = query.list();
		return new Page(modelList, totalCount);
	}

	/**
	 * 執行SQL語句返回受影響的記錄數
	 * 
	 * @String sql sql語句
	 * @return int 受影響的記錄數
	 */
	protected final int executeSql(final String sql) {

		Integer obj = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				// 在SQL語句中加入修改時間和傳輸狀態
				int updateCount = session.createSQLQuery(sql).executeUpdate();
				return Integer.valueOf(updateCount);
			}
		});
		return obj == null ? 0 : obj.intValue();

	}

	/**
	 * 執行SQL語句返回受影響的記錄數
	 * 
	 * @String hql hql語句
	 * @return int 受影響的記錄數
	 */
	protected final int executeHql(final String hql) {

		Integer obj = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				int updateCount = session.createQuery(hql).executeUpdate();
				return new Integer(updateCount);
			}
		});
		return obj == null ? 0 : obj.intValue();

	}

	/**
	 * 統計記錄總數
	 * 
	 * @param sql     查詢sql語句
	 * @param session hibernate的Session物件
	 * @return 記錄總數 2012-3-12 gavin lee 修改方法為保護類型的，允許子類能夠覆蓋這個方法，處理比較特殊的sql腳本查詢
	 */
	protected int countDataTotalBySQL(String sql, Session session) {

		int totalCount = 0;
		String lowerHql = sql.trim().toLowerCase();
		int fromPos = 0;
		int orderPos = lowerHql.length();
		fromPos = lowerHql.indexOf(" from ") + 1;
		orderPos = lowerHql.indexOf(" order by ") + 1;

		String countSql;
		if (orderPos != 0)
			countSql = "select count(*) as num " + sql.substring(fromPos, orderPos);
		else
			countSql = "select count(*) as num " + sql.substring(fromPos);

		int distinctPos = lowerHql.indexOf(" distinct ");

		if (distinctPos >= 0 && distinctPos < fromPos) {
			totalCount = session.createSQLQuery(sql).list().size();
		} else {
			Object value = session.createSQLQuery(countSql).uniqueResult();
			if (value == null) {
				totalCount = 0;
			} else {
				totalCount = ((Long) value).intValue();
			}
		}
		return totalCount;

	}

	/**
	 * 獲取默認的排序
	 * 
	 * @return
	 */
	private String getDefaultOrder() {
		if (defaultOrder == null)
			return "";
		if (defaultOrder.trim().equals(""))
			return "";
		else {
			String lowerHql = defaultOrder.trim().toLowerCase();
			if (lowerHql.indexOf("order by") == 0)
				return " " + defaultOrder;
			else
				return " order by " + defaultOrder;
		}
	}

	/**
	 * 根據用戶物件，獲取數據過濾的HQL語句
	 * 
	 * @param user 用戶物件
	 * @return
	 */
	/**
	 * public String getFilterHql(User user) { String modelName =
	 * modelClass.getSimpleName(); return getFilterHql(user, null, modelName); }
	 */

	/**
	 * 根據用戶物件，獲取數據過濾的HQL語句
	 * 
	 * @param user      用戶物件
	 * @param aliasName 別名
	 * @return
	 */
	/**
	 * public String getFilterHql(User user, String aliasName) { String modelName =
	 * modelClass.getSimpleName(); return getFilterHql(user, aliasName, modelName);
	 * }
	 */

	/**
	 * 根據用戶物件，獲取數據過濾的HQL語句
	 * 
	 * @param user      用戶物件
	 * @param aliasName 別名
	 * @param modelName 實體類名
	 * @return
	 */
	/**
	 * public String getFilterHql(User user, String aliasName, String modelName) {
	 * List<DataFilterObject> dataFilterList = user.getDataFilterList(); //
	 * 用戶未設置過濾條件 if (ValidateUtil.isEmpty(dataFilterList)) { return "(1=1)"; }
	 * Object groupIdObject = DataControlManger.modelNameMap.get(modelName); //
	 * 表未設置組合 if (groupIdObject == null) { return "(1=1)"; } long groupId =
	 * Long.parseLong(groupIdObject.toString()); for (DataFilterObject
	 * dataFilterObject : dataFilterList) { // 組合ID匹配 if
	 * (dataFilterObject.getGroupdId() == groupId) { String groupFilterId =
	 * String.valueOf(dataFilterObject.getGroupFilterId()); String filterHql =
	 * DataControlManger.hqlFilterMap.get(groupFilterId).toString(); if
	 * (ValidateUtil.isEmpty(aliasName)) { filterHql = filterHql.replace("#.", "");
	 * } else { filterHql = filterHql.replace("#.", aliasName + "."); } return "(" +
	 * filterHql + ")"; } } return "(1=1)"; }
	 */

	/**
	 * 根據用戶物件，獲取數據過濾的SQL語句
	 * 
	 * @param user 用戶物件
	 * @return
	 */
	/**
	 * public String getFilterSql(User user) { String modelName =
	 * modelClass.getSimpleName(); return getFilterSql(user, null, modelName); }
	 */
	/**
	 * 根據用戶物件，獲取數據過濾的HQL語句
	 * 
	 * @param user      用戶物件
	 * @param aliasName 別名
	 * @return
	 */
	/**
	 * public String getFilterSql(User user, String aliasName) { String modelName =
	 * modelClass.getSimpleName(); return getFilterSql(user, aliasName, modelName);
	 * }
	 */

	/**
	 * 根據用戶物件，獲取數據過濾的SQL語句
	 * 
	 * @param user      用戶物件
	 * @param aliasName 別名
	 * @param modelName 實體類名
	 * @return
	 */
	/**
	 * public String getFilterSql(User user, String aliasName, String modelName) {
	 * List<DataFilterObject> dataFilterList = user.getDataFilterList(); //
	 * 用戶未設置過濾條件 if (ValidateUtil.isEmpty(dataFilterList)) { return "(1=1)"; }
	 * Object groupIdObject = DataControlManger.modelNameMap.get(modelName); //
	 * 表未設置組合 if (groupIdObject == null) { return "(1=1)"; } long groupId =
	 * Long.parseLong(groupIdObject.toString()); for (DataFilterObject
	 * dataFilterObject : dataFilterList) { // 組合ID匹配 if
	 * (dataFilterObject.getGroupdId() == groupId) { String groupFilterId =
	 * String.valueOf(dataFilterObject.getGroupFilterId()); String filterHql =
	 * DataControlManger.sqlFilterMap.get(groupFilterId).toString(); if
	 * (ValidateUtil.isEmpty(aliasName)) { filterHql = filterHql.replace("#.", "");
	 * } else { filterHql = filterHql.replace("#.", aliasName + "."); } return "(" +
	 * filterHql + ")"; } } return "(1=1)"; }
	 */

	/**
	 * 取得物件的主鍵名.
	 */
	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClazz);
		return meta.getIdentifierPropertyName();
	}

}
