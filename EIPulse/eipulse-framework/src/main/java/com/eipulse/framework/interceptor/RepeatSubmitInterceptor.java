package com.eipulse.framework.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson.JSONObject;
import com.eipulse.common.annotation.RepeatSubmit;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.utils.ServletUtils;

/**
 * 防止重覆提交攔截器
 *
 * @author eipulse
 */
@Component
public abstract class RepeatSubmitInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
			if (annotation != null) {
				if (this.isRepeatSubmit(request, annotation)) {
					AjaxResult ajaxResult = AjaxResult.error(annotation.message());
					ServletUtils.renderString(response, JSONObject.toJSONString(ajaxResult));
					return false;
				}
			}
			return true;
		} else {
			return true;
		}
	}

	/**
	 * 驗證是否重覆提交由子類實現具體的防重覆提交的規則
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public abstract boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit annotation);
}
