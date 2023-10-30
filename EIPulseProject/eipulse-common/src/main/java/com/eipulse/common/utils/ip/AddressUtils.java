package com.eipulse.common.utils.ip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.eipulse.common.config.EipulseConfig;
import com.eipulse.common.constant.Constants;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.http.HttpUtils;

/**
 * 獲取位址類
 */
public class AddressUtils {
	private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

	// IP位址查詢
	public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

	// 未知位址
	public static final String UNKNOWN = "XX XX";

	public static String getRealAddressByIP(String ip) {
		String address = UNKNOWN;
		// 內網不查詢
		if (IpUtils.internalIp(ip)) {
			return "內網IP";
		}
		if (EipulseConfig.isAddressEnabled()) {
			try {
				String rspStr = HttpUtils.sendGet(IP_URL, "ip=" + ip + "&json=true", Constants.GBK);
				if (StringUtils.isEmpty(rspStr)) {
					log.error("獲取地理位置異常 {}", ip);
					return UNKNOWN;
				}
				JSONObject obj = JSONObject.parseObject(rspStr);
				String region = obj.getString("pro");
				String city = obj.getString("city");
				return String.format("%s %s", region, city);
			} catch (Exception e) {
				log.error("獲取地理位置異常 {}", ip);
			}
		}
		return address;
	}
}
