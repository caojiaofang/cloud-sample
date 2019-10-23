package com.cloud.auto.dao.impl;

import java.util.List;

import com.cloud.auto.bean.TableStruct;
import com.cloud.auto.dao.GetTablesDao;
import com.cloud.auto.dao.ServiceImplAutoDao;
import com.cloud.auto.util.ConfigUtil;
import com.cloud.auto.util.FileUtil;

/**
 * 鐢熸垚ServiceImpl瀹炵幇绫荤殑dao灞傚疄鐜扮被
 * 
 * @author
 */
@SuppressWarnings("all")
public class ServiceImplAutoDaoImpl implements ServiceImplAutoDao {

	GetTablesDao getTables = new GetTablesDaoImpl();
	List<TableStruct> list = getTables.getTablesStruct();
	TableStruct sendTable = null;
	TableStruct	recvTable = null;

	@Override
	public boolean createServiceImpl() {
		String projectPath = ConfigUtil.projectPath;
		String serviceImplFalg = ConfigUtil.serviceImplFlag;
		String serviceImplPackage = ConfigUtil.serviceImplPackage;
		String beanPackage = ConfigUtil.beanPackage;
		String servicePackage = ConfigUtil.servicePackage;
		String daoPackage = ConfigUtil.daoPackage;
		String interfaceName = ConfigUtil.interfaceName;
		if ("true".equals(serviceImplFalg) && list.size() > 0) {
			
			serviceImplPackage = serviceImplPackage.replaceAll("interfaceName", interfaceName.substring(0,1).toLowerCase()+interfaceName.substring(1));
			String serviceImplPath = serviceImplPackage.replace(".", "/");
			String path = projectPath + "/src/" + serviceImplPath;
			
				String fileName = interfaceName + "Service";
			
				String packageCon = "package " + serviceImplPackage + ";\n\n";
				StringBuffer importCon = new StringBuffer();
				//基础引包
				importCon.append("import javax.annotation.Resource;\n");
				importCon.append("import org.springframework.stereotype.Service;");
				String className = "\n\n@Service \npublic class " + fileName  + "{\n\n";
				StringBuffer classCon = new StringBuffer();

				for( int i = 0 ; i < list.size() ; i ++ ) {
					TableStruct tableStruct = list.get(i);
					importCon.append("import " + daoPackage + "." + tableStruct.getDaoName() + ";\n");
					importCon.append("import " + beanPackage + "." + tableStruct.getDoName() + ";\n");
					classCon.append("\t@Resource\n\tprivate " + tableStruct.getDaoName() + " " +  
							tableStruct.getDaoName().substring(0, 1).toLowerCase() + tableStruct.getDaoName().substring(1) + ";\n\n");
				}

				if( list.size() == 1 ) { //来往账只有一张表
					sendTable = list.get(0);
					recvTable = list.get(0);
				} else if (list.size() == 2) {
					sendTable = list.get(0);
					recvTable = list.get(1);
				}
				
				if( list.size() > 0) {
					classCon.append("\tpublic void saveSend("+sendTable.getDoName()+" dbSendDO){\n");
					classCon.append("\t\t"+sendTable.getDaoName().substring(0, 1).toLowerCase() + sendTable.getDaoName().substring(1)+".insertRecord(dbSendDO);" );
					classCon.append("\n\t}\n\n");
					
					classCon.append("\n\tpublic void saveRecv("+recvTable.getDoName()+" dbRecvDO){\n");
					classCon.append("\t\t"+recvTable.getDaoName().substring(0, 1).toLowerCase() + recvTable.getDaoName().substring(1)+".insertRecord(dbRecvDO);" );
					classCon.append("\n\t}\n\n");
				}
				
				
				StringBuffer content = new StringBuffer();
				content.append(packageCon);
				content.append(importCon.toString());
				content.append(className);
				content.append(classCon.toString());
				content.append("\n}");
				FileUtil.createFileAtPath(path + "/", fileName + ".java",
						content.toString());
				
			
			return true;
		}
		return false;
	}

}