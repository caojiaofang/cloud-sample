package com.cloud.client;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cloud.utils.client.RestClientService;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class SettingService implements InitializingBean {

	
	private static final Logger log = LoggerFactory.getLogger(SettingService.class);

	private String serviceId;
	private String url;
	
	@Autowired
	private ConfigClientProperties configClientProperties;
	
	@Autowired
	private RestClientService restClientService;
	
	@Autowired
	private ObjectMapper objectMapper;

	public String getProperty(String key) {
		return getProperty(key, configClientProperties.getName(), configClientProperties.getProfile());
	}

	public String getProperty(String lable, String application, String profile) {
		if (StringUtils.isEmpty(lable)) {
			return null;
		}
		String req = url + "lable=" + lable;
		if (!StringUtils.isEmpty(application)) {
			req = req + "&application=" + application;
		}
		if (!StringUtils.isEmpty(profile)) {
			req = req + "&profile=" + profile;
		}
		Map<String, Object> result = doGet(req);
		if (result != null) {
			return (String) result.get(lable);
		}
		return null;
	}

	public Map<String, Object> getProperties(String application, String profile) {
		String req = url;
		if (!StringUtils.isEmpty(application)) {
			req = req + "&application=" + application;
		}
		if (!StringUtils.isEmpty(profile)) {
			req = req + "&profile=" + profile;
		}
		return doGet(req);
	}

	private Map<String, Object> doGet(String req) {
		String jsonStr = restClientService.getStringToService(req);
		if (StringUtils.isEmpty(jsonStr)) {
			return null;
		}
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> result = objectMapper.readValue(jsonStr, Map.class);
			return result;
		} catch (IOException e) {
			log.error("", e);
		}
		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		serviceId = configClientProperties.getDiscovery().getServiceId();
		url = String.format("http://%s/sample/findcfg?", serviceId);
		log.info("获取配置的url:{}",url);
	}

}
