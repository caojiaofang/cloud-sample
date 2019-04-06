package com.cloud.utils.client;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
/**
 * @Description: 
 * @author  lizhi 
 * @date  2019年4月6日 下午2:10:32
 */
@Component
public class RestClientService {

	@Autowired
	protected RestTemplate restClient;

	@Autowired
	protected RestTemplate restTemplate;

	/**
	 * 负载均衡调用服务
	 * 
	 * @param String
	 * @param String
	 * @param HttpEntity
	 * @return String
	 */
	public String postReqToService(String serviceId, String path, HttpEntity<?> entity) {
		String serviceUrl = joinUrl(serviceId, path);
		return restClient.postForObject(serviceUrl, entity, String.class);
	}

	/**
	 * 负载均衡调用服务
	 * 
	 * @param String
	 * @param String
	 * @param String
	 * @return String
	 */
	public String postStringToService(String serviceId, String path, String body) {
		String serviceUrl = joinUrl(serviceId, path);
		return restClient.postForObject(serviceUrl, body, String.class);
	}

	/**
	 * 负载均衡调用服务
	 * 
	 * @param String
	 * @param String
	 * @param MultiValueMap
	 * @return String
	 */
	public String postReqToService(String serviceId, String path, MultiValueMap<String, Object> params) {
		String serviceUrl = joinUrl(serviceId, path);
		return restClient.postForObject(serviceUrl, params, String.class);
	}

	/**
	 * 负载均衡调用服务
	 * 
	 * @param String
	 * @param String
	 * @return String
	 */
	public String getStringToService(String serviceId, String path) {
		String serviceUrl = joinUrl(serviceId, path);
		return restClient.getForObject(serviceUrl, String.class);
	}

	/**
	 * 负载均衡调用服务
	 * 
	 * @param String
	 * @return String
	 */
	public String getStringToService(String serviceUrl) {
		return restClient.getForObject(serviceUrl, String.class);
	}

	/**
	 * 根据URL发送请求
	 * 
	 * @param String
	 * @param HttpEntity
	 * @return String
	 */
	public String postReq(String url, HttpEntity<?> entity) {
		return restTemplate.postForObject(url, entity, String.class);
	}

	/**
	 * 根据URL发送请求
	 * 
	 * @param String
	 * @param String
	 * @return String
	 */
	public String postString(String url, String body) {
		return restTemplate.postForObject(url, body, String.class);
	}

	/**
	 * 根据URL发送请求
	 * 
	 * @param String
	 * @param MultiValueMap
	 * @return String
	 */
	public String postReq(String url, MultiValueMap<String, Object> params) {
		return restTemplate.postForObject(url, params, String.class);
	}

	/**
	 * 发送请求
	 * 
	 * @param String
	 * @param int
	 * @param String
	 * @param HttpEntity
	 * @return String
	 */
	public String postReq(String host, int port, String path, HttpEntity<?> entity) {
		String url = joinUrl(host, port, path);
		return postReq(url, entity);
	}

	/**
	 * 发送请求
	 * 
	 * @param String
	 * @param int
	 * @param String
	 * @param String
	 * @return String
	 */
	public String postString(String host, int port, String path, String body) {
		String url = joinUrl(host, port, path);
		return postString(url, body);
	}

	/**
	 * 发送文件
	 * 
	 * @param String
	 * @param Map<String,File>
	 * @return String
	 */
	public String postFile(String url, Map<String, File> map) {
		Objects.requireNonNull(map);
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		for (Entry<String, File> entry : map.entrySet()) {
			params.add(entry.getKey(), new FileSystemResource(entry.getValue()));
		}
		return postReq(url, params);
	}

	/**
	 * 发送文件
	 * 
	 * @param String
	 * @param int
	 * @param String
	 * @param Map<String,File>
	 * @return String
	 */
	public String postFile(String host, int port, String path, Map<String, File> map) {
		String url = joinUrl(host, port, path);
		return postFile(url, map);
	}

	private String joinUrl(String serviceId, String path) {
		if (path == null) {
			path = "";
		}
		String str = serviceId + "/" + path;
		str = str.replace("//", "/");
		return "http://" + str;
	}

	private String joinUrl(String host, int port, String path) {
		if (path == null) {
			path = "";
		}
		String str = host + ":" + port + "/" + path;
		str = str.replace("//", "/");
		return "http://" + str;
	}

}
