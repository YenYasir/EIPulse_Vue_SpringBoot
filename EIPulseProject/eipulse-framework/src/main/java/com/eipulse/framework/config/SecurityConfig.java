package com.eipulse.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

import com.eipulse.framework.security.filter.JwtAuthenticationTokenFilter;
import com.eipulse.framework.security.handle.AuthenticationEntryPointImpl;
import com.eipulse.framework.security.handle.LogoutSuccessHandlerImpl;

/**
 * spring security配置
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	/**
	 * 自訂員工認證邏輯
	 */
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * 認證失敗處理類
	 */
	@Autowired
	private AuthenticationEntryPointImpl unauthorizedHandler;

	/**
	 * 退出處理類
	 */
	@Autowired
	private LogoutSuccessHandlerImpl logoutSuccessHandler;

	/**
	 * token認證過濾器
	 */
	@Autowired
	private JwtAuthenticationTokenFilter authenticationTokenFilter;

	/**
	 * 跨域過濾器
	 */
	@Autowired
	private CorsFilter corsFilter;

	/**
	 * 解決 無法直接注入 AuthenticationManager
	 *
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * anyRequest | 匹配所有請求路徑 access | SpringEl表達式結果為true時可以訪問 anonymous | 匿名可以訪問
	 * denyAll | 員工不能訪問 fullyAuthenticated | 員工完全認證可以訪問（非remember-me下自動登入）
	 * hasAnyAuthority | 如果有參數，參數表示權限，則其中任何一個權限可以訪問 hasAnyRole |
	 * 如果有參數，參數表示角色，則其中任何一個角色可以訪問 hasAuthority | 如果有參數，參數表示權限，則其權限可以訪問 hasIpAddress
	 * | 如果有參數，參數表示IP位址，如果員工IP和參數匹配，則可以訪問 hasRole | 如果有參數，參數表示角色，則其角色可以訪問 permitAll
	 * | 員工可以任意訪問 rememberMe | 允許通過remember-me登入的員工訪問 authenticated | 員工登入後可訪問
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				// CSRF禁用，因為不使用session
				.csrf().disable()
				// 認證失敗處理類
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				// 基於token，所以不需要session
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// 過濾請求
				.authorizeRequests()
				// 對於登入login 驗證碼captchaImage 允許匿名訪問
				.antMatchers("/login", "/captchaImage").anonymous()
				.antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
				.antMatchers("/profile/**").anonymous().antMatchers("/common/download**").anonymous()
				.antMatchers("/common/download/resource**").anonymous().antMatchers("/swagger-ui.html").anonymous()
				.antMatchers("/swagger-resources/**").anonymous().antMatchers("/webjars/**").anonymous()
				.antMatchers("/*/api-docs").anonymous().antMatchers("/druid/**").anonymous()
				// 除上面外的所有請求全部需要鑒權認證
				.anyRequest().authenticated().and().headers().frameOptions().disable();
		httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
		// 添加JWT filter
		httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
		// 添加CORS filter
		httpSecurity.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
		httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);
	}

	/**
	 * 強散列哈希加密實現
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 身份認證介面
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
}
