/*
 * 文件名：IndexController.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： IndexController.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年10月28日
 * 修改内容：新增
 */
package com.cloud.context;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Title:  IndexController.java
 * @Package: com.cloud.context.IndexController.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年10月28日 上午10:22:14
 */
@Configuration
@RequestMapping("/")
public class IndexController{
	
    @RequestMapping(path = "/")
    public String index() {
    	return "forward:login.html";
    } 
}
