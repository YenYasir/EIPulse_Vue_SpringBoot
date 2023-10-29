package com.eipulse.common.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.eipulse.common.utils.spring.SpringUtils;

/**
 * 獲取i18n資源檔案
 */
public class MessageUtils {
	/**
	 * 根據消息鍵和參數 獲取消息 委託給spring messageSource
	 *
	 * @param code 消息鍵
	 * @param args 參數
	 * @return 獲取國際化翻譯值
	 */
	public static String message(String code, Object... args) {
		MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	}
}
