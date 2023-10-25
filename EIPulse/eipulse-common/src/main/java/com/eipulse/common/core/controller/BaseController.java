package com.eipulse.common.core.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.eipulse.common.constant.HttpStatus;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.core.domain.model.LoginUser;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.page.TableDataInfo;
import com.eipulse.common.utils.DateUtils;
import com.eipulse.common.utils.SecurityUtils;
import com.eipulse.common.utils.StringUtils;

/**
 * web層通用數據處理
 * 
 * @author eipulse
 */
public class BaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 將前台傳遞過來的日期格式的字串，自動轉化為Date類型
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Date 類型轉換
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
		});
	}

	/**
	 * 響應請求分頁數據
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected TableDataInfo getDataTable(Page page) {
		TableDataInfo rspData = new TableDataInfo();
		rspData.setCode(HttpStatus.SUCCESS);
		rspData.setMsg("查詢成功");
		rspData.setRows(page.getItems());
		rspData.setTotal(page.getTotalCount());
		return rspData;
	}

	/**
	 * 響應請求分頁數據
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	/**
	 * protected TableDataInfo getDataTable(List<?> list) { TableDataInfo rspData =
	 * new TableDataInfo(); rspData.setCode(HttpStatus.SUCCESS);
	 * rspData.setMsg("查詢成功"); rspData.setRows(list); rspData.setTotal(new
	 * PageInfo(list).getTotal()); return rspData; }
	 */

	/**
	 * 返回成功
	 */
	public AjaxResult success() {
		return AjaxResult.success();
	}

	/**
	 * 返回失敗消息
	 */
	public AjaxResult error() {
		return AjaxResult.error();
	}

	/**
	 * 返回成功消息
	 */
	public AjaxResult success(String message) {
		return AjaxResult.success(message);
	}

	/**
	 * 返回失敗消息
	 */
	public AjaxResult error(String message) {
		return AjaxResult.error(message);
	}

	/**
	 * 響應返回結果
	 * 
	 * @param rows 影響行數
	 * @return 操作結果
	 */
	protected AjaxResult toAjax(int rows) {
		return rows > 0 ? AjaxResult.success() : AjaxResult.error();
	}

	/**
	 * 響應返回結果
	 * 
	 * @param result 結果
	 * @return 操作結果
	 */
	protected AjaxResult toAjax(boolean result) {
		return result ? success() : error();
	}

	/**
	 * 頁面跳轉
	 */
	public String redirect(String url) {
		return StringUtils.format("redirect:{}", url);
	}

	/**
	 * 獲取員工緩存信息
	 */
	public LoginUser getLoginUser() {
		return SecurityUtils.getLoginUser();
	}

	/**
	 * 獲取登入員工id
	 */
	public Long getUserId() {
		return getLoginUser().getUserId();
	}

	/**
	 * 獲取登入部門id
	 */
	public Long getDeptId() {
		return getLoginUser().getDeptId();
	}

	/**
	 * 獲取登入員工名
	 */
	public String getUsername() {
		return getLoginUser().getUsername();
	}
}
