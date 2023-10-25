package com.eipulse.quartz.task;

import org.springframework.stereotype.Component;

import com.eipulse.common.utils.StringUtils;

/**
 * 定時任務調度測試
 * 
 * @author eipulse
 */
@Component("eipulseTask")
public class EipulseTask {
	public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
		System.out.println(StringUtils.format("執行多參方法： 字串類型{}，布林類型{}，長整數{}，浮點數{}，整數{}", s, b, l, d, i));
	}

	public void ryParams(String params) {
		System.out.println("執行有參方法：" + params);
	}

	public void ryNoParams() {
		System.out.println("執行無參方法");
	}
}
