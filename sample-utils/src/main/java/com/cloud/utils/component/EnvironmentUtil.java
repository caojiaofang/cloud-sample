/*
 * 文件名：EnvironmentUtil.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： EnvironmentUtil.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年4月6日
 * 修改内容：新增
 */
package com.cloud.utils.component;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * @Title:  EnvironmentUtil.java
 * @Package: com.cloud.utils.component.EnvironmentUtil.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年4月6日 下午9:44:16
 */
@Component
public class EnvironmentUtil implements EnvironmentAware{

	@Getter
	private static Environment environment;


	@Override
	public void setEnvironment(Environment arg0) {
		environment = arg0;
	}
	
	public static String getProperty(String key) {
		return environment.getProperty(key);
	}
	
}
