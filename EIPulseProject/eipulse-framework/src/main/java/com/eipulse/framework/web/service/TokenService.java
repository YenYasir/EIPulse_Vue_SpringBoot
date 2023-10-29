package com.eipulse.framework.web.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eipulse.common.constant.Constants;
import com.eipulse.common.core.domain.model.LoginUser;
import com.eipulse.common.core.redis.RedisCache;
import com.eipulse.common.utils.ServletUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.ip.AddressUtils;
import com.eipulse.common.utils.ip.IpUtils;
import com.eipulse.common.utils.uuid.IdUtils;

import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * token驗證處理
 */
@Component
public class TokenService {
	// 令牌自訂標識
	@Value("${token.header}")
	private String header;

	// 令牌秘鑰
	@Value("${token.secret}")
	private String secret;

	// 令牌有效期（默認30分鐘）
	@Value("${token.expireTime}")
	private int expireTime;

	protected static final long MILLIS_SECOND = 1000;

	protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

	private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

	@Autowired
	private RedisCache redisCache;

	/**
	 * 獲取員工身份資訊
	 *
	 * @return 員工資訊
	 */
	public LoginUser getLoginUser(HttpServletRequest request) {
		// 獲取請求攜帶的令牌
		String token = getToken(request);
		if (StringUtils.isNotEmpty(token)) {
			Claims claims = parseToken(token);
			// 解析對應的權限以及員工資訊
			String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
			String userKey = getTokenKey(uuid);
			LoginUser user = redisCache.getCacheObject(userKey);
			return user;
		}
		return null;
	}

	/**
	 * 設置員工身份資訊
	 */
	public void setLoginUser(LoginUser loginUser) {
		if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken())) {
			refreshToken(loginUser);
		}
	}

	/**
	 * 刪除員工身份資訊
	 */
	public void delLoginUser(String token) {
		if (StringUtils.isNotEmpty(token)) {
			String userKey = getTokenKey(token);
			redisCache.deleteObject(userKey);
		}
	}

	/**
	 * 創建令牌
	 *
	 * @param loginUser 員工資訊
	 * @return 令牌
	 */
	public String createToken(LoginUser loginUser) {
		String token = IdUtils.fastUUID();
		loginUser.setToken(token);
		setUserAgent(loginUser);
		refreshToken(loginUser);

		Map<String, Object> claims = new HashMap<>();
		claims.put(Constants.LOGIN_USER_KEY, token);
		return createToken(claims);
	}

	/**
	 * 驗證令牌有效期，相差不足20分鐘，自動刷新快取
	 *
	 * @param loginUser
	 * @return 令牌
	 */
	public void verifyToken(LoginUser loginUser) {
		long expireTime = loginUser.getExpireTime();
		long currentTime = System.currentTimeMillis();
		if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
			refreshToken(loginUser);
		}
	}

	/**
	 * 刷新令牌有效期
	 *
	 * @param loginUser 登入資訊
	 */
	public void refreshToken(LoginUser loginUser) {
		loginUser.setLoginTime(System.currentTimeMillis());
		loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
		// 根據uuid將loginUser快取
		String userKey = getTokenKey(loginUser.getToken());
		redisCache.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
	}

	/**
	 * 設置員工代理資訊
	 *
	 * @param loginUser 登入資訊
	 */
	public void setUserAgent(LoginUser loginUser) {
		UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
		String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
		loginUser.setIpaddr(ip);
		loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
		loginUser.setBrowser(userAgent.getBrowser().getName());
		loginUser.setOs(userAgent.getOperatingSystem().getName());
	}

	/**
	 * 從數據聲明生成令牌
	 *
	 * @param claims 數據聲明
	 * @return 令牌
	 */
	private String createToken(Map<String, Object> claims) {
		String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
		return token;
	}

	/**
	 * 從令牌中獲取數據聲明
	 *
	 * @param token 令牌
	 * @return 數據聲明
	 */
	private Claims parseToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	/**
	 * 從令牌中獲取員工名
	 *
	 * @param token 令牌
	 * @return 員工名
	 */
	public String getUsernameFromToken(String token) {
		Claims claims = parseToken(token);
		return claims.getSubject();
	}

	/**
	 * 獲取請求token
	 *
	 * @param request
	 * @return token
	 */
	private String getToken(HttpServletRequest request) {
		String token = request.getHeader(header);
		if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
			token = token.replace(Constants.TOKEN_PREFIX, "");
		}
		return token;
	}

	private String getTokenKey(String uuid) {
		return Constants.LOGIN_TOKEN_KEY + uuid;
	}
}
