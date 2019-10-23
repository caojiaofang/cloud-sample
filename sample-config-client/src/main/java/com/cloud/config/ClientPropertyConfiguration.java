/*
 * 文件名：ClientPropertyConfiguration.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ClientPropertyConfiguration.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年10月22日
 * 修改内容：新增
 */
package com.cloud.config;

import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Title:  ClientPropertyConfiguration.java
 * @Package: com.cloud.client.ClientPropertyConfiguration.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年10月22日 下午3:13:50
 */
public class ClientPropertyConfiguration implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered{

	private static final Logger log = LoggerFactory.getLogger(ClientPropertyConfiguration.class);
	private static final String KEY_PREFIX_JSON = "json-";
	private static final String KEY_PREFIX_MARK = "mark-";
	private ObjectMapper objectMapper = new ObjectMapper();

	public int getOrder() {
		return Integer.MAX_VALUE;
	}

	public void initialize(ConfigurableApplicationContext applicationContext) {
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		MutablePropertySources propertySources = environment.getPropertySources();

		CompositePropertySource source = (CompositePropertySource) propertySources.get("bootstrapProperties");
		log.info("获取到的source:{}",source);
		if (source == null) {
			log.error("从configserver获取配置失败。");
			return;
		}
		String[] n = source.getPropertyNames();
		Map<String, Object> map = new TreeMap();
		log.info("获取到的n:{}",n);
		for (String k : n) {
			if (k.startsWith("json-")) {
				Object v = source.getProperty(k);
				log.info("获取到的v:{}",v);
				k = k.replaceFirst("json-", "");
				log.info("获取到的k:{}",k);
				handleJsonValue(map, k, v.toString());
			} else if (k.startsWith("mark-")) {
				Object v = source.getProperty(k);

				k = k.replaceFirst("mark-", "");
				handleMarkValue(map, k, v.toString());
			}
		}
		StringBuilder builder = new StringBuilder("配置解析>>");
		for (Object entry : map.entrySet()) {
			builder.append("\n ");
			builder.append((String) ((Map.Entry) entry).getKey());
			builder.append("=");
			builder.append(((Map.Entry) entry).getValue());
		}
		log.info(builder.toString());
		MapPropertySource mapSource = new MapPropertySource(ClientPropertyConfiguration.class.getSimpleName(), map);
		source.addPropertySource(mapSource);
	}

	private void handleJsonValue(Map<String, Object> map, String key, String value) {
		Map<String, String> valueMap = null;
		try {
			valueMap = (Map) objectMapper.readValue(value, new TypeReference<Map<String, String>>() {
			});
		} catch (Exception e) {
			log.error("", e);
		}
		if ((valueMap != null) && (valueMap.size() > 0)) {
			for (Map.Entry<String, String> entry : valueMap.entrySet()) {
				map.put(key + "." + (String) entry.getKey(), entry.getValue());
			}
		}
	}

	private void handleMarkValue(Map<String, Object> map, String key, String value) {
		if ((value == null) || (value.isEmpty())) {
			return;
		}
		String[] array = value.split("\\|");
		for (String s : array) {
			int i = s.indexOf('=');
			if ((i > 0) && (i < s.length() - 1)) {
				map.put(key + "." + s.substring(0, i).trim(), s.substring(i + 1).trim());
			}
		}
	}
}
