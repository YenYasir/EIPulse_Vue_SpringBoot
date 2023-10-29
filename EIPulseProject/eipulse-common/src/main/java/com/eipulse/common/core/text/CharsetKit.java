package com.eipulse.common.core.text;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.eipulse.common.utils.StringUtils;

/**
 * 字元集工具類
 */
public class CharsetKit {
	/**
	 * ISO-8859-1
	 */
	public static final String ISO_8859_1 = "ISO-8859-1";
	/**
	 * UTF-8
	 */
	public static final String UTF_8 = "UTF-8";
	/**
	 * GBK
	 */
	public static final String GBK = "GBK";

	/**
	 * ISO-8859-1
	 */
	public static final Charset CHARSET_ISO_8859_1 = Charset.forName(ISO_8859_1);
	/**
	 * UTF-8
	 */
	public static final Charset CHARSET_UTF_8 = Charset.forName(UTF_8);
	/**
	 * GBK
	 */
	public static final Charset CHARSET_GBK = Charset.forName(GBK);

	/**
	 * 轉換為Charset物件
	 *
	 * @param charset 字元集，為空則返回默認字元集
	 * @return Charset
	 */
	public static Charset charset(String charset) {
		return StringUtils.isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset);
	}

	/**
	 * 轉換字串的字元集編碼
	 *
	 * @param source      字串
	 * @param srcCharset  源字元集，默認ISO-8859-1
	 * @param destCharset 目標字元集，默認UTF-8
	 * @return 轉換後的字元集
	 */
	public static String convert(String source, String srcCharset, String destCharset) {
		return convert(source, Charset.forName(srcCharset), Charset.forName(destCharset));
	}

	/**
	 * 轉換字串的字元集編碼
	 *
	 * @param source      字串
	 * @param srcCharset  源字元集，默認ISO-8859-1
	 * @param destCharset 目標字元集，默認UTF-8
	 * @return 轉換後的字元集
	 */
	public static String convert(String source, Charset srcCharset, Charset destCharset) {
		if (null == srcCharset) {
			srcCharset = StandardCharsets.ISO_8859_1;
		}

		if (null == destCharset) {
			destCharset = StandardCharsets.UTF_8;
		}

		if (StringUtils.isEmpty(source) || srcCharset.equals(destCharset)) {
			return source;
		}
		return new String(source.getBytes(srcCharset), destCharset);
	}

	/**
	 * @return 系統字元集編碼
	 */
	public static String systemCharset() {
		return Charset.defaultCharset().name();
	}
}
