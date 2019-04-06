package com.cloud.utils.client;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
/**
 * @Description: 
 * @author  lizhi 
 * @date  2019年4月6日 下午2:10:32
 */
@Configuration
@EnableConfigurationProperties(RestClientProperties.class)
public class RestClientConfiguration {

	@Autowired
	private RestTemplateBuilder builder;
	@Autowired
	private RestClientProperties restClientProperties;

	@Bean
	@LoadBalanced
	public RestTemplate restClient() {
		builder = builder.requestFactory(() -> createRequestFactory());
		RestTemplate rest = builder.build();
		return rest;
	}

	@Bean
	public RestTemplate restTemplate() {
		builder = builder.requestFactory(() -> createRequestFactory());
		RestTemplate rest = builder.build();
		return rest;
	}

	private ClientHttpRequestFactory createRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setHttpClient(HttpClientBuilder.create().setMaxConnTotal(restClientProperties.getMaxConnTotal())
				.setMaxConnPerRoute(restClientProperties.getMaxConnPerRoute()).build());
		if (restClientProperties.getConnectTimeout() >= 0) {
			factory.setConnectTimeout(restClientProperties.getConnectTimeout());
		}
		if (restClientProperties.getReadTimeout() >= 0) {
			factory.setReadTimeout(restClientProperties.getReadTimeout());
		}
		return factory;
	}

}
