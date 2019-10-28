/*
 * 文件名：WebContextFilter.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： WebContextFilter.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年6月5日
 * 修改内容：新增
 */
package com.cloud.context;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Title:  WebContextFilter.java
 * @Package: com.cloud.context.WebContextFilter.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年6月5日 下午3:09:00
 */
@Component
@WebFilter(urlPatterns = "/*")
public class WebContextFilter implements Filter{

	private static Logger logger = LoggerFactory.getLogger(WebContextFilter.class); // 日志记录
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			String s = httpServletRequest.getRequestURI();
			if (isFilter(s)) {
				if (WebContextUtil.getUserContext() == null) {
					logger.debug("用户session已过期!");
					httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.html");
					logger.debug("sendRedirect login.html after!");
					return;
				}else if(null == WebContextUtil.getUserContext().getUserAcct()){
					logger.debug("用户session登陆信息不存在!");
					httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.html");
					logger.debug("sendRedirect login.html after!");
					return;
				}
			}
			chain.doFilter(request, response);
		}
	}
	
	
	@Override
	public void destroy() {

	}

	private boolean isFilter(String s) {
		if (s.endsWith(".html") || s.indexOf(".") < 0) {
			if (s.contains("login") || s.contains("logout")) {
				if(s.contains("getUserInfo")) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		}
		return false;
	}

}
