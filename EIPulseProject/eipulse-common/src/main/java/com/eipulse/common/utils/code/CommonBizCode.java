package com.eipulse.common.utils.code;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
 * 通用枚舉編碼
 */
public class CommonBizCode {

	/**
	 * 刪除標記: 1.刪除 0.正常
	 */
	public enum DelFlag {

		YES("1", "刪除"),

		NO("0", "正常");

		@Getter
		private String code;

		@Getter
		private String value;

		DelFlag(String code, String value) {
			this.code = code;
			this.value = value;
		}

		private static final Map<String, DelFlag> ENUM_MAP = new HashMap<>();

		static {
			DelFlag[] enumArr = DelFlag.values();
			for (DelFlag enumInfo : enumArr) {
				ENUM_MAP.put(enumInfo.getCode(), enumInfo);
			}
		}

		public static DelFlag asEnum(int code) {
			return ENUM_MAP.get(code);
		}
	}

}