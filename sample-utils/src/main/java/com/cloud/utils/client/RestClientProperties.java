package com.cloud.utils.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * @Description: 
 * @author  lizhi 
 * @date  2019年4月6日 下午2:10:32
 */
@ConfigurationProperties(prefix = "client")
public class RestClientProperties {

	private Integer maxConnTotal = 100;
	private Integer maxConnPerRoute = 100;
	private Integer connectTimeout = -1;
	private Integer readTimeout = 5000;

	public Integer getMaxConnTotal() {
		return maxConnTotal;
	}

	public void setMaxConnTotal(Integer maxConnTotal) {
		this.maxConnTotal = maxConnTotal;
	}

	public Integer getMaxConnPerRoute() {
		return maxConnPerRoute;
	}

	public void setMaxConnPerRoute(Integer maxConnPerRoute) {
		this.maxConnPerRoute = maxConnPerRoute;
	}

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Integer getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(Integer readTimeout) {
		this.readTimeout = readTimeout;
	}
}
