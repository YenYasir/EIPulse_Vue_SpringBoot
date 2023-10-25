package com.eipulse.common.exception;

/**
 * 全域異常
 * 
 * @author eipulse
 */
public class GlobalException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 錯誤提示
	 */
	private String message;

	/**
	 * 錯誤明細，內部調試錯誤
	 *
	 * 和 {@link CommonResult#getDetailMessage()} 一致的設計
	 */
	private String detailMessage;

	/**
	 * 空構造方法，避免反序列化問題
	 */
	public GlobalException() {
	}

	public GlobalException(String message) {
		this.message = message;
	}

	public String getDetailMessage() {
		return detailMessage;
	}

	public GlobalException setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
		return this;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public GlobalException setMessage(String message) {
		this.message = message;
		return this;
	}
}