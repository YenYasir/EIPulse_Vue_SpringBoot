package com.eipulse.common.enums;

/**
 * 限流類型
 *
 * @author eipulse
 */

public enum LimitType {
	/**
	 * 默認策略全域限流
	 */
	DEFAULT,

	/**
	 * 根據請求者IP進行限流
	 */
	IP
}
