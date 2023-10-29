package com.eipulse.framework.aspectj;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.eipulse.common.annotation.WebLog;

import lombok.extern.slf4j.Slf4j;

/**
 * web日誌切面類
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void pointcut() {

	}

	@Around("pointcut()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature methodSign = (MethodSignature) joinPoint.getSignature();
		Method method = methodSign.getMethod();
		WebLog logAnno = AnnotationUtils.getAnnotation(method, WebLog.class);
		if (logAnno != null && logAnno.ignore()) {
			return joinPoint.proceed();
		}

		long beginTime = System.currentTimeMillis();
		ServletRequestAttributes requestAttr = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		String ip = requestAttr.getRequest().getRemoteAddr();
		String uri = requestAttr.getRequest().getRequestURI();
		log.info("Begin===> 請求ip：{}, URI: {}, 請求方法：{}, 請求參數: {}", ip, uri, joinPoint.getSignature().toShortString(),
				Arrays.toString(joinPoint.getArgs()));

		Object object = joinPoint.proceed();

		if (logAnno != null && logAnno.logReturn()) {
			log.info("End===> 請求ip：{}, URI: {}, 請求方法：{}, 請求參數: {}, 返回參數: {}, 耗時(ms): {}", ip, uri,
					joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()), JSON.toJSONString(object),
					System.currentTimeMillis() - beginTime);
		} else {
			log.info("End===> 請求ip：{}, URI: {}, 請求方法：{}, 請求參數: {}, 耗時(ms): {}", ip, uri,
					joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()),
					System.currentTimeMillis() - beginTime);
		}
		return object;
	}

}