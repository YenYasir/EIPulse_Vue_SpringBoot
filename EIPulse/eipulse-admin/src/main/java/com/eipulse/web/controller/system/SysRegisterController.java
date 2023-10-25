package com.eipulse.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.common.core.controller.BaseController;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.core.domain.model.RegisterBody;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.framework.web.service.SysRegisterService;
import com.eipulse.system.service.SysConfigService;

/**
 * 註冊驗證
 * 
 * @author eipulse
 */
@RestController
public class SysRegisterController extends BaseController {
	@Autowired
	private SysRegisterService registerService;

	@Autowired
	private SysConfigService configService;

	@PostMapping("/register")
	public AjaxResult register(@RequestBody RegisterBody user) {
		if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
			return error("當前系統沒有開啟註冊功能！");
		}
		String msg = registerService.register(user);
		return StringUtils.isEmpty(msg) ? success() : error(msg);
	}
}
