package com.eipulse.common.core.domain.model;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.eipulse.common.core.domain.entity.SysUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 登入員工身份權限
 */
public class LoginUser implements UserDetails {

	private static final long serialVersionUID = 1L;

	/**
	 * 員工唯一標識
	 */
	private String token;

	/**
	 * 登入時間
	 */
	private Long loginTime;

	/**
	 * 過期時間
	 */
	private Long expireTime;

	/**
	 * 登入IP位址
	 */
	private String ipaddr;

	/**
	 * 登入地點
	 */
	private String loginLocation;

	/**
	 * 瀏覽器類型
	 */
	private String browser;

	/**
	 * 操作系統
	 */
	private String os;

	/**
	 * 權限列表
	 */
	private Set<String> permissions;

	/**
	 * 員工資訊
	 */
	private SysUser user;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LoginUser() {
	}

	public LoginUser(SysUser user, Set<String> permissions) {
		this.user = user;
		this.permissions = permissions;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserName();
	}

	/**
	 * 帳戶是否未過期,過期無法驗證
	 */
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 指定員工是否解鎖,鎖定的員工無法進行身份驗證
	 *
	 * @return
	 */
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * 指示是否已過期的員工的憑據(密碼),過期的憑據防止認證
	 *
	 * @return
	 */
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 是否可用 ,禁用的員工不能身份驗證
	 *
	 * @return
	 */
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

	public Long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public String getLoginLocation() {
		return loginLocation;
	}

	public void setLoginLocation(String loginLocation) {
		this.loginLocation = loginLocation;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
}
