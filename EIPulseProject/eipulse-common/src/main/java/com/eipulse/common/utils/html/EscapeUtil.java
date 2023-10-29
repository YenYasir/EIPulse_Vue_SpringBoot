package com.eipulse.common.utils.html;

import com.eipulse.common.utils.StringUtils;

/**
 * 轉義和反轉義工具類
 */
public class EscapeUtil {
	public static final String RE_HTML_MARK = "(<[^<]*?>)|(<[\\s]*?/[^<]*?>)|(<[^<]*?/[\\s]*?>)";

	private static final char[][] TEXT = new char[64][];

	static {
		for (int i = 0; i < 64; i++) {
			TEXT[i] = new char[] { (char) i };
		}

		// special HTML characters
		TEXT['\''] = "&#039;".toCharArray(); // 單引號
		TEXT['"'] = "&#34;".toCharArray(); // 單引號
		TEXT['&'] = "&#38;".toCharArray(); // &符
		TEXT['<'] = "&#60;".toCharArray(); // 小於號
		TEXT['>'] = "&#62;".toCharArray(); // 大於號
	}

	/**
	 * 轉義文本中的HTML字元為安全的字元
	 * 
	 * @param text 被轉義的文本
	 * @return 轉義後的文本
	 */
	public static String escape(String text) {
		return encode(text);
	}

	/**
	 * 還原被轉義的HTML特殊字元
	 * 
	 * @param content 包含轉義符的HTML內容
	 * @return 轉換後的字串
	 */
	public static String unescape(String content) {
		return decode(content);
	}

	/**
	 * 清除所有HTML標籤，但是不刪除標籤內的內容
	 * 
	 * @param content 文本
	 * @return 清除標籤後的文本
	 */
	public static String clean(String content) {
		return new HTMLFilter().filter(content);
	}

	/**
	 * Escape編碼
	 * 
	 * @param text 被編碼的文本
	 * @return 編碼後的字元
	 */
	private static String encode(String text) {
		int len;
		if ((text == null) || ((len = text.length()) == 0)) {
			return StringUtils.EMPTY;
		}
		StringBuilder buffer = new StringBuilder(len + (len >> 2));
		char c;
		for (int i = 0; i < len; i++) {
			c = text.charAt(i);
			if (c < 64) {
				buffer.append(TEXT[c]);
			} else {
				buffer.append(c);
			}
		}
		return buffer.toString();
	}

	/**
	 * Escape解碼
	 * 
	 * @param content 被轉義的內容
	 * @return 解碼後的字串
	 */
	public static String decode(String content) {
		if (StringUtils.isEmpty(content)) {
			return content;
		}

		StringBuilder tmp = new StringBuilder(content.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < content.length()) {
			pos = content.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (content.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(content.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(content.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(content.substring(lastPos));
					lastPos = content.length();
				} else {
					tmp.append(content.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	public static void main(String[] args) {
		String html = "<script>alert(1);</script>";
		// String html = "<scr<script>ipt>alert(\"XSS\")</scr<script>ipt>";
		// String html = "<123";
		// String html = "123>";
		System.out.println(EscapeUtil.clean(html));
		System.out.println(EscapeUtil.escape(html));
		System.out.println(EscapeUtil.unescape(html));
	}
}
