package com.eipulse.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.eipulse.common.enums.DataSourceType;

/**
 * 自定義多數據源切換註解
 *
 * 優先級：先方法，後類，如果方法覆蓋了類上的數據源類型，以方法的為準，否則以類上的為準
 *
 * @author eipulse
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {
	/**
	 * 切換數據源名稱
	 */
	public DataSourceType value() default DataSourceType.MASTER;
}
