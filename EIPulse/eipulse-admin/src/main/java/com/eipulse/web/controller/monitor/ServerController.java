package com.eipulse.web.controller.monitor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.framework.web.domain.Server;

/**
 * 伺服器監控
 * 
 * @author eipulse
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController {
	@PreAuthorize("@ss.hasPermi('monitor:server:list')")
	@GetMapping()
	public AjaxResult getInfo() throws Exception {
		Server server = new Server();
		server.copyTo();
		return AjaxResult.success(server);
	}
}
