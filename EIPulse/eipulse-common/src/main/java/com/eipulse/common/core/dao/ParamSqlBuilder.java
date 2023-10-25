package com.eipulse.common.core.dao;

import java.util.HashMap;
import java.util.Map;

import com.eipulse.common.exception.base.BaseException;
import com.eipulse.common.utils.ValidateUtil;

/**
 * 1、參數化sql構造器，使用hql在併發查詢存在解析的性能，因此併發量大的必須使用sql執行，而相應的sql應該禁止使用String想加和參數拼接
 * 2、使用StringBuilder來拼接sql字串，是比String的+和StringBuferr性能都高 3、sql盡量使用參數化，使用?和
 * :變量都可，這樣可以提高sql的查詢命中率，減少數據庫在解析sql的性能消耗
 * 
 * @author eipulse
 *
 */
public class ParamSqlBuilder {
	private StringBuilder builder = new StringBuilder();
	private Map<String, Object> paramMap = new HashMap<String, Object>();
	private String paramType;
	public final static String INDEXPARAM = "INDEX";
	public final static String NAMEPARAM = "NAME";

	public ParamSqlBuilder() {
		builder = new StringBuilder();
	}

	public ParamSqlBuilder(Object str) {
		builder = builder.append(str);
	}

	/**
	 * 添加字串
	 * 
	 * @param str 字串
	 * @return
	 * @author Administrator
	 */
	public ParamSqlBuilder append(Object str) {
		builder.append(str);
		return this;
	}

	/**
	 * 設置sql裡變量未？的變量，即索引變量
	 * 
	 * @param param 參數
	 * @return
	 */
	public ParamSqlBuilder setIndexParam(Object param) {
		if (ValidateUtil.isNull(paramType)) {
			paramType = this.INDEXPARAM;
		} else {
			if (!paramType.equals(this.INDEXPARAM)) {
				throw new BaseException("無效的SQL參數定義方式，嘗試設置為索引參數，但是已經設置過命名參數");
			}
		}
		paramMap.put(String.valueOf(paramMap.size()), param);
		return this;
	}

	/**
	 * 設置sql裡變量未？的變量，即索引變量,一次性全部設置
	 * 
	 * @param params 參數數組
	 * @return
	 */
	public ParamSqlBuilder setIndexParams(Object[] params) {

		for (Object param : params) {
			setIndexParam(param);
		}
		return this;
	}

	/**
	 * 設置sql變量為名字的變量，即命名變量
	 * 
	 * @param key   變量名
	 * @param param 參數
	 * @return
	 */
	public ParamSqlBuilder setNameParam(String key, Object param) {
		if (ValidateUtil.isNull(paramType)) {
			paramType = this.NAMEPARAM;
		} else {
			if (!paramType.equals(this.NAMEPARAM)) {
				throw new BaseException("無效的SQL參數定義方式，嘗試設置為命名參數，但是已經設置過索引參數");
			}
		}
		paramMap.put(key, param);
		return this;
	}

	/**
	 * 取SQL語句
	 */
	public String getSql() {
		return builder.toString();
	}

	/**
	 * 獲取參數map集合
	 * 
	 * @return
	 */
	public Map getParamMap() {
		return paramMap;
	}

	public String getParamType() {
		return this.paramType;
	}

}
