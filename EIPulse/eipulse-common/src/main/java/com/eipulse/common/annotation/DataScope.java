package com.eipulse.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 數據權限過濾註解
 * 
 * @author eipulse
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
	/**
	 * 部門表的別名
	 */
	public String deptAlias() default "";

	/**
	 * 員工表的別名
	 */
	public String userAlias() default "";
}
