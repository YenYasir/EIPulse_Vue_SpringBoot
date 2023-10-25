package com.eipulse.web.core.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eipulse.common.config.EipulseConfig;

import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger2的接口配置
 * 
 * @author eipulse
 */
@Configuration
public class SwaggerConfig {
	/** 系統基礎配置 */
	@Autowired
	private EipulseConfig eipulseConfig;

	/** 是否開啟swagger */
	@Value("${swagger.enabled}")
	private boolean enabled;

	/** 設置請求的統一前綴 */
	@Value("${swagger.pathMapping}")
	private String pathMapping;

	/**
	 * 創建API
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.OAS_30)
				// 是否啟用Swagger
				.enable(enabled)
				// 用來創建該API的基本資訊，展示在文檔的頁面中（自定義展示的資訊）
				.apiInfo(apiInfo())
				// 設置哪些接口暴露給Swagger展示
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
	 * 安全模式，這裏指定token通過Authorization頭請求頭傳遞
	 */
	private List<SecurityScheme> securitySchemes() {
		List<SecurityScheme> apiKeyList = new ArrayList<SecurityScheme>();
		apiKeyList.add(new ApiKey("Authorization", "Authorization", In.HEADER.toValue()));
		return apiKeyList;
	}

	/**
	 * 安全上下文
	 */
	private List<SecurityContext> securityContexts() {
		List<SecurityContext> securityContexts = new ArrayList<>();
		securityContexts.add(SecurityContext.builder().securityReferences(defaultAuth())
				.operationSelector(o -> o.requestMappingPattern().matches("/.*")).build());
		return securityContexts;
	}

	/**
	 * 默認的安全上引用
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
		// 用ApiInfoBuilder進行定制
		return new ApiInfoBuilder()
				// 設置標題
				.title("標題：EIPulse_接口文檔")
				// 描述
				.description("描述：用於管理集團旗下公司的員工資訊,具體包括XXX,XXX模組..")
				// 作者資訊
				.contact(new Contact(eipulseConfig.getName(), null, null))
				// 版本
				.version("版本號:" + eipulseConfig.getVersion()).build();
	}
}
