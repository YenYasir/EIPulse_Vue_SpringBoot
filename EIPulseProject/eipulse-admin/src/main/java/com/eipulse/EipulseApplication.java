package com.eipulse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 啟動程序
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class EipulseApplication {
	public static void main(String[] args) {
		// System.setProperty("spring.devtools.restart.enabled", "false"); a test
		SpringApplication.run(EipulseApplication.class, args);

	}
}
