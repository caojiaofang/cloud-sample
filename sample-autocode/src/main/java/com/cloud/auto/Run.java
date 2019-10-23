package com.cloud.auto;

import com.cloud.auto.dao.ApplicationAutoDao;
import com.cloud.auto.dao.BeanAutoDao;
import com.cloud.auto.dao.ControllerImplAutoDao;
import com.cloud.auto.dao.DaoAutoDao;
import com.cloud.auto.dao.DtoAutoDao;
import com.cloud.auto.dao.HandlerImplAutoDao;
import com.cloud.auto.dao.MapperXmlAutoDao;
import com.cloud.auto.dao.ServiceImplAutoDao;
import com.cloud.auto.dao.VoAutoDao;
import com.cloud.auto.dao.WebControllerAutoDao;
import com.cloud.auto.dao.WebHandlerDao;
import com.cloud.auto.dao.impl.ApplicationAutoDaoImpl;
import com.cloud.auto.dao.impl.BeanAutoDaoImpl;
import com.cloud.auto.dao.impl.ControllerImplAutoDaoImpl;
import com.cloud.auto.dao.impl.DaoAutoDaoImpl;
import com.cloud.auto.dao.impl.DtoAutoDaoImpl;
import com.cloud.auto.dao.impl.HandlerImplAutoDaoImpl;
import com.cloud.auto.dao.impl.MapperXmlAutoDaoImpl;
import com.cloud.auto.dao.impl.ServiceImplAutoDaoImpl;
import com.cloud.auto.dao.impl.VoAutoDaoImpl;
import com.cloud.auto.dao.impl.WebControllerAutoDaoImpl;
import com.cloud.auto.dao.impl.WebHandlerDaoImpl;
import com.cloud.auto.freemark.HtmlTemplateGen;

public class Run {

	public static void main(String[] args) {
		//generateCode();
		generateCode();
	}

	public static void generateCode() {
		// 1.生成Bean实体类
		BeanAutoDao beanAuto = new BeanAutoDaoImpl();
		if (beanAuto.createBean()) {
			System.out.println("Bean实体类生成成功");
		} else {
			System.out.println("Bean实体类生成失败");
		}
		// 2.生成Dao接口
		DaoAutoDao daoAuto = new DaoAutoDaoImpl();
		if (daoAuto.createDao()) {
			System.out.println("Dao接口生成成功");
		} else {
			System.out.println("Dao接口生成失败");
		}
		// 3.生成Mapper.xml
		MapperXmlAutoDao mapperXmlAuto = new MapperXmlAutoDaoImpl();
		if (mapperXmlAuto.createMapperXml()) {
			System.out.println("Mapper.xml生成成功");
		} else {
			System.out.println("Mapper.xml生成失败");
		}
//		// 4.生成ServiceImpl实现类
//		ServiceImplAutoDao serviceImplAuto = new ServiceImplAutoDaoImpl();
//		if (serviceImplAuto.createServiceImpl()) {
//			System.out.println("Service生成成功");
//		} else {
//			System.out.println("Service生成失败");
//		}
//		// 5.生成handle
//		HandlerImplAutoDao handle = new HandlerImplAutoDaoImpl();
//		if (handle.createHandlerImpl()) {
//			System.out.println("handler生成成功");
//		}else {
//			System.out.println("handler生成失败");
//		}
//		// 6.生成controller
//		ControllerImplAutoDao controller = new ControllerImplAutoDaoImpl();
//		if(controller.createController()) {
//			System.out.println("controller生成成功");
//		}else {
//			System.out.println("controller生成失败");
//		}
//		// 7.生成application
//		ApplicationAutoDao application = new ApplicationAutoDaoImpl();
//		if(application.createApplication()) {
//			System.out.println("application生成成功");
//		}else {
//			System.out.println("application生成失败");
//		}
//		// 8.生成dto
//		DtoAutoDao dto = new DtoAutoDaoImpl();
//		if(dto.createDto()) {
//			System.out.println("dto生成成功");
//		}else {
//			System.out.println("dto生成失败");
//		}
//		// 9.页面生成
//		HtmlTemplateGen html = new HtmlTemplateGen();
//		if(html.generateHtml()) {
//			System.out.println("html生成成功");
//		}else {
//			System.out.println("html生成失败");
//		}
//		// 10.生成Vo
//		VoAutoDao vo = new VoAutoDaoImpl();
//		if(vo.createVo()) {
//			System.out.println("vo生成成功");
//		}else {
//			System.out.println("vo生成失败");
//		}
//		// 11.生成web-controller
//		WebControllerAutoDao webController = new WebControllerAutoDaoImpl();
//		if(webController.createWebController()) {
//			System.out.println("webController生成成功");
//		}else {
//			System.out.println("webController生成失败");
//		}
//		// 12.生成web-handler
//		WebHandlerDao webHandler = new WebHandlerDaoImpl();
//		if(webHandler.createWebHandler()) {
//			System.out.println("webHandler生成成功");
//		}else {
//			System.out.println("webHandler生成失败");
//		}
	}
}
