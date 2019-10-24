/*
 * 文件名：WebContextUtil.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： WebContextUtil.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年6月5日
 * 修改内容：新增
 */
package com.cloud.context;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cloud.utils.date.DateUtils;
import com.cloud.utils.tools.Base64Util;

/**
 * @Title:  WebContextUtil.java
 * @Package: com.cloud.context.WebContextUtil.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年6月5日 下午3:15:29
 */
public class WebContextUtil {

	private static final String UPPCONTEXT = "WebContext";
	
	public static final String YMDHMS_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static void setUserContext(WebContextPOJO webContextPOJO, HttpSession session) {
		session.setAttribute(UPPCONTEXT, webContextPOJO);
	 }
	
	public static void key(WebContextPOJO webContextPOJO, HttpSession session) {
		String string = DateUtils.format(new Date(), YMDHMS_PATTERN) + webContextPOJO.getUserAcct() + session.getId();
		webContextPOJO.setKey(Base64Util.encodeByte(string.getBytes()));
	}
	
	public static void outerLoginSession() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute(UPPCONTEXT);
	}
	
	public static WebContextPOJO getUserContext() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		WebContextPOJO webContextPOJO = (WebContextPOJO) session.getAttribute(UPPCONTEXT);
		return webContextPOJO;
	}
}
