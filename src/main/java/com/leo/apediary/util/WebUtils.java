package com.leo.apediary.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

public class WebUtils {
	/**
	 * 初始化 bean 属性并返回 bean 实例
	 * @param request
	 * @param clazz
	 * @return bean 实例
	 */
	public static <T> T fillBean(HttpServletRequest request, Class<T> clazz) {
		try {
			T bean = clazz.newInstance();
			BeanUtils.populate(bean, request.getParameterMap());
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 将源对象属性值拷贝到目标属性
	 * @param dest
	 * @param src
	 */
	public static void copyProps(Object dest, Object src) {
		try {
			BeanUtils.copyProperties(dest, src);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private WebUtils() {}
}
