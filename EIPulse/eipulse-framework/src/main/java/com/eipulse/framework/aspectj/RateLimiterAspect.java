package com.eipulse.framework.aspectj;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import com.eipulse.common.annotation.RateLimiter;
import com.eipulse.common.enums.LimitType;
import com.eipulse.common.exception.ServiceException;
import com.eipulse.common.utils.ServletUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.ip.IpUtils;

/**
 * 限流處理
 *
 * @author eipulse
 */
@Aspect
@Component
public class RateLimiterAspect {
	private static final Logger log = LoggerFactory.getLogger(RateLimiterAspect.class);

	private RedisTemplate<Object, Object> redisTemplate;

	private RedisScript<Long> limitScript;

	@Autowired
	public void setRedisTemplate1(RedisTemplate<Object, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Autowired
	public void setLimitScript(RedisScript<Long> limitScript) {
		this.limitScript = limitScript;
	}

	@Before("@annotation(rateLimiter)")
	public void doBefore(JoinPoint point, RateLimiter rateLimiter) throws Throwable {
		String key = rateLimiter.key();
		int time = rateLimiter.time();
		int count = rateLimiter.count();

		String combineKey = getCombineKey(rateLimiter, point);
		List<Object> keys = Collections.singletonList(combineKey);
		try {
			Long number = redisTemplate.execute(limitScript, keys, count, time);
			if (StringUtils.isNull(number) || number.intValue() > count) {
				throw new ServiceException("訪問過於頻繁，請稍候再試");
			}
			log.info("限制請求'{}',當前請求'{}',緩存key'{}'", count, number.intValue(), key);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("伺服器限流異常，請稍候再試");
		}
	}

	public String getCombineKey(RateLimiter rateLimiter, JoinPoint point) {
		StringBuffer stringBuffer = new StringBuffer(rateLimiter.key());
		if (rateLimiter.limitType() == LimitType.IP) {
			stringBuffer.append(IpUtils.getIpAddr(ServletUtils.getRequest())).append("-");
		}
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Class<?> targetClass = method.getDeclaringClass();
		stringBuffer.append(targetClass.getName()).append("-").append(method.getName());
		return stringBuffer.toString();
	}
}
