package com.cloud.utils.json;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SamJsonUtil {

	private static Logger logger = LoggerFactory.getLogger(SamJsonUtil.class); // 日志记录
	
	private static ObjectMapper objectMapper;

	@Resource
	public void setObjectMapper(ObjectMapper objectMapper) {
		SamJsonUtil.objectMapper = objectMapper;
		objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
	}

	/**
	 * Object->JsonString
	 */
	public static String toJson(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * JsonString->Object
	 */
	public static <T> T toObject(String jsonStr, Class<T> clazz) {
		try {
			return objectMapper.readValue(jsonStr, clazz);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * JsonString->List
	 */
	public static <T> List<T> toList(String jsonStr) {
		try {
			return objectMapper.readValue(jsonStr, new TypeReference<List<T>>() {
			});
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * JsonString->Map
	 */
	public static <T> Map<String, T> toMap(String jsonStr) {
		try {
			return objectMapper.readValue(jsonStr, new TypeReference<Map<String, T>>() {
			});
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * 获取Json串里的指定值
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValueInJson(String jsonStr, String key) {
		Map<String, Object> map = toMap(jsonStr);
		if (map == null) {
			return null;
		}
		return (T) map.get(key);
	}

	/**
	 * Json串->CommonInf
	 */
	public static CommonInf getCommonInf(String jsonStr) {
		return toObject(jsonStr, CommonInf.class);
	}

	/**
	 * 从JSON串里获取交易编码
	 */
	public static String getTradCdInJson(String jsonStr) {
		CommonInf o = getCommonInf(jsonStr);
		if (o == null) {
			return null;
		}
		return o.getTradCd();
	}

}
