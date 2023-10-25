package com.eipulse.framework.interceptor.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.eipulse.common.annotation.RepeatSubmit;
import com.eipulse.common.constant.Constants;
import com.eipulse.common.core.redis.RedisCache;
import com.eipulse.common.filter.RepeatedlyRequestWrapper;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.http.HttpHelper;
import com.eipulse.framework.interceptor.RepeatSubmitInterceptor;

/**
 * 判斷請求url和數據是否和上一次相同， 如果和上次相同，則是重覆提交表單。 有效時間為10秒內。
 * 
 * @author eipulse
 */
@Component
public class SameUrlDataInterceptor extends RepeatSubmitInterceptor {
	public final String REPEAT_PARAMS = "repeatParams";

	public final String REPEAT_TIME = "repeatTime";

	// token自定義標識
	@Value("${token.header}")
	private String header;

	@Autowired
	private RedisCache redisCache;

	@SuppressWarnings("unchecked")
	@Override
	public boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit annotation) {
		String nowParams = "";
		if (request instanceof RepeatedlyRequestWrapper) {
			RepeatedlyRequestWrapper repeatedlyRequest = (RepeatedlyRequestWrapper) request;
			nowParams = HttpHelper.getBodyString(repeatedlyRequest);
		}

		// body參數為空，獲取Parameter的數據
		if (StringUtils.isEmpty(nowParams)) {
			nowParams = JSONObject.toJSONString(request.getParameterMap());
		}
		Map<String, Object> nowDataMap = new HashMap<String, Object>();
		nowDataMap.put(REPEAT_PARAMS, nowParams);
		nowDataMap.put(REPEAT_TIME, System.currentTimeMillis());

		// 請求地址（作為存放cache的key值）
		String url = request.getRequestURI();

		// 唯一值（沒有消息頭則使用請求地址）
		String submitKey = request.getHeader(header);
		if (StringUtils.isEmpty(submitKey)) {
			submitKey = url;
		}

		// 唯一標識（指定key + 消息頭）
		String cacheRepeatKey = Constants.REPEAT_SUBMIT_KEY + submitKey;

		Object sessionObj = redisCache.getCacheObject(cacheRepeatKey);
		if (sessionObj != null) {
			Map<String, Object> sessionMap = (Map<String, Object>) sessionObj;
			if (sessionMap.containsKey(url)) {
				Map<String, Object> preDataMap = (Map<String, Object>) sessionMap.get(url);
				if (compareParams(nowDataMap, preDataMap)
						&& compareTime(nowDataMap, preDataMap, annotation.interval())) {
					return true;
				}
			}
		}
		Map<String, Object> cacheMap = new HashMap<String, Object>();
		cacheMap.put(url, nowDataMap);
		redisCache.setCacheObject(cacheRepeatKey, cacheMap, annotation.interval(), TimeUnit.MILLISECONDS);
		return false;
	}

	/**
	 * 判斷參數是否相同
	 */
	private boolean compareParams(Map<String, Object> nowMap, Map<String, Object> preMap) {
		String nowParams = (String) nowMap.get(REPEAT_PARAMS);
		String preParams = (String) preMap.get(REPEAT_PARAMS);
		return nowParams.equals(preParams);
	}

	/**
	 * 判斷兩次間隔時間
	 */
	private boolean compareTime(Map<String, Object> nowMap, Map<String, Object> preMap, int interval) {
		long time1 = (Long) nowMap.get(REPEAT_TIME);
		long time2 = (Long) preMap.get(REPEAT_TIME);
		if ((time1 - time2) < interval) {
			return true;
		}
		return false;
	}
}
