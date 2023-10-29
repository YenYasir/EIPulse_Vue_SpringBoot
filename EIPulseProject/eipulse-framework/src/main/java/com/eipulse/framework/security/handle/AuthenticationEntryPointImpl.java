package com.eipulse.framework.security.handle;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.eipulse.common.constant.HttpStatus;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.utils.ServletUtils;
import com.eipulse.common.utils.StringUtils;

/**
 * 認證失敗處理類 返回未授權
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
	private static final long serialVersionUID = -8970718410437077606L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
			throws IOException {
		int code = HttpStatus.UNAUTHORIZED;
		String msg = StringUtils.format("請求訪問：{}，認證失敗，無法訪問系統資源", request.getRequestURI());
		ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(code, msg)));
	}
}
