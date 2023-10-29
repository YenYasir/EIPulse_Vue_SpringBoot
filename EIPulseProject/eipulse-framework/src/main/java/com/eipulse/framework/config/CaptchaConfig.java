package com.eipulse.framework.config;

import static com.google.code.kaptcha.Constants.KAPTCHA_BORDER;
import static com.google.code.kaptcha.Constants.KAPTCHA_BORDER_COLOR;
import static com.google.code.kaptcha.Constants.KAPTCHA_IMAGE_HEIGHT;
import static com.google.code.kaptcha.Constants.KAPTCHA_IMAGE_WIDTH;
import static com.google.code.kaptcha.Constants.KAPTCHA_NOISE_COLOR;
import static com.google.code.kaptcha.Constants.KAPTCHA_NOISE_IMPL;
import static com.google.code.kaptcha.Constants.KAPTCHA_OBSCURIFICATOR_IMPL;
import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_CONFIG_KEY;
import static com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH;
import static com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE;
import static com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR;
import static com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES;
import static com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE;
import static com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_IMPL;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

/**
 * 驗證碼配置
 */
@Configuration
public class CaptchaConfig {
	@Bean(name = "captchaProducer")
	public DefaultKaptcha getKaptchaBean() {
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		Properties properties = new Properties();
		// 是否有邊框 預設為true 我們可以自己設置yes，no
		properties.setProperty(KAPTCHA_BORDER, "yes");
		// 驗證碼文本字元顏色 預設為Color.BLACK
		properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
		// 驗證碼圖片寬度 預設為200
		properties.setProperty(KAPTCHA_IMAGE_WIDTH, "160");
		// 驗證碼圖片高度 預設為50
		properties.setProperty(KAPTCHA_IMAGE_HEIGHT, "60");
		// 驗證碼文本字元大小 預設為40
		properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_SIZE, "38");
		// KAPTCHA_SESSION_KEY
		properties.setProperty(KAPTCHA_SESSION_CONFIG_KEY, "kaptchaCode");
		// 驗證碼文本字元長度 預設為5
		properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
		// 驗證碼文本字體樣式 預設為new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
		properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Arial,Courier");
		// 圖片樣式 水紋com.google.code.kaptcha.impl.WaterRipple
		// 魚眼com.google.code.kaptcha.impl.FishEyeGimpy
		// 陰影com.google.code.kaptcha.impl.ShadowGimpy
		properties.setProperty(KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.ShadowGimpy");
		Config config = new Config(properties);
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}

	@Bean(name = "captchaProducerMath")
	public DefaultKaptcha getKaptchaBeanMath() {
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		Properties properties = new Properties();
		// 是否有邊框 預設為true 我們可以自己設置yes，no
		properties.setProperty(KAPTCHA_BORDER, "yes");
		// 邊框顏色 預設為Color.BLACK
		properties.setProperty(KAPTCHA_BORDER_COLOR, "105,179,90");
		// 驗證碼文本字元顏色 預設為Color.BLACK
		properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_COLOR, "blue");
		// 驗證碼圖片寬度 預設為200
		properties.setProperty(KAPTCHA_IMAGE_WIDTH, "160");
		// 驗證碼圖片高度 預設為50
		properties.setProperty(KAPTCHA_IMAGE_HEIGHT, "60");
		// 驗證碼文本字元大小 預設為40
		properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_SIZE, "35");
		// KAPTCHA_SESSION_KEY
		properties.setProperty(KAPTCHA_SESSION_CONFIG_KEY, "kaptchaCodeMath");
		// 驗證碼文本生成器
		properties.setProperty(KAPTCHA_TEXTPRODUCER_IMPL, "com.eipulse.framework.config.KaptchaTextCreator");
		// 驗證碼文本字元間距 預設為2
		properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "3");
		// 驗證碼文本字元長度 預設為5
		properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "6");
		// 驗證碼文本字體樣式 預設為new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
		properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Arial,Courier");
		// 驗證碼噪點顏色 預設為Color.BLACK
		properties.setProperty(KAPTCHA_NOISE_COLOR, "white");
		// 干擾實現類
		properties.setProperty(KAPTCHA_NOISE_IMPL, "com.google.code.kaptcha.impl.NoNoise");
		// 圖片樣式 水紋com.google.code.kaptcha.impl.WaterRipple
		// 魚眼com.google.code.kaptcha.impl.FishEyeGimpy
		// 陰影com.google.code.kaptcha.impl.ShadowGimpy
		properties.setProperty(KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.ShadowGimpy");
		Config config = new Config(properties);
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}
}
