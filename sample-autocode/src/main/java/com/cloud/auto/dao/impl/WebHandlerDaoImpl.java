package com.cloud.auto.dao.impl;

import java.util.List;

import com.cloud.auto.bean.ColumnStruct;
import com.cloud.auto.bean.TableStruct;
import com.cloud.auto.dao.GetTablesDao;
import com.cloud.auto.dao.WebHandlerDao;
import com.cloud.auto.util.ConfigUtil;
import com.cloud.auto.util.DataTypeUtil;
import com.cloud.auto.util.FileUtil;
import com.cloud.auto.util.NameUtil;

public class WebHandlerDaoImpl implements WebHandlerDao{

	GetTablesDao getTables = new GetTablesDaoImpl();
	List<TableStruct> list = getTables.getTablesStruct();
	
	@Override
	public boolean createWebHandler() {
		String webProjPath = ConfigUtil.webViewProjPath;
		String webHandlerFlag = ConfigUtil.webHandlerFlag;
		String webHandlerPackage = ConfigUtil.webHandlerPackage;
		String interfaceName = ConfigUtil.interfaceName;
		String lowInterfaceName = interfaceName.substring(0,1).toLowerCase() + interfaceName.substring(1);
		String entityPackage = ConfigUtil.beanPackage;
		String daoPackage = ConfigUtil.daoPackage;
		String voPackage = ConfigUtil.voPackage;
		
		
		if("true".equals(webHandlerFlag)) {
			webHandlerPackage = webHandlerPackage.replace("interfaceName", lowInterfaceName);
			String webHandlerPath = webHandlerPackage.replace(".", "/");
			String path = webProjPath + "/src/" + webHandlerPath;
			String fileName = interfaceName + "Handler";
			//package
			String packageCon = "package " + webHandlerPackage + ";\n\n";
			//引包
			StringBuffer importCon = new StringBuffer();
			importCon.append("import java.util.Map;\n");
			importCon.append("import java.util.List;\n");
			importCon.append("import javax.annotation.Resource;\n");
			importCon.append("import org.springframework.stereotype.Service;\n");
			importCon.append("import com.ylink.msf.mybatis.interceptor.PageBounds;\n");
			importCon.append("import com.ylink.upp.web.base.BaseHandler;\n");
			importCon.append("import com.ylink.upp.web.base.PagerView;\n");
			importCon.append("import lombok.extern.slf4j.Slf4j;\n");
			
			//类名
			String className = "\n\n@Slf4j\n@Service\npublic class " + fileName  + " extends BaseHandler {\n\n";
			//类体
			StringBuffer classCon = new StringBuffer();
			String rpFlg = null ;
			String voName = null;
			//依赖注入
			for(int j = 0 ; j < list.size() ; j ++ ) {
				classCon.append("\t@Resource\n\tprivate "+list.get(j).getDaoName()+" "+list.get(j).getDaoName().substring(0, 1).toLowerCase()+list.get(j).getDaoName().substring(1)+";\n\n");
			}
			
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
				importCon.append("import "+voPackage.replace("interfaceName", lowInterfaceName)+"."+voName+";\n");
				importCon.append("import "+entityPackage+"."+list.get(i).getDoName()+";\n");
				importCon.append("import "+daoPackage+"."+list.get(i).getDaoName()+";\n");
				
				//加载页面列表数据
				classCon.append("\tpublic PagerView<"+voName+"> "+rpFlg+"Main(Map<String,Object> param) {\n");
				classCon.append("\t\tPageBounds pageBounds = getPageBounds(param);\n");
				classCon.append("\t\tList<"+list.get(i).getDoName()+"> doList = "+getFirstCharLow(list.get(i).getDaoName())+".selectByMap(param, pageBounds);\n");
				classCon.append("\t\treturn getPagerView(pageBounds.getCountRows(),doList,"+voName+".class);\n\t}\n\n\n");
				
				//浏览
				//参数列表
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
				classCon.append("\tpublic "+voName+" "+rpFlg+"View("+pktParam+") {\n");
				classCon.append("\t\t"+list.get(i).getDoName()+" entity = "+getFirstCharLow(list.get(i).getDaoName())+".selectById("+param+");\n");
				classCon.append("\t\t"+voName+" vo = new "+voName+"();\n");
				classCon.append("\t\tcopyBean(entity, vo);\n");
				classCon.append("\t\treturn vo;\n\t}\n\n\n");
				
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

	/**
	 * 首字母小写 
	 */
	public static String getFirstCharLow(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}
	

}
