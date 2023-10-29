package com.eipulse.quartz.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.spring.SpringUtils;
import com.eipulse.quartz.domain.SysJob;

/**
 * 任務執行工具
 */
public class JobInvokeUtil {
	/**
	 * 執行方法
	 *
	 * @param sysJob 系統任務
	 */
	public static void invokeMethod(SysJob sysJob) throws Exception {
		String invokeTarget = sysJob.getInvokeTarget();
		String beanName = getBeanName(invokeTarget);
		String methodName = getMethodName(invokeTarget);
		List<Object[]> methodParams = getMethodParams(invokeTarget);

		if (!isValidClassName(beanName)) {
			Object bean = SpringUtils.getBean(beanName);
			invokeMethod(bean, methodName, methodParams);
		} else {
			Object bean = Class.forName(beanName).newInstance();
			invokeMethod(bean, methodName, methodParams);
		}
	}

	/**
	 * 調用任務方法
	 *
	 * @param bean         目標物件
	 * @param methodName   方法名稱
	 * @param methodParams 方法參數
	 */
	private static void invokeMethod(Object bean, String methodName, List<Object[]> methodParams)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		if (StringUtils.isNotNull(methodParams) && methodParams.size() > 0) {
			Method method = bean.getClass().getDeclaredMethod(methodName, getMethodParamsType(methodParams));
			method.invoke(bean, getMethodParamsValue(methodParams));
		} else {
			Method method = bean.getClass().getDeclaredMethod(methodName);
			method.invoke(bean);
		}
	}

	/**
	 * 校驗是否為為class包名
	 * 
	 * @param str 名稱
	 * @return true是 false否
	 */
	public static boolean isValidClassName(String invokeTarget) {
		return StringUtils.countMatches(invokeTarget, ".") > 1;
	}

	/**
	 * 獲取bean名稱
	 * 
	 * @param invokeTarget 目標字串
	 * @return bean名稱
	 */
	public static String getBeanName(String invokeTarget) {
		String beanName = StringUtils.substringBefore(invokeTarget, "(");
		return StringUtils.substringBeforeLast(beanName, ".");
	}

	/**
	 * 獲取bean方法
	 * 
	 * @param invokeTarget 目標字串
	 * @return method方法
	 */
	public static String getMethodName(String invokeTarget) {
		String methodName = StringUtils.substringBefore(invokeTarget, "(");
		return StringUtils.substringAfterLast(methodName, ".");
	}

	/**
	 * 獲取method方法參數相關列表
	 * 
	 * @param invokeTarget 目標字串
	 * @return method方法相關參數列表
	 */
	public static List<Object[]> getMethodParams(String invokeTarget) {
		String methodStr = StringUtils.substringBetween(invokeTarget, "(", ")");
		if (StringUtils.isEmpty(methodStr)) {
			return null;
		}
		String[] methodParams = methodStr.split(",");
		List<Object[]> classs = new LinkedList<>();
		for (int i = 0; i < methodParams.length; i++) {
			String str = StringUtils.trimToEmpty(methodParams[i]);
			// String字串類型，包含'
			if (StringUtils.contains(str, "'")) {
				classs.add(new Object[] { StringUtils.replace(str, "'", ""), String.class });
			}
			// boolean布爾類型，等於true或者false
			else if (StringUtils.equals(str, "true") || StringUtils.equalsIgnoreCase(str, "false")) {
				classs.add(new Object[] { Boolean.valueOf(str), Boolean.class });
			}
			// long長整形，包含L
			else if (StringUtils.containsIgnoreCase(str, "L")) {
				classs.add(new Object[] { Long.valueOf(StringUtils.replaceIgnoreCase(str, "L", "")), Long.class });
			}
			// double浮點類型，包含D
			else if (StringUtils.containsIgnoreCase(str, "D")) {
				classs.add(new Object[] { Double.valueOf(StringUtils.replaceIgnoreCase(str, "D", "")), Double.class });
			}
			// 其他類型歸類為整形
			else {
				classs.add(new Object[] { Integer.valueOf(str), Integer.class });
			}
		}
		return classs;
	}

	/**
	 * 獲取參數類型
	 * 
	 * @param methodParams 參數相關列表
	 * @return 參數類型列表
	 */
	public static Class<?>[] getMethodParamsType(List<Object[]> methodParams) {
		Class<?>[] classs = new Class<?>[methodParams.size()];
		int index = 0;
		for (Object[] os : methodParams) {
			classs[index] = (Class<?>) os[1];
			index++;
		}
		return classs;
	}

	/**
	 * 獲取參數值
	 * 
	 * @param methodParams 參數相關列表
	 * @return 參數值列表
	 */
	public static Object[] getMethodParamsValue(List<Object[]> methodParams) {
		Object[] classs = new Object[methodParams.size()];
		int index = 0;
		for (Object[] os : methodParams) {
			classs[index] = os[0];
			index++;
		}
		return classs;
	}
}
