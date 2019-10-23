package com.cloud.auto.dao.impl;

import java.util.List;

import com.cloud.auto.bean.TableStruct;
import com.cloud.auto.dao.GetTablesDao;
import com.cloud.auto.dao.HandlerImplAutoDao;
import com.cloud.auto.util.ConfigUtil;
import com.cloud.auto.util.FileUtil;

public class HandlerImplAutoDaoImpl implements HandlerImplAutoDao{

	GetTablesDao getTables = new GetTablesDaoImpl();
	List<TableStruct> list = getTables.getTablesStruct();
	
	@Override
	public boolean createHandlerImpl() {
		String projectPath = ConfigUtil.projectPath;
		String serviceImplPackage = ConfigUtil.serviceImplPackage;
		String beanPackage = ConfigUtil.beanPackage;
		String handlerImplFlag = ConfigUtil.handlerImplFlag;
		String handlerImplPackage = ConfigUtil.handlerImplPackage;
		String interfaceName = ConfigUtil.interfaceName;
		String lowInterFaceName = interfaceName.substring(0,1).toLowerCase()+interfaceName.substring(1);
		if("true".equals(handlerImplFlag) && list.size() > 0) {
			handlerImplPackage = handlerImplPackage.replaceAll("interfaceName", lowInterFaceName);
			String handlerImplPath = handlerImplPackage.replace(".", "/");
			String path = projectPath + "/src/" + handlerImplPath;
			String fileName = interfaceName + "Handler";
			//package
			String packageCon = "package " + handlerImplPackage + ";\n\n";
			//引包
			StringBuffer importCon = new StringBuffer();
			importCon.append("import javax.annotation.Resource;\n");
			importCon.append("import org.springframework.stereotype.Service;\n");
			importCon.append("import lombok.extern.slf4j.Slf4j;\n");
			importCon.append("import com.ylink.upp.common.enums.TradCdEnum;\n");
			importCon.append("import com.ylink.upp.common.utils.UppJsonUtil;\n");
			importCon.append("import com.ylink.upp.common.msgdto."+interfaceName+"DTO;\n");
			importCon.append("import com.ylink.upp.common.enums.PfProCdEnum;\n");
			importCon.append("import com.ylink.upp.common.utils.MsgUtil;\n");
			importCon.append("import "+beanPackage+"."+list.get(0).getDoName()+";\n");
			importCon.append("import com.ylink.msf.util.tools.BeanCopyUtil;\n");
			importCon.append("import com.ylink.upp.common.msgdto.Upp5002DTO;\n");
			importCon.append("import "+serviceImplPackage.replace("interfaceName", lowInterFaceName)+"."+interfaceName+"Service;\n");
			//类名
			String className = "\n\n@Slf4j\n@Service\npublic class " + fileName  + "{\n\n";
			//类体
			StringBuffer classCon = new StringBuffer();
			//依赖注入
			classCon.append("\t@Resource\n\tprivate "+interfaceName+"Service "+lowInterFaceName+"Service\n\n");
			//往账handle
			classCon.append("\tpublic String sendHandle(String msg) {\n\n");
			classCon.append("\t\tif(TradCdEnum.TC"+interfaceName.replace("Upp", "_")+".getEnValue().equals(UppJsonUtil.getTradCdInJson(msg))){\n");
			classCon.append("\t\t\t"+interfaceName+"DTO " +lowInterFaceName+"DTO = UppJsonUtil.toObject(msg, "+interfaceName+"DTO.class);\n");
			classCon.append("\t\t\tif("+lowInterFaceName+"DTO==null){\n\t\t\t\tPfProCdEnum pfProCdEnum = PfProCdEnum.CD_UI000001;\n");
			classCon.append("\t\t\t\treturn UppJsonUtil.toJson(MsgUtil.getExceptionUpp5002DTO(pfProCdEnum.getEnValue(),pfProCdEnum.getEnName()));\n\t\t\t}\n");
			classCon.append("\t\t\ttry{\n");
			classCon.append("\t\t\t\t//往账入库\n");
			classCon.append("\t\t\t\t"+list.get(0).getDoName()+" sendDO = sendInit("+lowInterFaceName+"DTO);\n");
			classCon.append("\t\t\t\t"+lowInterFaceName+"Service.saveSend(sendDO);\n");
			classCon.append("\t\t\t\t//业务处理\n\n\n");
			classCon.append("\t\t\t\tUpp5002DTO upp5002DTO = getReplyDTO("+lowInterFaceName+"DTO, sendDO);\n");
			classCon.append("\t\t\t\treturn UppJsonUtil.toJson(upp5002DTO);\n");
			classCon.append("\t\t\t} catch (Exception e) {\n");
			classCon.append("\t\t\t\te.printStackTrace();\n");
			classCon.append("\t\t\t\tPfProCdEnum pfProCdEnum = PfProCdEnum.CD_UI900001;\n");
			classCon.append("\t\t\t\tUpp5002DTO upp5002DTO = MsgUtil.getUpp5002DTO("+lowInterFaceName+"DTO);\n");
			classCon.append("\t\t\t\tupp5002DTO.setPfProCd(pfProCdEnum.getEnValue());\n");
			classCon.append("\t\t\t\tupp5002DTO.setPfProDesc(pfProCdEnum.getEnName());\n");
			classCon.append("\t\t\t\treturn UppJsonUtil.toJson(upp5002DTO);\n");
			classCon.append("\t\t\t}\n");
			classCon.append("\n\n\treturn null;");
			classCon.append("\n\t}\n\n");
			//来账handle
			TableStruct recvTable = null;
			if(list.size() == 1) {
				recvTable = list.get(0);
			}else if(list.size() == 2){
				recvTable = list.get(1);
			}
			classCon.append("\tpublic String recvHandle(String msg) {\n\n");
			classCon.append("\t\tif(TradCdEnum.TC"+interfaceName.replace("Upp", "_")+".getEnValue().equals(UppJsonUtil.getTradCdInJson(msg))){\n");
			classCon.append("\t\t\t"+interfaceName+"DTO " +lowInterFaceName+"DTO = UppJsonUtil.toObject(msg, "+interfaceName+"DTO.class);\n");
			classCon.append("\t\t\tif("+lowInterFaceName+"DTO==null){\n\t\t\t\tPfProCdEnum pfProCdEnum = PfProCdEnum.CD_UI000001;\n");
			classCon.append("\t\t\t\treturn UppJsonUtil.toJson(MsgUtil.getExceptionUpp5002DTO(pfProCdEnum.getEnValue(),pfProCdEnum.getEnName()));\n\t\t\t}\n");
			classCon.append("\t\t\ttry{\n");
			classCon.append("\t\t\t\t//来账入库\n");
			classCon.append("\t\t\t\t"+recvTable.getDoName()+" recvDO = recvInit("+lowInterFaceName+"DTO);\n");
			classCon.append("\t\t\t\t"+lowInterFaceName+"Service.saveRecv(recvDO);\n");
			classCon.append("\t\t\t\t//业务处理\n\n\n");
			classCon.append("\t\t\t\tUpp5002DTO upp5002DTO = getReplyDTO("+lowInterFaceName+"DTO, recvDO);\n");
			classCon.append("\t\t\t\treturn UppJsonUtil.toJson(upp5002DTO);\n");
			classCon.append("\t\t\t} catch (Exception e) {\n");
			classCon.append("\t\t\t\te.printStackTrace();\n");
			classCon.append("\t\t\t\tPfProCdEnum pfProCdEnum = PfProCdEnum.CD_UI900001;\n");
			classCon.append("\t\t\t\tUpp5002DTO upp5002DTO = MsgUtil.getUpp5002DTO("+lowInterFaceName+"DTO);\n");
			classCon.append("\t\t\t\tupp5002DTO.setPfProCd(pfProCdEnum.getEnValue());\n");
			classCon.append("\t\t\t\tupp5002DTO.setPfProDesc(pfProCdEnum.getEnName());\n");
			classCon.append("\t\t\t\treturn UppJsonUtil.toJson(upp5002DTO);\n");
			classCon.append("\t\t\t}\n");
			classCon.append("\n\n\treturn null;");
			classCon.append("\n\t}\n\n");
			//方法
			classCon.append("\tprivate "+list.get(0).getDaoName()+" sendInit("+interfaceName+"DTO "+lowInterFaceName+"DTO) {\n");
			classCon.append("\t\t"+list.get(0).getDoName()+" sendDO = new "+list.get(0).getDoName()+"();\n");
			classCon.append("\t\tBeanCopyUtil.copy("+lowInterFaceName+"DTO, sendDO);\n");
			classCon.append("\t\t//业务字段和数据库字段映射转换\n\n\n");
			classCon.append("\t\treturn sendDO;\n\t}\n");
			
			classCon.append("\tprivate "+recvTable.getDaoName()+" recvInit("+interfaceName+"DTO "+lowInterFaceName+"DTO) {\n");
			classCon.append("\t\t"+recvTable.getDoName()+" recvDO = new "+recvTable.getDoName()+"();\n");
			classCon.append("\t\tBeanCopyUtil.copy("+lowInterFaceName+"DTO, recvDO);\n");
			classCon.append("\t\t//业务字段和数据库字段映射转换\n\n\n");
			classCon.append("\t\treturn recvDO;\n\t}\n");
			
			classCon.append("\tprivate Upp5002DTO getReplyDTO("+interfaceName+"DTO "+lowInterFaceName+"DTO, "+list.get(0).getDoName()+" sendDO) {\n");
			classCon.append("\t\tUpp5002DTO upp5002DTO = MsgUtil.getUpp5002DTO("+lowInterFaceName+"DTO);\n");
			classCon.append("\t\t//设置MsgId、PfProCd、PfProDesc、SndTm\n\n\n");
			classCon.append("\t\treturn upp5002DTO;\n\t}\n");
			
			if(list.size() > 1) {
				classCon.append("\tprivate Upp5002DTO getReplyDTO("+interfaceName+"DTO "+lowInterFaceName+"DTO, "+recvTable.getDoName()+" recvDO) {\n");
				classCon.append("\t\tUpp5002DTO upp5002DTO = MsgUtil.getUpp5002DTO("+lowInterFaceName+"DTO);\n");
				classCon.append("\t\t//设置MsgId、PfProCd、PfProDesc、SndTm\n\n\n");
				classCon.append("\t\treturn upp5002DTO;\n\t}\n");
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
