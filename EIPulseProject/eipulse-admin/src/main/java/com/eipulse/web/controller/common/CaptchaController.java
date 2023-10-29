package com.eipulse.web.controller.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.common.constant.Constants;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.core.redis.RedisCache;
import com.eipulse.common.utils.sign.Base64;
import com.eipulse.common.utils.uuid.IdUtils;
import com.google.code.kaptcha.Producer;

/**
 * 驗證碼操作處理
 */
@RestController
public class CaptchaController {
	@Resource(name = "captchaProducer")
	private Producer captchaProducer;

	@Resource(name = "captchaProducerMath")
	private Producer captchaProducerMath;

	@Autowired
	private RedisCache redisCache;

	// 驗證碼類型
	@Value("${eipulse.captchaType}")
	private String captchaType;

	/**
	 * 生成驗證碼
	 */
	@GetMapping("/captchaImage")
	public AjaxResult getCode() {
		// 保存驗證碼資訊
		String uuid = IdUtils.simpleUUID();
		String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

		String capStr, code = null;
		BufferedImage image = null;

		// 生成驗證碼
		if ("math".equals(captchaType)) {
			String capText = captchaProducerMath.createText();
			capStr = capText.substring(0, capText.lastIndexOf("@"));
			code = capText.substring(capText.lastIndexOf("@") + 1);
			image = captchaProducerMath.createImage(capStr);
		} else if ("char".equals(captchaType)) {
			capStr = code = captchaProducer.createText();
			image = captchaProducer.createImage(capStr);
		}

		redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
		// 轉換流資訊寫出
		FastByteArrayOutputStream os = new FastByteArrayOutputStream();
		try {
			ImageIO.write(image, "jpg", os);
		} catch (IOException e) {
			return AjaxResult.error(e.getMessage());
		}

		AjaxResult ajax = AjaxResult.success();
		ajax.put("uuid", uuid);
		ajax.put("img", Base64.encode(os.toByteArray()));
		return ajax;
	}
}
