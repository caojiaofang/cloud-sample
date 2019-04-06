/*
 * 文件名：TestController.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： TestController.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年4月6日
 * 修改内容：新增
 */
package com.cloud.provider.controller;

import javax.annotation.Resource;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.db.dao.PropertiesDao;
import com.cloud.db.pojo.PropertiesPOJO;

/**
 * @Title:  TestController.java
 * @Package: com.dm.provider.controller.TestController.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年4月6日 上午11:57:01
 */
@RestController
@RefreshScope
@RequestMapping("test")
public class TestController {

	@Resource
	private PropertiesDao propertiesDao;
	
	@GetMapping("/get")
	@ResponseBody
	public String getString() {
		PropertiesPOJO propertiesPOJO = propertiesDao.selectById(1l);
		return propertiesPOJO.toString();
	}
}
