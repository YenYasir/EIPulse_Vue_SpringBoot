package com.eipulse.common.core.domain;

import java.util.HashMap;

import com.eipulse.common.constant.HttpStatus;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.code.BusinessBizCode;

import lombok.Getter;

/**
 * 操作消息提醒
 */
public class AjaxResult extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	/**
	 * 初始化一個新創建的 AjaxResult 物件，使其表示一個空消息。
	 */
	public AjaxResult() {
	}

	/**
	 * 初始化一個新創建的 AjaxResult 物件
	 *
	 * @param code 狀態碼
	 * @param msg  返回內容
	 */
	public AjaxResult(int code, String msg) {
		super.put(MsgCode.CODE_TAG.getCode(), code);
		super.put(MsgCode.MSG_TAG.getCode(), msg);
	}

	/**
	 * 初始化一個新創建的 AjaxResult 物件
	 *
	 * @param code 狀態碼
	 * @param msg  返回內容
	 * @param data 數據物件
	 */
	public AjaxResult(int code, String msg, Object data) {
		super.put(MsgCode.CODE_TAG.getCode(), code);
		super.put(MsgCode.MSG_TAG.getCode(), msg);
		if (StringUtils.isNotNull(data)) {
			super.put(MsgCode.DATA_TAG.getCode(), data);
		}
	}

	/**
	 * 返回成功消息
	 *
	 * @return 成功消息
	 */
	public static AjaxResult success() {
		return AjaxResult.success(BusinessBizCode.OPTION_SUCCESS.getMsg());
	}

	/**
	 * 返回成功數據
	 *
	 * @return 成功消息
	 */
	public static AjaxResult success(Object data) {
		return AjaxResult.success(BusinessBizCode.OPTION_SUCCESS.getMsg(), data);
	}

	/**
	 * 返回成功消息
	 *
	 * @param msg 返回內容
	 * @return 成功消息
	 */
	public static AjaxResult success(String msg) {
		return AjaxResult.success(msg, null);
	}

	/**
	 * 返回成功消息
	 *
	 * @param msg  返回內容
	 * @param data 數據物件
	 * @return 成功消息
	 */
	public static AjaxResult success(String msg, Object data) {
		return new AjaxResult(HttpStatus.SUCCESS, msg, data);
	}

	/**
	 * 返回錯誤消息
	 *
	 * @return
	 */
	public static AjaxResult error() {
		return AjaxResult.error(BusinessBizCode.OPTION_ERROR.getMsg());
	}

	/**
	 * 返回錯誤消息
	 *
	 * @param msg 返回內容
	 * @return 警告消息
	 */
	public static AjaxResult error(String msg) {
		return AjaxResult.error(msg, null);
	}

	/**
	 * 返回錯誤消息
	 *
	 * @param msg  返回內容
	 * @param data 數據物件
	 * @return 警告消息
	 */
	public static AjaxResult error(String msg, Object data) {
		return new AjaxResult(HttpStatus.ERROR, msg, data);
	}

	/**
	 * 返回錯誤消息
	 *
	 * @param code 狀態碼
	 * @param msg  返回內容
	 * @return 警告消息
	 */
	public static AjaxResult error(int code, String msg) {
		return new AjaxResult(code, msg, null);
	}

	/**
	 * 操作消息提醒基礎碼: code.狀態碼 , msg.返回內容 ,data.數據物件
	 */
	public enum MsgCode {

		/**
		 * 狀態碼
		 */
		CODE_TAG("code"),

		/**
		 * 數據物件
		 */
		DATA_TAG("data"),

		/**
		 * 返回內容
		 */
		MSG_TAG("msg");

		@Getter
		private String code;

		MsgCode(String code) {
			this.code = code;
		}
	}

}
