package com.eipulse.quartz.task;

import org.springframework.stereotype.Component;

import com.eipulse.common.utils.StringUtils;

/**
 * 定時任務調度測試
 */
@Component("eipulseTask")
public class EipulseTask {
	public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
		System.out.println(StringUtils.format("執行多參方法： 字串類型{}，布爾類型{}，長整型{}，浮點型{}，整形{}", s, b, l, d, i));
	}

	public void ryParams(String params) {
		System.out.println("執行有參方法：" + params);
	}

	public void ryNoParams() {
		System.out.println("執行無參方法");
	}
}
