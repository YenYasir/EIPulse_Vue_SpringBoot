package com.eipulse.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;

/**
 * 自訂導出Excel數據註解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel {
	/**
	 * 導出時在excel中排序
	 */
	int sort() default Integer.MAX_VALUE;

	/**
	 * 導出到Excel中的名字.
	 */
	String name() default "";

	/**
	 * 日期格式, 如: yyyy-MM-dd
	 */
	String dateFormat() default "";

	/**
	 * 如果是字典類型，請設置字典的type值 (如: sys_user_sex)
	 */
	String dictType() default "";

	/**
	 * 讀取內容轉表達式 (如: 0=男,1=女,2=未知)
	 */
	String readConverterExp() default "";

	/**
	 * 分隔符號，讀取字串組內容
	 */
	String separator() default ",";

	/**
	 * BigDecimal 精度 默認:-1(默認不開啟BigDecimal格式化)
	 */
	int scale() default -1;

	/**
	 * BigDecimal 捨入規則 默認:BigDecimal.ROUND_HALF_EVEN
	 */
	int roundingMode() default BigDecimal.ROUND_HALF_EVEN;

	/**
	 * 導出類型（0數字 1字串）
	 */
	ColumnType cellType() default ColumnType.STRING;

	/**
	 * 導出時在excel中每個列的高度 單位為字元
	 */
	double height() default 14;

	/**
	 * 導出時在excel中每個列的寬 單位為字元
	 */
	double width() default 16;

	/**
	 * 文字後綴,如% 90 變成90%
	 */
	String suffix() default "";

	/**
	 * 當值為空時,欄位的預設值
	 */
	String defaultValue() default "";

	/**
	 * 提示資訊
	 */
	String prompt() default "";

	/**
	 * 設置只能選擇不能輸入的列內容.
	 */
	String[] combo() default {};

	/**
	 * 是否導出數據,應對需求:有時我們需要導出一份模板,這是標題需要但內容需要員工手工填寫.
	 */
	boolean isExport() default true;

	/**
	 * 另一個類中的屬性名稱,支持多級獲取,以小數點隔開
	 */
	String targetAttr() default "";

	/**
	 * 是否自動統計數據,在最後追加一行統計數據總和
	 */
	boolean isStatistics() default false;

	/**
	 * 導出欄位對齊方式（0：默認；1：靠左；2：居中；3：靠右）
	 */
	Align align() default Align.AUTO;

	enum Align {
		AUTO(0), LEFT(1), CENTER(2), RIGHT(3);

		private final int value;

		Align(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	/**
	 * 欄位類型（0：導出導入；1：僅導出；2：僅導入）
	 */
	Type type() default Type.ALL;

	enum Type {
		ALL(0), EXPORT(1), IMPORT(2);

		private final int value;

		Type(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	enum ColumnType {
		NUMERIC(0), STRING(1);

		private final int value;

		ColumnType(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}
}