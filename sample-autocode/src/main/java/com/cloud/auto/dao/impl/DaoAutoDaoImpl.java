package com.cloud.auto.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.auto.bean.ColumnStruct;
import com.cloud.auto.bean.TableStruct;
import com.cloud.auto.dao.DaoAutoDao;
import com.cloud.auto.dao.GetTablesDao;
import com.cloud.auto.util.ConfigUtil;
import com.cloud.auto.util.DataTypeUtil;
import com.cloud.auto.util.FileUtil;
import com.cloud.auto.util.NameUtil;


/**
 * 生成Dao接口的dao层实现类
 * 
 * @author
 */
@SuppressWarnings("all")
public class DaoAutoDaoImpl implements DaoAutoDao {

	// 从GetTablesDaoImpl中获得装有所有表结构的List
	GetTablesDao getTables = new GetTablesDaoImpl();
	List<TableStruct> list = getTables.getTablesStruct();

	// 通过表名、字段名称、字段类型创建Dao接口
	@Override
	public boolean createDao() {
		// 获得配置文件的参数
		// 项目路径
		String projectPath = ConfigUtil.projectPath;
		// 是否生成Dao
		String daoFalg = ConfigUtil.daoFlag;
		// Dao接口的包名
		String daoPackage = ConfigUtil.daoPackage;
		// Bean实体类的包名
		String beanPackage = ConfigUtil.beanPackage;
		
		boolean isSysTable = ConfigUtil.userName.toLowerCase().endsWith("sys");
		
		if ("true".equals(daoFalg) && list.size() > 0) {
			// 将包名com.xxx.xxx形式，替换成com/xxx/xxx形成
			String daoPath = daoPackage.replace(".", "/");
			// Dao接口的路径
			String path = projectPath + "/src/" + daoPath;
			//类名称
			String interfaceName[] = ConfigUtil.interfaceName.split(";");
			//类名称
			//String classNames[] = ConfigUtil.className.split(";");
			// 遍历装有所有表结构的List
			for (int i = 0; i < list.size(); i++) {
				// 文件名
				String fileName = NameUtil.fileName(list.get(i).getTableName()) + "Dao";
				String beanName = NameUtil.fileName(list.get(i).getTableName()) + "DO";
				String paramTypeName = "Map<String,Object>";
				Map<String,String> dataTypeMap = new HashMap<String,String>();
				// 获得每个表的所有主键的列结构
				//List<ColumnStruct> columns = list.get(i).getpKeyColums();
				List<ColumnStruct> columns = list.get(i).getColumns();
				StringBuffer idTypeStringBuff = null;
				boolean multiPKeyFlg = columns.size()>1;
				for(ColumnStruct columnStruct:columns){
					// 变量名（属性名）
					String columnName = NameUtil
							.columnName(columnStruct.getColumnName());
					// 获得数据类型
					String type = columnStruct.getDataType();
					int colSize = columnStruct.getColSize();
					int colScale = columnStruct.getColScale();
					// 将mysql数据类型转换为java数据类型
					//String dateType = DataTypeUtil.getType(type);
					String dateType = DataTypeUtil.sqlType2JavaType(type, colSize, colScale);
					dataTypeMap.put(dateType, dateType);
					if(idTypeStringBuff==null){
						if(multiPKeyFlg){
							idTypeStringBuff = new StringBuffer("@Param(\""+columnName+"\")" + dateType +" " + columnName);
						}else{
							idTypeStringBuff = new StringBuffer(dateType +" " + columnName);
						}
					}else{
						idTypeStringBuff = idTypeStringBuff.append(",@Param(\""+columnName+"\")" + dateType +" " + columnName);
					}
				}
				String idTypeString = idTypeStringBuff.toString();

				// (Dao接口）文件内容
				String packageCon = "package " + daoPackage + ";\n\n";
				StringBuffer importCon = new StringBuffer();
				
				String className = "public interface " + fileName + "{\n\n";
				
				//注解
				String annotationCon = isSysTable?"@SysMapper\n":"@Mapper\n";
				
				StringBuffer classCon = new StringBuffer();

				// 生成导包内容
				importCon.append("import" + "\t" + beanPackage + "." + beanName
						+ ";\n\n");
				// 有date类型的数据需导包
				if (dataTypeMap.containsKey("Date")) {
					importCon.append("import java.util.Date;\n\n");
				}
				// 有Timestamp类型的数据需导包
				if (dataTypeMap.containsKey("Timestamp")) {
					importCon.append("import java.sql.Timestamp;\n\n");
				}
				importCon.append("import java.util.List;\n");
				importCon.append("import java.util.Map;\n\n");
				
				if(multiPKeyFlg){
					importCon.append("import org.apache.ibatis.annotations.Param;\n\n");	
				}
				
				if(isSysTable){
					importCon.append("import com.cloud.mybatis.config.SysMapper;;\n\n");
				}else{
					importCon.append("import org.apache.ibatis.annotations.Mapper;\n\n");
				}
				importCon.append("import com.cloud.mybatis.interceptor.PageBounds;\n\n");

				// 生成接口方法
				classCon.append("\t" + "public int insertRecord(" + beanName
						+ " record);//添加一条完整记录\n\n");
				classCon.append("\t" + "public int deleteById(" + idTypeString + ");//通过Id(主键)删除一条记录\n\n");
				classCon.append("\t" + "public int updateByIdSelective("
						+ beanName + " record);//按Id(主键)修改指定列的值\n\n");
				classCon.append("\t" + "public int updateById(" + beanName
						+ " record);//按Id(主键)修改所有列的值\n\n");
				classCon.append("\t" + "public" + " " + beanName + " " + "selectById(" + idTypeString + ");//通过Id(主键)查询一条记录\n\n");
				classCon.append("\t" + "public List<"+ beanName + "> selectAll(" + beanName + " record,PageBounds pageBounds);//根据条件查询\n\n");
				classCon.append("\t" + "public List<"+ beanName + "> selectByMap(Map<String,Object> paramMap,PageBounds pageBounds);//根据条件查询\n\n");
				// 拼接(Dao接口）文件内容
				StringBuffer content = new StringBuffer();
				content.append(packageCon);
				content.append(importCon.toString());

				content.append(annotationCon);
				
				content.append(className);
				content.append(classCon.toString());
				content.append("\n}");
				FileUtil.createFileAtPath(path + "/", fileName + ".java",
						content.toString());
			}
			return true;
		}
		return false;
	}

}