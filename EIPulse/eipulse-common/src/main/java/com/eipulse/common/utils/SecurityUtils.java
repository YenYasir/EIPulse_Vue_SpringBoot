package com.eipulse.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.eipulse.common.constant.HttpStatus;
import com.eipulse.common.core.domain.model.LoginUser;
import com.eipulse.common.exception.ServiceException;

/**
 * 安全服務工具類
 * 
 * @author eipulse
 */
public class SecurityUtils {
	/**
	 * 員工ID
	 **/
	public static Long getUserId() {
		try {
			return getLoginUser().getUserId();
		} catch (Exception e) {
			throw new ServiceException("獲取員工ID異常", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * 獲取部門ID
	 **/
	public static Long getDeptId() {
		try {
			return getLoginUser().getDeptId();
		} catch (Exception e) {
			throw new ServiceException("獲取部門ID異常", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * 獲取員工帳戶
	 **/
	public static String getUsername() {
		try {
			return getLoginUser().getUsername();
		} catch (Exception e) {
			throw new ServiceException("獲取員工帳戶異常", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * 獲取員工
	 **/
	public static LoginUser getLoginUser() {
		try {
			return (LoginUser) getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new ServiceException("獲取員工資訊異常", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * 獲取Authentication
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 生成BCryptPasswordEncoder密碼
	 *
	 * @param password 密碼
	 * @return 加密字串
	 */
	public static String encryptPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}

	/**
	 * 判斷密碼是否相同
	 *
	 * @param rawPassword     真實密碼
	 * @param encodedPassword 加密後字符
	 * @return 結果
	 */
	public static boolean matchesPassword(String rawPassword, String encodedPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	/**
	 * 是否為管理員
	 * 
	 * @param userId 員工ID
	 * @return 結果
	 */
	public static boolean isAdmin(Long userId) {
		return userId != null && 1L == userId;
	}
}
