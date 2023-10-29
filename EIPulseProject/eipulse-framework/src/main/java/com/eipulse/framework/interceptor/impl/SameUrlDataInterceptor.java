package com.eipulse.framework.interceptor.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.eipulse.common.constant.Constants;
import com.eipulse.common.core.redis.RedisCache;
import com.eipulse.common.filter.RepeatedlyRequestWrapper;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.http.HttpHelper;
import com.eipulse.framework.interceptor.RepeatSubmitInterceptor;

/**
 * 判斷請求url和數據是否和上一次相同， 如果和上次相同，則是重複提交表單。 有效時間為10秒內。
 */
@Component
public class SameUrlDataInterceptor extends RepeatSubmitInterceptor {
	public final String REPEAT_PARAMS = "repeatParams";

	public final String REPEAT_TIME = "repeatTime";

	// 令牌自訂標識
	@Value("${token.header}")
	private String header;

	@Autowired
	private RedisCache redisCache;

	/**
	 * 間隔時間，單位:秒 默認10秒
	 * <p>
	 * 兩次相同參數的請求，如果間隔時間大於該參數，系統不會認定為重複提交的數據
	 */
	private int intervalTime = 10;

	public void setIntervalTime(int intervalTime) {
		this.intervalTime = intervalTime;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isRepeatSubmit(HttpServletRequest request) {
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

		// 請求位址（作為存放cache的key值）
		String url = request.getRequestURI();

		// 唯一值（沒有消息頭則使用請求位址）
		String submitKey = request.getHeader(header);
		if (StringUtils.isEmpty(submitKey)) {
			submitKey = url;
		}

		// 唯一標識（指定key + 消息頭）
		String cache_repeat_key = Constants.REPEAT_SUBMIT_KEY + submitKey;

		Object sessionObj = redisCache.getCacheObject(cache_repeat_key);
		if (sessionObj != null) {
			Map<String, Object> sessionMap = (Map<String, Object>) sessionObj;
			if (sessionMap.containsKey(url)) {
				Map<String, Object> preDataMap = (Map<String, Object>) sessionMap.get(url);
				if (compareParams(nowDataMap, preDataMap) && compareTime(nowDataMap, preDataMap)) {
					return true;
				}
			}
		}
		Map<String, Object> cacheMap = new HashMap<String, Object>();
		cacheMap.put(url, nowDataMap);
		redisCache.setCacheObject(cache_repeat_key, cacheMap, intervalTime, TimeUnit.SECONDS);
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
	private boolean compareTime(Map<String, Object> nowMap, Map<String, Object> preMap) {
		long time1 = (Long) nowMap.get(REPEAT_TIME);
		long time2 = (Long) preMap.get(REPEAT_TIME);
		if ((time1 - time2) < (this.intervalTime * 1000)) {
			return true;
		}
		return false;
	}
}
