package com.cloud.auto.dao.impl;

import com.cloud.auto.dao.ApplicationAutoDao;
import com.cloud.auto.util.ConfigUtil;
import com.cloud.auto.util.FileUtil;

public class ApplicationAutoDaoImpl implements ApplicationAutoDao{

	@Override
	public boolean createApplication() {
		String projectPath = ConfigUtil.projectPath;
		String interfaceName = ConfigUtil.interfaceName;
		String applicationFlag = ConfigUtil.applicationFlag;
		String applicationPackage = ConfigUtil.applicationPackage;
		String lowInterFaceName = interfaceName.substring(0,1).toLowerCase()+interfaceName.substring(1);
		if("true".equals(applicationFlag)) {
			applicationPackage = applicationPackage.replaceAll("interfaceName", lowInterFaceName);
			String applicationPath = applicationPackage.replace(".", "/");
			String path = projectPath + "/src/" + applicationPath;
			String fileName = interfaceName + "Application";
			//package
			String packageCon = "package " + applicationPackage + ";\n\n";
			//����
			StringBuffer importCon = new StringBuffer();
			importCon.append("import org.springframework.boot.SpringApplication;\n");
			importCon.append("import org.springframework.cloud.client.discovery.EnableDiscoveryClient;\n");
			importCon.append("import org.springframework.cloud.netflix.eureka.EnableEurekaClient;\n");
			importCon.append("import annotation.UppSpringBootApplication;\n");
			//����
			String className = "\n\n@EnableEurekaClient\n@UppSpringBootApplication\n@EnableDiscoveryClient\npublic class " + fileName  + "{\n\n";
			//����
			StringBuffer classCon = new StringBuffer();
			classCon.append("\n\n\tpublic static void main(String[] args) {\n");
			classCon.append("\t\tSpringApplication application = new SpringApplication("+interfaceName+"Application.class);\n");
			classCon.append("\t\tapplication.run(args);\n\t}\n");
			StringBuffer content = new StringBuffer();
			content.append(packageCon);
			content.append(importCon.toString());
			content.append(className);
			content.append(classCon.toString());
			content.append("\n}");
			FileUtil.createFileAtPath(path + "/", fileName + ".java", content.toString());
			return true;
		}
		return false;
	}


}
