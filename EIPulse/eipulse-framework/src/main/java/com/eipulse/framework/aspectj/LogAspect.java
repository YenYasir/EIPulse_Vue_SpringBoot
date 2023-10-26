package com.eipulse.framework.aspectj;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import com.alibaba.fastjson.JSON;
import com.eipulse.common.annotation.Log;
import com.eipulse.common.core.domain.model.LoginUser;
import com.eipulse.common.enums.BusinessStatus;
import com.eipulse.common.enums.HttpMethod;
import com.eipulse.common.utils.SecurityUtils;
import com.eipulse.common.utils.ServletUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.ip.IpUtils;
import com.eipulse.framework.manager.AsyncManager;
import com.eipulse.framework.manager.factory.AsyncFactory;
import com.eipulse.system.domain.SysOperLog;

/**
 * 操作日誌記錄處理
 * 
 * @author eipulse
 */
@Aspect
@Component
public class LogAspect {
	private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

	/**
	 * 處理完請求後執行
	 *
	 * @param joinPoint 切點
	 */
	@AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
	public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
		handleLog(joinPoint, controllerLog, null, jsonResult);
	}

	/**
	 * 攔截異常操作
	 * 
	 * @param joinPoint 切點
	 * @param e         異常
	 */
	@AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e) {
		handleLog(joinPoint, controllerLog, e, null);
	}

	protected void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e, Object jsonResult) {
		try {

			// 獲取當前的員工
			LoginUser loginUser = SecurityUtils.getLoginUser();

			// *========數據庫日誌=========*//
			SysOperLog operLog = new SysOperLog();
			operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
			// 請求的地址
			String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
			operLog.setOperIp(ip);
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
			getControllerMethodDescription(joinPoint, controllerLog, operLog, jsonResult);
			// 保存數據庫
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
	public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLog operLog, Object jsonResult)
			throws Exception {
		// 設置action動作
		operLog.setBusinessType(log.businessType().ordinal());
		// 設置標題
		operLog.setTitle(log.title());
		// 設置操作人類別
		operLog.setOperatorType(log.operatorType().ordinal());
		// 是否需要保存request，參數和值
		if (log.isSaveRequestData()) {
			// 獲取參數的資訊，傳入到數據庫中。
			setRequestValue(joinPoint, operLog);
		}
		// 是否需要保存response，參數和值
		if (log.isSaveResponseData() && StringUtils.isNotNull(jsonResult)) {
			operLog.setJsonResult(StringUtils.substring(JSON.toJSONString(jsonResult), 0, 2000));
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
	 * 參數拼裝
	 */
	private String argsArrayToString(Object[] paramsArray) {
		String params = "";
		if (paramsArray != null && paramsArray.length > 0) {
			for (Object o : paramsArray) {
				if (StringUtils.isNotNull(o) && !isFilterObject(o)) {
					try {
						Object jsonObj = JSON.toJSON(o);
						params += jsonObj.toString() + " ";
					} catch (Exception e) {
					}
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
	@SuppressWarnings("rawtypes")
	public boolean isFilterObject(final Object o) {
		Class<?> clazz = o.getClass();
		if (clazz.isArray()) {
			return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
		} else if (Collection.class.isAssignableFrom(clazz)) {
			Collection collection = (Collection) o;
			for (Object value : collection) {
				return value instanceof MultipartFile;
			}
		} else if (Map.class.isAssignableFrom(clazz)) {
			Map map = (Map) o;
			for (Object value : map.entrySet()) {
				Map.Entry entry = (Map.Entry) value;
				return entry.getValue() instanceof MultipartFile;
			}
		}
		return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
				|| o instanceof BindingResult;
	}
}
