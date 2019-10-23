package com.cloud.auto.dao.impl;

import com.cloud.auto.dao.ControllerImplAutoDao;
import com.cloud.auto.util.ConfigUtil;
import com.cloud.auto.util.FileUtil;

public class ControllerImplAutoDaoImpl implements ControllerImplAutoDao{
	

	@Override
	public boolean createController() {
		String controllerFlag = ConfigUtil.controllerFlag;
		String controllerPackage = ConfigUtil.controllerPackage;
		String projectPath = ConfigUtil.projectPath;
		String handlerImplPackage = ConfigUtil.handlerImplPackage;
		String interfaceName = ConfigUtil.interfaceName;
		String controllerUrl = ConfigUtil.controllerUrl;
		String lowInterFaceName = interfaceName.substring(0,1).toLowerCase()+interfaceName.substring(1);
		if("true".equals(controllerFlag)) {
			handlerImplPackage = handlerImplPackage.replaceAll("interfaceName", lowInterFaceName);
			controllerPackage = controllerPackage.replace("interfaceName", lowInterFaceName);
			String controllerPath = controllerPackage.replace(".", "/");
			String path = projectPath + "/src/" + controllerPath;
			String fileName = interfaceName + "Controller";
			//package
			String packageCon = "package " + controllerPackage + ";\n\n";
			//引包
			StringBuffer importCon = new StringBuffer();
			importCon.append("import org.springframework.web.bind.annotation.RestController;\n");
			importCon.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
			importCon.append("import org.springframework.web.bind.annotation.RequestMethod;\n");
			importCon.append("import org.springframework.web.bind.annotation.RequestBody;\n");
			importCon.append("import javax.annotation.Resource;\n");
			importCon.append("import "+handlerImplPackage+"."+interfaceName+"Handler;\n");
			//类名
			String className = "\n\n@RestController\n@RequestMapping(value = \""+controllerUrl+"\",method = RequestMethod.POST)\npublic class " + fileName  + "{\n\n";
			//类体
			StringBuffer classCon = new StringBuffer();
			classCon.append("\t@Resource\n\tprivate "+interfaceName+"Handler "+lowInterFaceName+"Handler;\n\n");
			classCon.append("\t@RequestMapping(value = \"/send\")\n\tpublic String sendIndex(@RequestBody String msg) {\n");
			classCon.append("\t\treturn "+lowInterFaceName+"Handler.sendHandle(msg);\n\t}\n\n\n");
			
			classCon.append("\t@RequestMapping(value = \"/recv\")\n\tpublic String recvIndex(@RequestBody String msg) {\n");
			classCon.append("\t\treturn "+lowInterFaceName+"Handler.recvHandle(msg);\n\t}\n\n\n");
			
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
