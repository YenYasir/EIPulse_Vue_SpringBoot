package com.eipulse.web.controller.monitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.common.annotation.Log;
import com.eipulse.common.constant.Constants;
import com.eipulse.common.constant.HttpStatus;
import com.eipulse.common.core.controller.BaseController;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.core.domain.model.LoginUser;
import com.eipulse.common.core.page.TableDataInfo;
import com.eipulse.common.core.redis.RedisCache;
import com.eipulse.common.enums.BusinessType;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.system.dto.SysUserOnline;
import com.eipulse.system.service.ISysUserOnlineService;

/**
 * 在線員工監控
 */
@RestController
@RequestMapping("/monitor/online")
public class SysUserOnlineController extends BaseController {
	@Autowired
	private ISysUserOnlineService userOnlineService;

	@Autowired
	private RedisCache redisCache;

	@PreAuthorize("@ss.hasPermi('monitor:online:list')")
	@GetMapping("/list")
	public TableDataInfo list(String ipaddr, String userName) {
		Collection<String> keys = redisCache.keys(Constants.LOGIN_TOKEN_KEY + "*");
		List<SysUserOnline> userOnlineList = new ArrayList<>();
		for (String key : keys) {
			LoginUser user = redisCache.getCacheObject(key);
			if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
				if (StringUtils.equals(ipaddr, user.getIpaddr()) && StringUtils.equals(userName, user.getUsername())) {
					userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
				}
			} else if (StringUtils.isNotEmpty(ipaddr)) {
				if (StringUtils.equals(ipaddr, user.getIpaddr())) {
					userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
				}
			} else if (StringUtils.isNotEmpty(userName) && StringUtils.isNotNull(user.getUser())) {
				if (StringUtils.equals(userName, user.getUsername())) {
					userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
				}
			} else {
				userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
			}
		}
		Collections.reverse(userOnlineList);
		userOnlineList.removeAll(Collections.singleton(null));
		TableDataInfo rspData = new TableDataInfo();
		rspData.setCode(HttpStatus.SUCCESS);
		rspData.setMsg("查詢成功");
		rspData.setRows(userOnlineList);
		rspData.setTotal(userOnlineList.size());
		return rspData;
	}

	/**
	 * 強退員工
	 */
	@PreAuthorize("@ss.hasPermi('monitor:online:forceLogout')")
	@Log(title = "在線員工", businessType = BusinessType.FORCE)
	@DeleteMapping("/{tokenId}")
	public AjaxResult forceLogout(@PathVariable String tokenId) {
		redisCache.deleteObject(Constants.LOGIN_TOKEN_KEY + tokenId);
		return AjaxResult.success();
	}
}
