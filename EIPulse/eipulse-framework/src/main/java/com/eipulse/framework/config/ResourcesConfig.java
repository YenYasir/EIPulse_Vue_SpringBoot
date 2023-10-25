package com.eipulse.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.eipulse.common.config.EipulseConfig;
import com.eipulse.common.constant.Constants;
import com.eipulse.framework.interceptor.RepeatSubmitInterceptor;

/**
 * 通用配置
 * 
 * @author eipulse
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {
	@Autowired
	private RepeatSubmitInterceptor repeatSubmitInterceptor;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/** 本地文件上傳路徑 */
		registry.addResourceHandler(Constants.RESOURCE_PREFIX + "/**")
				.addResourceLocations("file:" + EipulseConfig.getProfile() + "/");

		/** swagger配置 */
		registry.addResourceHandler("/swagger-ui/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
	}

	/**
	 * 自定義攔截規則
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/**");
	}

	/**
	 * 跨域配置
	 */
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		// 設置訪問源地址
		config.addAllowedOriginPattern("*");
		// 設置訪問源請求頭
		config.addAllowedHeader("*");
		// 設置訪問源請求方法
		config.addAllowedMethod("*");
		// 有效期 1800秒
		config.setMaxAge(1800L);
		// 添加映射路徑，攔截一切請求
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		// 返回新的CorsFilter
		return new CorsFilter(source);
	}
}