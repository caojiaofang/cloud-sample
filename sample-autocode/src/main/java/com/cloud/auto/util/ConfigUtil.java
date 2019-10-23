package com.cloud.auto.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *  配置信息实体类
 */
public class ConfigUtil {

	//工程目录
	public static String projectPath;
	//实体类生成控制器
	public static String beanFlag;
	public static String beanPackage;
	//dao类生成控制器
	public static String daoFlag;
	public static String daoPackage;
	//service接口生成控制器
	public static String serviceFlag;
	public static String servicePackage;
	//mapper生成控制器
	public static String mapperXmlFlag;
	public static String mapperXmlPackage;
	//service实现类生成控制器
	public static String serviceImplFlag;
	public static String serviceImplPackage;
	//handler生成控制器
	public static String handlerImplFlag;
	public static String handlerImplPackage;
	//controller生成控制器
	public static String controllerFlag;
	public static String controllerPackage;
	public static String controllerUrl;
	//application生成控制器
	public static String applicationFlag;
	public static String applicationPackage;
	//dto生成控制器
	public static String dtoFlag;
	public static String dtoPackage;
	//html生成控制器
	public static String webViewProjPath;
	//vo生成控制器
	public static String voFlag;
	public static String voPackage;
	//webController生成控制器
	public static String webControllerFlag;
	public static String webControllerPackage;
	//webHandler生成控制器
	public static String webHandlerFlag;
	public static String webHandlerPackage;
	
	//数据源
	public static String driver;
	public static String url;
	public static String userName;
	public static String userPs;
	//表名
	public static String tables;
	
	//接口名
	public static String interfaceName;
	//类名
	public static String className;
	static {
		try {
			InputStream in = DataSourceUtil.class.getClassLoader()
					.getResourceAsStream("config.properties");
			Properties pro = new Properties();
			pro.load(in);
			projectPath = pro.getProperty("projectPath");
			beanFlag = pro.getProperty("beanFlag");
			beanPackage = pro.getProperty("beanPackage");
			daoFlag = pro.getProperty("daoFlag");
			daoPackage = pro.getProperty("daoPackage");
			serviceFlag = pro.getProperty("serviceFlag");
			servicePackage = pro.getProperty("servicePackage");
			mapperXmlFlag = pro.getProperty("mapperXmlFlag");
			mapperXmlPackage = pro.getProperty("mapperXmlPackage");
			serviceImplFlag = pro.getProperty("serviceImplFlag");
			serviceImplPackage = pro.getProperty("serviceImplPackage");
			handlerImplFlag = pro.getProperty("handlerImplFlag");
			handlerImplPackage = pro.getProperty("handlerImplPackage");
			controllerFlag = pro.getProperty("controllerFlag");
			controllerPackage = pro.getProperty("controllerPackage");
			controllerUrl = pro.getProperty("controllerUrl");
			applicationFlag = pro.getProperty("applicationFlag");
			applicationPackage = pro.getProperty("applicationPackage");
			dtoFlag = pro.getProperty("dtoFlag");
			dtoPackage = pro.getProperty("dtoPackage");
			webViewProjPath = pro.getProperty("webViewProjPath");
			voFlag = pro.getProperty("voFlag");
			voPackage = pro.getProperty("voPackage");
			webControllerFlag = pro.getProperty("webControllerFlag");
			webControllerPackage = pro.getProperty("webControllerPackage");
			webHandlerFlag = pro.getProperty("webHandlerFlag");
			webHandlerPackage = pro.getProperty("webHandlerPackage");
			
			driver = pro.getProperty("jdbc.driverClass");
			url = pro.getProperty("jdbc.url");
			userName = pro.getProperty("jdbc.username");
			userPs = pro.getProperty("jdbc.password");
			//琛ㄥ悕
			tables = pro.getProperty("tables");
			
			className = pro.getProperty("className");
			interfaceName = pro.getProperty("interfaceName");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}