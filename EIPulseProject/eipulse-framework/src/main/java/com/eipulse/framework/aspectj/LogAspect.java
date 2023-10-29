package com.eipulse.framework.aspectj;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import com.alibaba.fastjson.JSON;
import com.eipulse.common.annotation.Log;
import com.eipulse.common.core.domain.model.LoginUser;
import com.eipulse.common.enums.BusinessStatus;
import com.eipulse.common.enums.HttpMethod;
import com.eipulse.common.utils.ServletUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.ip.IpUtils;
import com.eipulse.common.utils.spring.SpringUtils;
import com.eipulse.framework.manager.AsyncManager;
import com.eipulse.framework.manager.factory.AsyncFactory;
import com.eipulse.framework.web.service.TokenService;
import com.eipulse.system.domain.SysOperLog;

/**
 * 操作日誌記錄處理
 */
@Aspect
@Component
public class LogAspect {
	private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

	// 配置織入點
	@Pointcut("@annotation(com.eipulse.common.annotation.Log)")
	public void logPointCut() {
	}

	/**
	 * 處理完請求後執行
	 *
	 * @param joinPoint 切點
	 */
	@AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
	public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
		handleLog(joinPoint, null, jsonResult);
	}

	/**
	 * 攔截異常操作
	 *
	 * @param joinPoint 切點
	 * @param e         異常
	 */
	@AfterThrowing(value = "logPointCut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
		handleLog(joinPoint, e, null);
	}

	protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
		try {
			// 獲得註解
			Log controllerLog = getAnnotationLog(joinPoint);
			if (controllerLog == null) {
				return;
			}

			// 獲取當前的員工
			LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());

			// *========資料庫日誌=========*//
			SysOperLog operLog = new SysOperLog();
			operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
			// 請求的位址
			String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
			operLog.setOperIp(ip);
			// 返回參數
			operLog.setJsonResult(JSON.toJSONString(jsonResult));

			operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
			if (loginUser != null) {
				operLog.setOperName(loginUser.getUsername());
			}

			if (e != null) {
				operLog.setStatus(BusinessStatus.FAIL.ordinal());
				operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
			}
			// 設置方法名稱
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			operLog.setMethod(className + "." + methodName + "()");
			// 設置請求方式
			operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
			// 處理設置註解上的參數
			getControllerMethodDescription(joinPoint, controllerLog, operLog);
			// 保存資料庫
			AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
		} catch (Exception exp) {
			// 記錄本地異常日誌
			log.error("==前置通知異常==");
			log.error("異常資訊:{}", exp.getMessage());
			exp.printStackTrace();
		}
	}

	/**
	 * 獲取註解中對方法的描述資訊 用於Controller層註解
	 *
	 * @param log     日誌
	 * @param operLog 操作日誌
	 * @throws Exception
	 */
	public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLog operLog) throws Exception {
		// 設置action動作
		operLog.setBusinessType(log.businessType().ordinal());
		// 設置標題
		operLog.setTitle(log.title());
		// 設置操作人類別
		operLog.setOperatorType(log.operatorType().ordinal());
		// 是否需要保存request，參數和值
		if (log.isSaveRequestData()) {
			// 獲取參數的資訊，傳入到資料庫中。
			setRequestValue(joinPoint, operLog);
		}
	}

	/**
	 * 獲取請求的參數，放到log中
	 *
	 * @param operLog 操作日誌
	 * @throws Exception 異常
	 */
	private void setRequestValue(JoinPoint joinPoint, SysOperLog operLog) throws Exception {
		String requestMethod = operLog.getRequestMethod();
		if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
			String params = argsArrayToString(joinPoint.getArgs());
			operLog.setOperParam(StringUtils.substring(params, 0, 2000));
		} else {
			Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest()
					.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			operLog.setOperParam(StringUtils.substring(paramsMap.toString(), 0, 2000));
		}
	}

	/**
	 * 是否存在註解，如果存在就獲取
	 */
	private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		if (method != null) {
			return method.getAnnotation(Log.class);
		}
		return null;
	}

	/**
	 * 參數拼裝
	 */
	private String argsArrayToString(Object[] paramsArray) {
		String params = "";
		if (paramsArray != null && paramsArray.length > 0) {
			for (int i = 0; i < paramsArray.length; i++) {
				if (!isFilterObject(paramsArray[i])) {
					Object jsonObj = JSON.toJSON(paramsArray[i]);
					params += jsonObj.toString() + " ";
				}
			}
		}
		return params.trim();
	}

	/**
	 * 判斷是否需要過濾的物件。
	 *
	 * @param o 物件資訊。
	 * @return 如果是需要過濾的物件，則返回true；否則返回false。
	 */
	public boolean isFilterObject(final Object o) {
		return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
	}
}
