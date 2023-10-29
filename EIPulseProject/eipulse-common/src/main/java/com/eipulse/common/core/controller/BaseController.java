package com.eipulse.common.core.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.eipulse.common.constant.HttpStatus;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.core.page.TableDataInfo;
import com.eipulse.common.utils.DateUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.code.BusinessBizCode;

/**
 * web層通用數據處理
 */
public class BaseController {
	protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

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
	protected TableDataInfo getDataTable(Page<?> page) {
		TableDataInfo rspData = new TableDataInfo();
		rspData.setCode(HttpStatus.SUCCESS);
		rspData.setMsg("查詢成功");
		rspData.setRows(page.getContent());
		rspData.setTotal(page.getTotalElements());
		return rspData;
	}

	/**
	 * 響應返回結果
	 *
	 * @param rows 影響行數
	 * @return 操作結果
	 */
	protected AjaxResult toAjax(int rows) {
		return rows == BusinessBizCode.OPTION_SUCCESS.getCode() ? AjaxResult.success() : AjaxResult.error();
	}

	/**
	 * 頁面跳轉
	 */
	public String redirect(String url) {
		return StringUtils.format("redirect:{}", url);
	}
}
