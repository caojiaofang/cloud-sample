/*
 * 文件名：LogFileNameEnum.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： LogFileNameEnum.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年5月13日
 * 修改内容：新增
 */
package com.cloud.utils.enums;


import com.cloud.utils.enums.enumi.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Title:  LogFileNameEnum.java
 * @Package: com.ylink.bgbc.enums.LogFileNameEnum.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年5月13日 下午7:19:53
 */
@AllArgsConstructor
public enum LogFileNameEnum implements IEnum<String>{

	LOG_FIEL_SEND("send_log", "往账日志文件名"),
	LOG_FIEL_RECV("recv_log", "来账日志文件名"),
	;
	
	/** 枚举值*/
    @Getter
    private String enValue;

    /** 枚举名称*/
    @Getter
    private String enName;
}

