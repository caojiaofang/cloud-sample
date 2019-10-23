package com.cloud.auto.dao.impl;

import java.util.List;

import com.cloud.auto.bean.ColumnStruct;
import com.cloud.auto.bean.TableStruct;
import com.cloud.auto.dao.GetTablesDao;
import com.cloud.auto.dao.WebControllerAutoDao;
import com.cloud.auto.util.ConfigUtil;
import com.cloud.auto.util.DataTypeUtil;
import com.cloud.auto.util.FileUtil;
import com.cloud.auto.util.NameUtil;

public class WebControllerAutoDaoImpl implements WebControllerAutoDao{
	
	GetTablesDao getTables = new GetTablesDaoImpl();
	List<TableStruct> list = getTables.getTablesStruct();
	
	@Override
	public boolean createWebController() {
		String webControllerFlag = ConfigUtil.webControllerFlag;
		String webControllerPackage = ConfigUtil.webControllerPackage;
		String webProjectPath = ConfigUtil.webViewProjPath;
		String webHandlerPackage = ConfigUtil.webHandlerPackage;
		String interfaceName = ConfigUtil.interfaceName;
		String voPackage = ConfigUtil.voPackage;
		String lowInterFaceName = interfaceName.substring(0,1).toLowerCase()+interfaceName.substring(1);
		if("true".equals(webControllerFlag)) {
			webHandlerPackage = webHandlerPackage.replace("interfaceName", lowInterFaceName);
			webControllerPackage = webControllerPackage.replace("interfaceName", lowInterFaceName);
			String controllerPath = webControllerPackage.replace(".", "/");
			String path = webProjectPath + "/src/" + controllerPath;
			String fileName = interfaceName + "Controller";
			//package
			String packageCon = "package " + webControllerPackage + ";\n\n";
			//����
			StringBuffer importCon = new StringBuffer();
			importCon.append("import java.util.Map;\n");
			importCon.append("import org.springframework.cloud.context.config.annotation.RefreshScope;\n");
			importCon.append("import org.springframework.web.bind.annotation.RequestParam;\n");
			importCon.append("import com.ylink.upp.web.base.PagerView;\n");
			importCon.append("import org.springframework.web.bind.annotation.RestController;\n");
			importCon.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
			importCon.append("import org.springframework.web.bind.annotation.RequestBody;\n");
			importCon.append("import javax.annotation.Resource;\n");
			importCon.append("import "+webHandlerPackage+"."+interfaceName+"Handler;\n");
			//����
			String className = "\n\n@RefreshScope\n@RestController\n@RequestMapping(value = \"/"+lowInterFaceName+"/\")\npublic class " + fileName  + "  {\n\n";
			//����
			StringBuffer classCon = new StringBuffer();
			classCon.append("\t@Resource\n\tprivate "+interfaceName+"Handler "+lowInterFaceName+"Handler;\n\n");
			String rpFlg = null ;
			String voName = null;
			for( int i = 0 ; i < list.size() ; i++ ) {
				if( list.size() == 1 ) { 
					rpFlg = "";
					voName = interfaceName + "Vo";
				} else {
					if (i == 0) {
						rpFlg = "send";
						voName = interfaceName + "SendVo";
					} 
					if( i == 1) {
						rpFlg = "recv";
						voName = interfaceName + "RecvVo";
					}
				}
				importCon.append("import "+voPackage.replace("interfaceName", lowInterFaceName)+"."+voName+";\n");
				//����ҳ���б�����
				classCon.append("\t@RequestMapping(path = \"/"+rpFlg+"Main\")\n");
				classCon.append("\t public PagerView<"+voName+"> "+rpFlg+"Main(@RequestBody Map<String,Object> param) {\n");
				classCon.append("\t\treturn "+lowInterFaceName+"Handler."+rpFlg+"Main(param);\n\t}\n\n");
				
				//���
				//�����б�
				String pktParam = "";
				String param = "" ;
				List pktList = list.get(i).getpKeyColums();
				for ( int j = 0 ; j < pktList.size() ; j ++ ) {
					ColumnStruct pkt = (ColumnStruct)pktList.get(j);
					pktParam += DataTypeUtil.sqlType2JavaType(pkt) + " " +  NameUtil.columnName(pkt.getColumnName()) + ",";
					param += NameUtil.columnName(pkt.getColumnName()) + ",";
				}
				pktParam = pktParam.substring(0, pktParam.length() -1 );
				param = param.substring(0, param.length() - 1);
				classCon.append("\t@RequestMapping(path = \"/"+rpFlg+"View\")\n");
				classCon.append("\tpublic "+voName+" "+rpFlg+"View(@RequestParam "+pktParam+") {\n");
				classCon.append("\t\treturn "+lowInterFaceName+"Handler."+rpFlg+"View("+param+");\n\t}\n\n");
				
			}
			
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

