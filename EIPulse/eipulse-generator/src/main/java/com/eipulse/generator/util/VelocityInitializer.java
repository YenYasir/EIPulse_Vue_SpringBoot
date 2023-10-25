package com.eipulse.generator.util;

import java.util.Properties;

import org.apache.velocity.app.Velocity;

import com.eipulse.common.constant.Constants;

/**
 * VelocityEngine工廠
 * 
 * @author eipulse
 */
public class VelocityInitializer {
	/**
	 * 初始化vm方法
	 */
	public static void initVelocity() {
		Properties p = new Properties();
		try {
			// 加載classpath目錄下的vm文件
			p.setProperty("file.resource.loader.class",
					"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			// 定義編碼
			p.setProperty(Velocity.INPUT_ENCODING, Constants.UTF8);
			p.setProperty(Velocity.OUTPUT_ENCODING, Constants.UTF8);
			// 初始化Velocity引擎，指定配置Properties
			Velocity.init(p);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
