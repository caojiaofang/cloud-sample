/*
 * 文件名：LoginController.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： LoginController.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年10月24日
 * 修改内容：新增
 */
package com.cloud.web.login.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.web.login.handler.LoginHandler;

/**
 * @Title:  LoginController.java
 * @Package: com.cloud.web.login.controller.LoginController.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年10月24日 下午4:15:39
 */
@RestController
@RequestMapping("/login")
public class LoginController {

	@Resource
	private LoginHandler loginHandler;
	
	/**
	 * 登录
	 * 
	 * @param account
	 * @param password
	 * @param HttpSession
	 * @return String
	 */
	@RequestMapping(path = "/doLogin")
	public String doLogin(String account, String password, HttpSession session) {
		return loginHandler.doLogin(account, password, session);
	}
}
