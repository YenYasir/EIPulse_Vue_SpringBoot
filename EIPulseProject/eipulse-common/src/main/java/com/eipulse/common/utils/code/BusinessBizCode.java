package com.eipulse.common.utils.code;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.eipulse.common.exception.ServiceException;

/**
 * 通用服務狀態碼
 *
 * <pre>
 * 狀態碼範圍：
 * 12000 ~ 12999
 * </pre>
 */
public enum BusinessBizCode {

	OPTION_SUCCESS(12000, "操作成功"), OPTION_ERROR(12001, "操作失敗"), TEMPLATE_NOT_EXISTS(12002, "模板[%s]不存在"),

	;

	private int code;
	private String msg;

	BusinessBizCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public ServiceException exception(String msg) {
		return new ServiceException(this.code, msg == null ? this.msg : msg);
	}

	public ServiceException exception() {
		return new ServiceException(this.code, this.msg);
	}

	public ServiceException exceptionFormat(Object... args) {
		return new ServiceException(this.code, String.format(this.msg, args));
	}

	private static final Map<Integer, BusinessBizCode> ENUM_MAP = new HashMap<>();

	static {
		ENUM_MAP.putAll(
				Stream.of(BusinessBizCode.values()).collect(Collectors.toMap(BusinessBizCode::getCode, e -> e)));
	}

	public static String getMsg(int code) {
		return Optional.of(ENUM_MAP.get(code)).map(BusinessBizCode::getMsg).orElse("");
	}
}
