package com.eipulse.web.core.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eipulse.common.config.RuoYiConfig;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2的介面配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	/**
	 * 系統基礎配置
	 */
	@Autowired
	private RuoYiConfig ruoyiConfig;

	/**
	 * 是否開啟swagger
	 */
	@Value("${swagger.enabled}")
	private boolean enabled;

	/**
	 * 設置請求的統一前綴
	 */
	@Value("${swagger.pathMapping}")
	private String pathMapping;

	/**
	 * 創建API
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				// 是否啟用Swagger
				.enable(enabled)
				// 用來創建該API的基本資訊，展示在文件的頁面中（自訂展示的資訊）
				.apiInfo(apiInfo())
				// 設置哪些介面暴露給Swagger展示
				.select()
				// 掃描所有有註解的api，用這種方式更靈活
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				// 掃描指定包中的swagger註解
				// .apis(RequestHandlerSelectors.basePackage("com.eipulse.project.tool.swagger"))
				// 掃描所有 .apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build()
				/* 設置安全模式，swagger可以設置訪問token */
				.securitySchemes(securitySchemes()).securityContexts(securityContexts()).pathMapping(pathMapping);
	}

	/**
	 * 安全模式，這裡指定token通過Authorization頭請求頭傳遞
	 */
	private List<ApiKey> securitySchemes() {
		List<ApiKey> apiKeyList = new ArrayList<ApiKey>();
		apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
		return apiKeyList;
	}

	/**
	 * 安全上下文
	 */
	private List<SecurityContext> securityContexts() {
		List<SecurityContext> securityContexts = new ArrayList<>();
		securityContexts.add(SecurityContext.builder().securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("^(?!auth).*$")).build());
		return securityContexts;
	}

	/**
	 * 預設的安全上引用
	 */
	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		List<SecurityReference> securityReferences = new ArrayList<>();
		securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
		return securityReferences;
	}

	/**
	 * 添加摘要資訊
	 */
	private ApiInfo apiInfo() {
		// 用ApiInfoBuilder進行訂製
		return new ApiInfoBuilder()
				// 設置標題
				.title("標題：EIPulse人事入口網站_介面文件")
				// 描述
				.description("描述：用於管理集團旗下公司的人員資訊,具體包括XXX,XXX模組...")
				// 作者資訊
				.contact(new Contact(ruoyiConfig.getName(), null, null))
				// 版本
				.version("版本號:" + ruoyiConfig.getVersion()).build();
	}
}
