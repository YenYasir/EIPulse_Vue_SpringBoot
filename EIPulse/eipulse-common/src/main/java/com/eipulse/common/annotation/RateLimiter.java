package com.eipulse.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.eipulse.common.constant.Constants;
import com.eipulse.common.enums.LimitType;

/**
 * 限流註解
 * 
 * @author eipulse
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {
	/**
	 * 限流key
	 */
	public String key() default Constants.RATE_LIMIT_KEY;

	/**
	 * 限流時間,單位秒
	 */
	public int time() default 60;

	/**
	 * 限流次數
	 */
	public int count() default 100;

	/**
	 * 限流類型
	 */
	public LimitType limitType() default LimitType.DEFAULT;
}
