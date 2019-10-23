/*
 * 文件名：ConfigServerController.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ConfigServerController.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年4月6日
 * 修改内容：新增
 */
package com.cloud.config.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Title:  ConfigServerController.java
 * @Package: com.dm.config.ConfigServerController.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年4月6日 下午12:18:57
 */
@RestController
@RequestMapping(value = "/sample")
public class ConfigServerController {

	private static final Logger log = LoggerFactory.getLogger(ConfigServerController.class);

	@Autowired
	private EnvironmentRepository repository;
	@Autowired
	private ObjectMapper objectMapper;

	@RequestMapping({ "/findcfg" })
	public String findcfg(String name, String profiles, String key) {
		System.err.println("我进来了");
		if (StringUtils.isEmpty(name)) {
			name = "application";
		}
		if (StringUtils.isEmpty(profiles)) {
			profiles = "dev";
		}
		Environment environment = repository.findOne(name, profiles, null);
		log.info("获取到的信息是environment:{}",environment);
		Map<Object, Object> result = new HashMap<>();
		if (StringUtils.isEmpty(key)) {
			for (PropertySource ps : environment.getPropertySources()) {
				result.putAll(ps.getSource());
			}
		} else {
			for (PropertySource ps : environment.getPropertySources()) {
				Object o = ps.getSource().get(key);
				if (o != null) {
					result.put(key, o);
					break;
				}
			}
		}
		try {
			return objectMapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			log.error("", e);
		}
		return "";
	}
}
