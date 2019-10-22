/*
 * 文件名：LoggerUtils.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： LoggerUtils.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年5月13日
 * 修改内容：新增
 */
package com.cloud.utils.logger;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloud.utils.enums.LogFileNameEnum;

/**
 * @Title:  LoggerUtils.java
 * @Package: com.ylink.bgbc.util.LoggerUtils.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年5月13日 下午7:23:30
 */
public class LoggerUtils {

	private static HashMap<String, Logger> logHashMap = new HashMap<String, Logger>();
	
	public static <T> Logger Logger(Class<T> clazz) {
		return LoggerFactory.getLogger(clazz);
	}

	public static Logger Logger_SEND() {
        return LoggerFactory.getLogger(LogFileNameEnum.LOG_FIEL_SEND.getEnValue());
    }
	
	public static Logger Logger_RECV() {
        return LoggerFactory.getLogger(LogFileNameEnum.LOG_FIEL_RECV.getEnValue());
    }
	
	/**
	 * 打印到指定的文件内
	 * @param logName 日志文件名
	 * @return
	 */
	public static Logger getInfoLog(String logName) {
		if(logHashMap.containsKey(logName)) {
			return logHashMap.get(logName);
		}else{
			Logger log = LoggerFactory.getLogger(logName);
			logHashMap.put(logName, log);
			return log;
		}
	}
}
