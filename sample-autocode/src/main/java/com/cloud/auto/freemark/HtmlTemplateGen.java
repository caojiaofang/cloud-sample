package com.cloud.auto.freemark;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.cloud.auto.bean.ColumnStruct;
import com.cloud.auto.bean.TableStruct;
import com.cloud.auto.dao.GetTablesDao;
import com.cloud.auto.dao.impl.GetTablesDaoImpl;
import com.cloud.auto.util.ConfigUtil;
import com.cloud.auto.util.DataTypeUtil;
import com.cloud.auto.util.JdbcTypeUtil;
import com.cloud.auto.util.NameUtil;
import com.cloud.auto.util.ParamTypeUtil;

import freemarker.template.Template;

/**
 * 项目名称：autoGenerate
 * 类描述：html模版生成
 * 创建者：longhuaiyu
 * 创建时间：2018年8月1日上午10:33:42
 * 版本号：V1.0
 */
@SuppressWarnings("all")
public class HtmlTemplateGen {
	//生成html
	public boolean generateHtml() {
		try {
			String path = ConfigUtil.webViewProjPath + "html/";
			String interfaceName = ConfigUtil.interfaceName;
			//获取模板
			Template temp = FreeMarkerInit.getDefinedTemplate("html.ftl");
			
			// 从GetTablesDaoImpl中获得装有所有表结构的List
			GetTablesDao getTables = new GetTablesDaoImpl();
			List<TableStruct> list = getTables.getTablesStruct();
			
			//设置根值
			Map<String, Object> root = new HashMap<String, Object>();
			// 文件名
			String fileName = "";
			String url = "";
			StringBuffer pk = new StringBuffer();
			for(int i=0;i<list.size();i++) {
				// 表名
				String tableName = list.get(i).getTableName();
				if(list.size() > 1) {
					fileName = NameUtil.toLowerCaseFirstOne(interfaceName) + (i==1?"Send":"Recv") +"_main";
				}else {
					fileName = NameUtil.toLowerCaseFirstOne(interfaceName) + "_main";
				}
				url = interfaceName;
				// 获得每个表的所有列结构
				List<ColumnStruct> columns = list.get(i).getColumns();
				//列名转换成bean名称
				for(ColumnStruct col : columns) {
					col.setColumnName(NameUtil.columnName(col.getColumnName()));
				}
				root.put("columns", columns);
				
				List<ColumnStruct> keyColumns = list.get(i).getpKeyColums();
				for(int k=0;k<keyColumns.size();k++){
					ColumnStruct columnStruct = keyColumns.get(k);
					columnStruct.setColumnName(NameUtil.columnName(columnStruct.getColumnName()));
					pk.append(NameUtil.columnName(columnStruct.getColumnName()));
					pk.append("=\"+");
					pk.append(NameUtil.columnName(columnStruct.getColumnName())+"+\"&");
				}
				root.put("primarkKeys", keyColumns);
				root.put("url", NameUtil.toLowerCaseFirstOne(url));
				root.put("urlParam", pk.substring(0, pk.length()-1));
				
				if(list.size() == 1) {
					root.put("rpFlg", "");
				}else {
					if(i == 1) {
						root.put("rpFlg", "send");
					}else {
						root.put("rpFlg", "recv");
					}
					
				}
				
				File file = new File(path, fileName+".html");
				if(!file.getParentFile().isDirectory()) {
					file.getParentFile().mkdirs();
				}
				if(!file.exists()) {
					file.createNewFile();
				}
		        OutputStream fos = new  FileOutputStream( file);
		        Writer out = new OutputStreamWriter(fos);
		        temp.process(root, out);
		        fos.flush();  
		        fos.close();
			}
			
	        return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
}

