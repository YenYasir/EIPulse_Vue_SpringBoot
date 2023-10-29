package com.eipulse.common.exception.job;

/**
 * 計劃策略異常
 */
public class TaskException extends Exception {
	private static final long serialVersionUID = 1L;

	private Code code;

	public TaskException(String msg, Code code) {
		this(msg, code, null);
	}

	public TaskException(String msg, Code code, Exception nestedEx) {
		super(msg, nestedEx);
		this.code = code;
	}

	public Code getCode() {
		return code;
	}

	public enum Code {
		TASK_EXISTS, NO_TASK_EXISTS, TASK_ALREADY_STARTED, UNKNOWN, CONFIG_ERROR, TASK_NODE_NOT_AVAILABLE
	}
}