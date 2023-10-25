package com.eipulse.framework.manager.factory;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eipulse.common.constant.Constants;
import com.eipulse.common.utils.LogUtils;
import com.eipulse.common.utils.ServletUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.ip.AddressUtils;
import com.eipulse.common.utils.ip.IpUtils;
import com.eipulse.common.utils.spring.SpringUtils;
import com.eipulse.system.domain.SysLogininfor;
import com.eipulse.system.domain.SysOperLog;
import com.eipulse.system.service.SysLogininforService;
import com.eipulse.system.service.SysOperLogService;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * AsyncFactory（產生任務用）
 * 
 * @author eipulse
 */
public class AsyncFactory {
	private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

	/**
	 * 記錄登入資訊
	 * 
	 * @param username 員工名
	 * @param status   狀態
	 * @param message  消息
	 * @param args     列表
	 * @return 任務task
	 */
	public static TimerTask recordLogininfor(final String username, final String status, final String message,
			final Object... args) {
		final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
		final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
		return new TimerTask() {
			@Override
			public void run() {
				String address = AddressUtils.getRealAddressByIP(ip);
				StringBuilder s = new StringBuilder();
				s.append(LogUtils.getBlock(ip));
				s.append(address);
				s.append(LogUtils.getBlock(username));
				s.append(LogUtils.getBlock(status));
				s.append(LogUtils.getBlock(message));
				// 打印資訊到日誌
				sys_user_logger.info(s.toString(), args);
				// 獲取客戶端操作系統
				String os = userAgent.getOperatingSystem().getName();
				// 獲取客戶端瀏覽器
				String browser = userAgent.getBrowser().getName();
				// 封裝物件
				SysLogininfor logininfor = new SysLogininfor();
				logininfor.setUserName(username);
				logininfor.setIpaddr(ip);
				logininfor.setLoginLocation(address);
				logininfor.setBrowser(browser);
				logininfor.setOs(os);
				logininfor.setMsg(message);
				// 日誌狀態
				if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
					logininfor.setStatus(Constants.SUCCESS);
				} else if (Constants.LOGIN_FAIL.equals(status)) {
					logininfor.setStatus(Constants.FAIL);
				}
				// 插入數據
				SpringUtils.getBean(SysLogininforService.class).saveData(logininfor);
			}
		};
	}

	/**
	 * 操作日誌記錄
	 * 
	 * @param operLog 操作日誌資訊
	 * @return 任務task
	 */
	public static TimerTask recordOper(final SysOperLog operLog) {
		return new TimerTask() {
			@Override
			public void run() {
				// 遠程查詢操作地點
				operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
				SpringUtils.getBean(SysOperLogService.class).savaData(operLog);
			}
		};
	}
}
