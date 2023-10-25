package com.eipulse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 啟動程式
 * 
 * @author eipulse
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class }, scanBasePackages = { "com.eipulse.*" })
@EnableJpaRepositories(basePackages = { "com.eipulse.**.dao" })
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.eipulse.*" })

public class EipulseApplication {
	public static void main(String[] args) {
		// System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(EipulseApplication.class, args);
		System.out.println(" EIPulse 系統啟動成功     \n" + " ______  _____  _____        __        \n"
				+ "|  ____|  |_   _||  __ \\        | |  \n"
				+ "| |__         | |  | |__) | _     | |  ___   ___      \n"
				+ "|  __|        | |  |  ___/ | | | || |/ __| / _   \\      \n"
				+ "| |____    _| |_ |  |     | |_| || |\\__ \\|  __/       \n"
				+ "|______||_____||_|     \\__,_||_||___/ \\___|     \n");
	}
}
