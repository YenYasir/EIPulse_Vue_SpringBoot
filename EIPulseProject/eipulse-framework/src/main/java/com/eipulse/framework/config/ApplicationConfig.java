package com.eipulse.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 程序註解配置
 */
@Configuration
// 表示通過aop框架暴露該代理物件,AopContext能夠訪問
@EnableAspectJAutoProxy(exposeProxy = true)
public class ApplicationConfig {

}
