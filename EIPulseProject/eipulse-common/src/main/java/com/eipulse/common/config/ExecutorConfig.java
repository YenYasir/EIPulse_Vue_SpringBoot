package com.eipulse.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfig {
	
	@Bean(value = "microSvcExecutor", destroyMethod="shutdown")
	public ExecutorService microSvcExecutor() {
		return Executors.newCachedThreadPool();
	}
	
}