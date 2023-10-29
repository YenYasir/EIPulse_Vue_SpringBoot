package com.eipulse.framework.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.eipulse.common.constant.HttpStatus;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.exception.BaseException;
import com.eipulse.common.exception.CustomException;
import com.eipulse.common.exception.DemoModeException;
import com.eipulse.common.utils.StringUtils;

/**
 * 全域異常處理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * 基礎異常
	 */
	@ExceptionHandler(BaseException.class)
	public AjaxResult baseException(BaseException e) {
		return AjaxResult.error(e.getMessage());
	}

	/**
	 * 業務異常
	 */
	@ExceptionHandler(CustomException.class)
	public AjaxResult businessException(CustomException e) {
		if (StringUtils.isNull(e.getCode())) {
			return AjaxResult.error(e.getMessage());
		}
		return AjaxResult.error(e.getCode(), e.getMessage());
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public AjaxResult handlerNoFoundException(Exception e) {
		log.error(e.getMessage(), e);
		return AjaxResult.error(HttpStatus.NOT_FOUND, "路徑不存在，請檢查路徑是否正確");
	}

	@ExceptionHandler(AccessDeniedException.class)
	public AjaxResult handleAuthorizationException(AccessDeniedException e) {
		log.error(e.getMessage());
		return AjaxResult.error(HttpStatus.FORBIDDEN, "沒有權限，請聯絡管理員授權");
	}

	@ExceptionHandler(AccountExpiredException.class)
	public AjaxResult handleAccountExpiredException(AccountExpiredException e) {
		log.error(e.getMessage(), e);
		return AjaxResult.error(e.getMessage());
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public AjaxResult handleUsernameNotFoundException(UsernameNotFoundException e) {
		log.error(e.getMessage(), e);
		return AjaxResult.error(e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public AjaxResult handleException(Exception e) {
		log.error(e.getMessage(), e);
		return AjaxResult.error(e.getMessage());
	}

	/**
	 * 自訂驗證異常
	 */
	@ExceptionHandler(BindException.class)
	public AjaxResult validatedBindException(BindException e) {
		log.error(e.getMessage(), e);
		String message = e.getAllErrors().get(0).getDefaultMessage();
		return AjaxResult.error(message);
	}

	/**
	 * 自訂驗證異常
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Object validExceptionHandler(MethodArgumentNotValidException e) {
		log.error(e.getMessage(), e);
		String message = e.getBindingResult().getFieldError().getDefaultMessage();
		return AjaxResult.error(message);
	}

	/**
	 * 示範模式異常
	 */
	@ExceptionHandler(DemoModeException.class)
	public AjaxResult demoModeException(DemoModeException e) {
		return AjaxResult.error("示範模式，不允許操作");
	}
}
