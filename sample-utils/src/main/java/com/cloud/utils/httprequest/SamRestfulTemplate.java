/*
 * 文件名：SamRestfulTemplate.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： SamRestfulTemplate.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年6月4日
 * 修改内容：新增
 */
package com.cloud.utils.httprequest;

import java.io.File;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.cloud.utils.client.RestClientService;

/**
 * @Title:  SamRestfulTemplate.java
 * @Package: com.cloud.utils.httprequest.SamRestfulTemplate.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年6月4日 下午6:39:23
 */
@Service
public class SamRestfulTemplate {

	private static Logger logger = LoggerFactory.getLogger(SamRestfulTemplate.class); // 日志记录
	
	@Autowired
	protected RestClientService restClientService;
	
	/**
	 * 负载均衡调用服务
	 * 
	 * @param String服务标识
	 * @param String
	 * @param String
	 * @return String
	 */
	public String postString(String serviceId, String path, String body) {
		try {
			return restClientService.postStringToService(serviceId, path, body);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	public String postString(String host, int port, String path, String body) {
		try {
			return restClientService.postString(host, port, path, body);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	public String postString(String url, String body) {
		try {
			return restClientService.postString(url, body);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	public String post(String url, MultiValueMap<String, Object> params) {
		try {
			return restClientService.postReq(url, params);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	public String postFile(String host, int port, String path, Map<String, File> map) {
		try {
			return restClientService.postFile(host, port, path, map);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	public String postFile(String url, Map<String, File> map) {
		try {
			return restClientService.postFile(url, map);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
}
