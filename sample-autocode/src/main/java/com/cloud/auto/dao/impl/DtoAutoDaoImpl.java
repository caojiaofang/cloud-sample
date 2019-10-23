package com.cloud.auto.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.auto.bean.ColumnStruct;
import com.cloud.auto.bean.TableStruct;
import com.cloud.auto.dao.DtoAutoDao;
import com.cloud.auto.dao.GetTablesDao;
import com.cloud.auto.util.ConfigUtil;
import com.cloud.auto.util.DataTypeUtil;
import com.cloud.auto.util.FileUtil;
import com.cloud.auto.util.NameUtil;

public class DtoAutoDaoImpl implements DtoAutoDao {
	
	GetTablesDao getTables = new GetTablesDaoImpl();
	List<TableStruct> list = getTables.getTablesStruct();
	
	@Override
	public boolean createDto() {
		String projectPath = ConfigUtil.projectPath;
		String interfaceName = ConfigUtil.interfaceName;
		String dtoFlag = ConfigUtil.dtoFlag;
		String dtoPackage = ConfigUtil.dtoPackage;
		if ("true".equals(dtoFlag) && list.size() > 0) {
			String beanPath = dtoPackage.replace(".", "/");
			String path = projectPath + "/src/" + beanPath;

			String fileName = interfaceName+"DTO";
			List<ColumnStruct> columns = list.get(0).getColumns();
			String packageCon = "package " + dtoPackage + ";\n\n";
			StringBuffer importCon = new StringBuffer();
			String className = "public class " + fileName + "{\n\n";
			StringBuffer classCon = new StringBuffer();
			StringBuffer gettersCon = new StringBuffer();
			StringBuffer settersCon = new StringBuffer();
			StringBuffer noneConstructor = new StringBuffer();
			StringBuffer constructor = new StringBuffer();
			String constructorParam = "";
			StringBuffer constructorCon = new StringBuffer();
			Map<String,String> dataTypeMap = new HashMap<String,String>();
			for (int j = 0; j < columns.size(); j++) {
				String columnName = NameUtil.columnName(columns.get(j).getColumnName());
				String type = columns.get(j).getDataType();
				int colSize = columns.get(j).getColSize();
				int colScale = columns.get(j).getColScale();
				String dateType = DataTypeUtil.sqlType2JavaType(type, colSize, colScale);
				if(dateType.equals("Long")){
					String a = "1";
				}
				dataTypeMap.put(dateType, dateType);
				classCon.append("\t" + "private" + " " + dateType + " " + columnName + ";\t//"+ columns.get(j).getCulumnExplain() +"\n");
				if (constructorParam == null
						|| "".equals(constructorParam)) {
					constructorParam = dateType + " " + columnName;
				} else {
					constructorParam += "," + dateType + " " + columnName;
				}
				constructorCon.append("\t\t" + "this." + columnName + " = "
						+ columnName + ";\n");
			}
			if (dataTypeMap.containsKey("Date")) {
				importCon.append("import java.util.Date;\n");
			}
			if (dataTypeMap.containsKey("Timestamp")) {
				importCon.append("import java.sql.Timestamp;\n");
			}
			if (dataTypeMap.containsKey("BigDecimal")) {
				importCon.append("import java.math.BigDecimal;\n");
			}
			importCon.append("import lombok.Getter;\n");
			importCon.append("import lombok.Setter;\n\n");
			noneConstructor.append("\t" + "public" + "\t" + fileName
					+ "(){\n" + "\t\t" + "super();\n" + "\t" + "}\n");
			constructor.append("\t" + "public" + " " + fileName + "("
					+ constructorParam + "){\n" + "\t\t" + "super();\n"
					+ constructorCon + "\t" + "}\n");
			StringBuffer content = new StringBuffer();
			content.append(packageCon);
			content.append(importCon.toString());
			String annotationCon = "@Getter\n@Setter\n";
			content.append(annotationCon);
			content.append(className);
			content.append(classCon.toString()+"\n");
			content.append(gettersCon.toString());
			content.append(settersCon.toString());
			content.append("}");
			FileUtil.createFileAtPath(path + "/", fileName + ".java", content.toString());
			return true;
		}
		return false;
	}


}
