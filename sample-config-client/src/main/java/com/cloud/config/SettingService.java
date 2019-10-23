package com.cloud.config;

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
	@Autowired
	private ConfigClientProperties configClientProperties;
	private String serviceId;
	private String url;
	@Autowired
	private RestClientService restClientService;
	@Autowired
	private ObjectMapper objectMapper;

	public String getProperty(String key) {
		return this.getProperty(key, this.configClientProperties.getName(), this.configClientProperties.getProfile());
	}

	public String getProperty(String key, String name, String profiles) {
		System.err.println("测试key:"+key);
		if (StringUtils.isEmpty(key)) {
			return null;
		} else {
			String req = this.url + "key=" + key;
			if (!StringUtils.isEmpty(name)) {
				req = req + "&name=" + name;
			}

			if (!StringUtils.isEmpty(profiles)) {
				req = req + "&profiles=" + profiles;
			}

			Map<String, Object> result = this.doGet(req);
			return result != null ? (String) result.get(key) : null;
		}
	}

	public Map<String, Object> getProperties(String name, String profiles) {
		String req = this.url;
		if (!StringUtils.isEmpty(name)) {
			req = req + "&name=" + name;
		}

		if (!StringUtils.isEmpty(profiles)) {
			req = req + "&profiles=" + profiles;
		}

		return this.doGet(req);
	}

	private Map<String, Object> doGet(String req) {
		log.info("获取配置的url:{}",req);
		String jsonStr = null;

		try {
			jsonStr = this.restClientService.getStringToService(req);
			System.err.println("获取到的信息是jsonStr:"+jsonStr);
		} catch (Exception var5) {
			log.error("", var5);
		}

		if (StringUtils.isEmpty(jsonStr)) {
			return null;
		} else {
			try {
				Map<String, Object> result = (Map) this.objectMapper.readValue(jsonStr, Map.class);
				return result;
			} catch (IOException var4) {
				log.error("", var4);
				return null;
			}
		}
	}

	public void afterPropertiesSet() throws Exception {
		this.serviceId = this.configClientProperties.getDiscovery().getServiceId();
		this.url = String.format("http://%s/sample/findcfg?", this.serviceId);
	}
}
