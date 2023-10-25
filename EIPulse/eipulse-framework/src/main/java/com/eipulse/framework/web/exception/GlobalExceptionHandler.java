package com.eipulse.framework.web.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eipulse.common.constant.HttpStatus;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.exception.DemoModeException;
import com.eipulse.common.exception.ServiceException;
import com.eipulse.common.utils.StringUtils;

/**
 * 全域異常處理器
 * 
 * @author eipulse
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * 權限校驗異常
	 */
	@ExceptionHandler(AccessDeniedException.class)
	public AjaxResult handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		log.error("請求地址'{}',權限校驗失敗'{}'", requestURI, e.getMessage());
		return AjaxResult.error(HttpStatus.FORBIDDEN, "沒有權限，請聯繫管理員授權");
	}

	/**
	 * 請求方式不支持
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public AjaxResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
			HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		log.error("請求地址'{}',不支持'{}'請求", requestURI, e.getMethod());
		return AjaxResult.error(e.getMessage());
	}

	/**
	 * 業務異常
	 */
	@ExceptionHandler(ServiceException.class)
	public AjaxResult handleServiceException(ServiceException e, HttpServletRequest request) {
		log.error(e.getMessage(), e);
		Integer code = e.getCode();
		return StringUtils.isNotNull(code) ? AjaxResult.error(code, e.getMessage()) : AjaxResult.error(e.getMessage());
	}

	/**
	 * 攔截未知的運行時異常
	 */
	@ExceptionHandler(RuntimeException.class)
	public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		log.error("請求地址'{}',發生未知異常.", requestURI, e);
		return AjaxResult.error(e.getMessage());
	}

	/**
	 * 系統異常
	 */
	@ExceptionHandler(Exception.class)
	public AjaxResult handleException(Exception e, HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		log.error("請求地址'{}',發生系統異常.", requestURI, e);
		return AjaxResult.error(e.getMessage());
	}

	/**
	 * 自定義驗證異常
	 */
	@ExceptionHandler(BindException.class)
	public AjaxResult handleBindException(BindException e) {
		log.error(e.getMessage(), e);
		String message = e.getAllErrors().get(0).getDefaultMessage();
		return AjaxResult.error(message);
	}

	/**
	 * 自定義驗證異常
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error(e.getMessage(), e);
		String message = e.getBindingResult().getFieldError().getDefaultMessage();
		return AjaxResult.error(message);
	}

	/**
	 * 演示模式異常
	 */
	@ExceptionHandler(DemoModeException.class)
	public AjaxResult handleDemoModeException(DemoModeException e) {
		return AjaxResult.error("演示模式，不允許操作");
	}
}
