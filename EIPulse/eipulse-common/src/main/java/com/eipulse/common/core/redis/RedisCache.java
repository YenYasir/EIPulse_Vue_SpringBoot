package com.eipulse.common.core.redis;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * spring redis 工具類
 *
 * @author eipulse
 **/
@SuppressWarnings(value = { "unchecked", "rawtypes" })
@Component
public class RedisCache {
	@Autowired
	public RedisTemplate redisTemplate;

	/**
	 * 緩存基本的物件，Integer、String、實體類等
	 *
	 * @param key   緩存的鍵值
	 * @param value 緩存的值
	 */
	public <T> void setCacheObject(final String key, final T value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 緩存基本的物件，Integer、String、實體類等
	 *
	 * @param key      緩存的鍵值
	 * @param value    緩存的值
	 * @param timeout  時間
	 * @param timeUnit 時間顆粒度
	 */
	public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
	}

	/**
	 * 設置有效時間
	 *
	 * @param key     Redis鍵
	 * @param timeout 超時時間
	 * @return true=設置成功；false=設置失敗
	 */
	public boolean expire(final String key, final long timeout) {
		return expire(key, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 設置有效時間
	 *
	 * @param key     Redis鍵
	 * @param timeout 超時時間
	 * @param unit    時間單位
	 * @return true=設置成功；false=設置失敗
	 */
	public boolean expire(final String key, final long timeout, final TimeUnit unit) {
		return redisTemplate.expire(key, timeout, unit);
	}

	/**
	 * 獲得緩存的基本物件。
	 *
	 * @param key 緩存鍵值
	 * @return 緩存鍵值對應的數據
	 */
	public <T> T getCacheObject(final String key) {
		ValueOperations<String, T> operation = redisTemplate.opsForValue();
		return operation.get(key);
	}

	/**
	 * 刪除單個物件
	 *
	 * @param key
	 */
	public boolean deleteObject(final String key) {
		return redisTemplate.delete(key);
	}

	/**
	 * 刪除集合物件
	 *
	 * @param collection 多個物件
	 * @return
	 */
	public long deleteObject(final Collection collection) {
		return redisTemplate.delete(collection);
	}

	/**
	 * 緩存List數據
	 *
	 * @param key      緩存的鍵值
	 * @param dataList 待緩存的List數據
	 * @return 緩存的物件
	 */
	public <T> long setCacheList(final String key, final List<T> dataList) {
		Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
		return count == null ? 0 : count;
	}

	/**
	 * 獲得緩存的list物件
	 *
	 * @param key 緩存的鍵值
	 * @return 緩存鍵值對應的數據
	 */
	public <T> List<T> getCacheList(final String key) {
		return redisTemplate.opsForList().range(key, 0, -1);
	}

	/**
	 * 緩存Set
	 *
	 * @param key     緩存鍵值
	 * @param dataSet 緩存的數據
	 * @return 緩存數據的物件
	 */
	public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet) {
		BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
		Iterator<T> it = dataSet.iterator();
		while (it.hasNext()) {
			setOperation.add(it.next());
		}
		return setOperation;
	}

	/**
	 * 獲得緩存的set
	 *
	 * @param key
	 * @return
	 */
	public <T> Set<T> getCacheSet(final String key) {
		return redisTemplate.opsForSet().members(key);
	}

	/**
	 * 緩存Map
	 *
	 * @param key
	 * @param dataMap
	 */
	public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
		if (dataMap != null) {
			redisTemplate.opsForHash().putAll(key, dataMap);
		}
	}

	/**
	 * 獲得緩存的Map
	 *
	 * @param key
	 * @return
	 */
	public <T> Map<String, T> getCacheMap(final String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * 往Hash中存入數據
	 *
	 * @param key   Redis鍵
	 * @param hKey  Hash鍵
	 * @param value 值
	 */
	public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
		redisTemplate.opsForHash().put(key, hKey, value);
	}

	/**
	 * 獲取Hash中的數據
	 *
	 * @param key  Redis鍵
	 * @param hKey Hash鍵
	 * @return Hash中的物件
	 */
	public <T> T getCacheMapValue(final String key, final String hKey) {
		HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
		return opsForHash.get(key, hKey);
	}

	/**
	 * 獲取多個Hash中的數據
	 *
	 * @param key   Redis鍵
	 * @param hKeys Hash鍵集合
	 * @return Hash物件集合
	 */
	public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
		return redisTemplate.opsForHash().multiGet(key, hKeys);
	}

	/**
	 * 獲得緩存的基本物件列表
	 *
	 * @param pattern 字符串前綴
	 * @return 物件列表
	 */
	public Collection<String> keys(final String pattern) {
		return redisTemplate.keys(pattern);
	}
}
