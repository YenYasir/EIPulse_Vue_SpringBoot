package com.eipulse.common.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 通用的web層日誌記錄註解
 */
@Documented
@Retention(RUNTIME)
@Target({ METHOD })
public @interface WebLog {

	/**
	 * 自訂日誌方法名
	 * 
	 * @return
	 */
	String value() default "";

	/**
	 * 是否忽略列印
	 * 
	 * @return
	 */
	boolean ignore() default false;

	/**
	 * 是列印返回參數
	 * 
	 * @return
	 */
	boolean logReturn() default false;

}
