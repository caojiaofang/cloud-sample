package com.cloud.auto.dao.impl;

import java.util.List;

import com.cloud.auto.bean.ColumnStruct;
import com.cloud.auto.bean.TableStruct;
import com.cloud.auto.dao.GetTablesDao;
import com.cloud.auto.dao.MapperXmlAutoDao;
import com.cloud.auto.util.ConfigUtil;
import com.cloud.auto.util.DataTypeUtil;
import com.cloud.auto.util.FileUtil;
import com.cloud.auto.util.JdbcTypeUtil;
import com.cloud.auto.util.NameUtil;
import com.cloud.auto.util.ParamTypeUtil;


/**
 * 生成Mapper.xml的dao层实现类
 * 
 * @author
 */
@SuppressWarnings("all")
public class MapperXmlAutoDaoImpl implements MapperXmlAutoDao {

	// 从GetTablesDaoImpl中获得装有所有表结构的List
	GetTablesDao getTables = new GetTablesDaoImpl();
	List<TableStruct> list = getTables.getTablesStruct();

	// 通过表名、字段名称、字段类型创建Mapper.xml
	@Override
	public boolean createMapperXml() {
		// 获得配置文件的参数
		// 项目路径
		String projectPath = ConfigUtil.projectPath;
		// 是否生成Mapper.xml
		String mapperXmlFalg = ConfigUtil.mapperXmlFlag;
		// Mapper.xml的包名
		String mapperXmlPackage = ConfigUtil.mapperXmlPackage;
		// Bean实体类的包名
		String beanPackage = ConfigUtil.beanPackage;
		// Dao接口的包名
		String daoPackage = ConfigUtil.daoPackage;
		if ("true".equals(mapperXmlFalg) && list.size() > 0) {
			// 将包名com.xxx.xxx形式，替换成com/xxx/xxx形成
			String mapperXmlPath = mapperXmlPackage.replace(".", "/");
			// Mapper.xml的路径
			String path = projectPath + "/src/" + mapperXmlPath;
			//类名称
			String interfaceName[] = ConfigUtil.interfaceName.split(";");
			//类名称
			//String classNames[] = ConfigUtil.className.split(";");
			// 遍历装有所有表结构的List
			for (int i = 0; i < list.size(); i++) {
				// 表名
				String tableName = list.get(i).getTableName();

				// 文件名
				String fileName = "mapper-" + NameUtil.fileName(tableName) ;
				String beanName = NameUtil.fileName(tableName) + "DO";
				String daoName = NameUtil.fileName(tableName) + "Dao";
				// 获得每个表的所有主键的列结构
				//List<ColumnStruct> keyColumns = list.get(i).getpKeyColums();
				List<ColumnStruct> columns = list.get(i).getColumns();
				StringBuffer idTypeStringBuff = null;
				boolean multiPKeyFlg = columns.size()>1;
				String idParamType = null;
				
				
				for(int k=0;k<columns.size();k++){
					ColumnStruct columnStruct = columns.get(k);
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
					String jdbcType = JdbcTypeUtil.getJdbcType(dateType);
					if(idTypeStringBuff==null){
						idTypeStringBuff = new StringBuffer(columnStruct.getColumnName()+ "= #{" + columnName +  ",jdbcType=" + jdbcType + "}");
					}else{
						idTypeStringBuff = idTypeStringBuff.append(" and "+ columnStruct.getColumnName()+ "= #{" + columnName +  ",jdbcType=" + jdbcType + "}");
						if(i%3==0){
							idTypeStringBuff.append("\n");
						}
					}
					if(!multiPKeyFlg){
						idParamType = ParamTypeUtil.getParamType(dateType);
					}
				}
				String idTypeString = idTypeStringBuff.toString();

				// (Mapper.xml）文件内容
				StringBuffer headCon = new StringBuffer();
				headCon.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
				headCon.append(
						"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
				headCon.append("<mapper namespace=\"" + daoPackage + "."
						+ daoName + "\">\n");

				StringBuffer resultMapCon = new StringBuffer();
				resultMapCon
						.append("\t" + "<resultMap id=\"baseResultMap\" type=\""
								+ beanPackage + "." + beanName + "\">\n");

				StringBuffer baseColCon = new StringBuffer();
				baseColCon.append("\t" + "<sql id=\"baseColumnList\">\n");

				StringBuffer insertRecordCon = new StringBuffer();
				insertRecordCon.append(
						"\t" + "<insert id=\"insertRecord\" parameterType=\""
								+ beanPackage + "." + beanName + "\">\n");
				insertRecordCon.append("\t\t" + "insert into " + tableName + "(");

				StringBuffer insertRecordCons = new StringBuffer();
				insertRecordCons.append("\t\t" + "values (");

				
				StringBuffer delByIdCon = new StringBuffer();
				//复核主键不需要parameterType
				if(multiPKeyFlg){
					delByIdCon.append(
							"\t" + "<delete id=\"deleteById\" >\n");
				}else{
					delByIdCon.append(
							"\t" + "<delete id=\"deleteById\" parameterType=\""
									+ idParamType + "\">\n");
				}
				
				/*delByIdCon.append("\t\t" + "delete from " + tableName
						+ " where " + IdName + "= #{" + beanIdName
						+ ",jdbcType=" + IdJdbcType + "}\n");*/
				delByIdCon.append("\t\t" + "delete from " + tableName
						+ " where " + idTypeString
						+ "\n");
				delByIdCon.append("\t" + "</delete>\n");

				StringBuffer updateByIdSelCon = new StringBuffer();
				updateByIdSelCon.append(
						"\t" + "<update id=\"updateByIdSelective\" parameterType=\""
								+ beanPackage + "." + beanName + "\">\n");
				updateByIdSelCon.append("\t\t" + "update " + tableName + "\n"
						+ "\t\t" + "<set>\n");

				StringBuffer updateByIdCon = new StringBuffer();
				updateByIdCon.append(
						"\t" + "<update id=\"updateById\" parameterType=\""
								+ beanPackage + "." + beanName + "\">\n");
				updateByIdCon.append("\t\t" + "update " + tableName + " set\n");

				
				StringBuffer selectSel = new StringBuffer();
				
				selectSel.append(
						"\t" + "<select id=\"selectAll\" parameterType=\""
								+ beanPackage + "." + beanName
								+ "\" resultMap=\"baseResultMap\">\n");
				selectSel.append("\t\t" + "select <include refid=\"baseColumnList\"/> from " + tableName
						+ " where 1=1\n");
				StringBuffer selectMap = new StringBuffer();
				selectMap.append(
						"\t" + "<select id=\"selectByMap\" parameterType=\"Map\" resultMap=\"baseResultMap\">\n");
				selectMap.append("\t\t" + "select <include refid=\"baseColumnList\"/> from " + tableName
						+ " where 1=1\n");

				StringBuffer selectByIdCon = new StringBuffer();
				
				//复核主键不需要parameterType
				if(multiPKeyFlg){
					selectByIdCon.append("\t"+ "<select id=\"selectById\" resultMap=\"baseResultMap\">\n");
				}else{
					selectByIdCon.append("\t"+ "<select id=\"selectById\" parameterType=\""
									+ idParamType + "\" resultMap=\"BaseResultMap\">\n");
				}

				selectByIdCon.append("\t\t" + "select\n" + "\t\t"
						+ "<include refid=\"baseColumnList\"/>\n");
				selectByIdCon.append("\t\t" + "from " + tableName + "\n"
						+ "\t\t" + "where " + idTypeString + "\n");
				selectByIdCon.append("\t" + "</select>\n");


				// 遍历List，将字段名称和字段类型、属性名写进文件
				for (int j = 0; j < columns.size(); j++) {
					// 字段名
					String columnName = columns.get(j).getColumnName();
					// 属性（变量）名
					String attrName = NameUtil
							.columnName(columns.get(j).getColumnName());
					// 字段类型
					String type = DataTypeUtil
							.sqlType2JavaType(columns.get(j));
					;
					String jdbcType = JdbcTypeUtil.getJdbcType(type);
					if (jdbcType == "INT" || "INT".equals(jdbcType)) {
						jdbcType = "INTEGER";
					}
					if (j == 0) {
						/*resultMapCon.append("\t\t" + "<id column=\""
								+ columnName + "\" property=\"" + attrName
								+ "\" jdbcType=\"" + jdbcType + "\"/>\n");*/
						resultMapCon.append("\t\t" + "<id column=\""
								+ columnName + "\" property=\"" + attrName
								+ "\"/>\n");
						baseColCon.append("\t\t" + columnName);
						insertRecordCon.append(columnName);
						insertRecordCons.append("#{" + attrName + ",jdbcType=" + jdbcType + "}");
//						insertRecordCons.append("#{" + attrName + "}");
					} else {
						resultMapCon.append("\t\t" + "<result column=\""
								+ columnName + "\" property=\"" + attrName
								+ "\" jdbcType=\"" + jdbcType + "\"/>\n");
						/*resultMapCon.append("\t\t" + "<result column=\""
								+ columnName + "\" property=\"" + attrName
								+ "\"/>\n");*/
						baseColCon.append("," + columnName);
						insertRecordCon.append(",\n" + "\t\t\t" + columnName);
						insertRecordCons.append(",\n" + "\t\t\t" + "#{"
								+ attrName + ",jdbcType=" + jdbcType + "}");
						updateByIdSelCon.append("\t\t\t" + "<if test=\""
								+ attrName + " != null\" >\n" + "\t\t\t\t"
								+ columnName + "=" + "#{" + attrName
								+ ",jdbcType=" + jdbcType + "},\n" + "\t\t\t"
								+ "</if>\n");
						/*insertRecordCons.append(",\n" + "\t\t\t" + "#{"
								+ attrName + "}");
						updateByIdSelCon.append("\t\t\t" + "<if test=\""
								+ attrName + " != null\" >\n" + "\t\t\t\t"
								+ columnName + "=" + "#{" + attrName
								+ "},\n" + "\t\t\t"
								+ "</if>\n");*/

						if (j == columns.size() - 1) {
							updateByIdCon.append("\t\t\t" + columnName + "="
									+ "#{" + attrName + ",jdbcType=" + jdbcType
									+ "}\n");
							/*updateByIdCon.append("\t\t\t" + columnName + "="
									+ "#{" + attrName + "}\n");*/
						} else {
							/*updateByIdCon.append("\t\t\t" + columnName + "="
									+ "#{" + attrName + "},\n");*/
							updateByIdCon.append("\t\t\t" + columnName + "="
									+ "#{" + attrName + ",jdbcType=" + jdbcType
									+ "},\n");
						}
					}
					
					/*countSelCon.append("\t\t" + "<if test=\"" + attrName
							+ " != null\" >\n" + "\t\t\t" + "and " + columnName
							+ "=" + "#{" + attrName + ",jdbcType=" + jdbcType
							+ "}\n" + "\t\t" + "</if>\n");*/
					
					/*selectSel.append("\t\t" + "<if test=\"" + attrName + " != null and " + attrName + " != ''\" >\n" + "\t\t\t" + "and " + columnName
							+ "=" + "#{" + attrName + "}\n" + "\t\t" + "</if>\n");
					selectMap.append("\t\t" + "<if test=\"" + attrName + " != null and " + attrName + " != ''\" >\n" + "\t\t\t" + "and " + columnName
							+ "=" + "#{" + attrName + "}\n" + "\t\t" + "</if>\n");*/
					
					selectSel.append("\t\t" + "<if test=\"" + attrName + " != null and " + attrName + " != ''\" >\n" + "\t\t\t" + "and " + columnName
							+ "=" + "#{" + attrName + ",jdbcType=" + jdbcType + "}\n" + "\t\t" + "</if>\n");
					selectMap.append("\t\t" + "<if test=\"" + attrName + " != null and " + attrName + " != ''\" >\n" + "\t\t\t" + "and " + columnName
							+ "=" + "#{" + attrName + ",jdbcType=" + jdbcType + "}\n" + "\t\t" + "</if>\n");

				}
				resultMapCon.append("\t" + "</resultMap>\n");
				baseColCon.append("\n\t" + "</sql>\n");
				insertRecordCon.append(")\n");
				insertRecordCons.append(")\n" + " " + "</insert>\n");
				
				updateByIdSelCon.append("\t\t" + "</set>\n" + "\t\t" + "where "
						+ idTypeString + "\n" + "\t" + "</update>\n");
				updateByIdCon.append("\t\t" + "where " + idTypeString  + "\t"
						+ "</update>\n");
				/*updateByIdSelCon.append("\t\t" + "</set>\n" + "\t\t" + "where "
						+ IdName + "= #{" + beanIdName + ",jdbcType="
						+ IdJdbcType + "}\n" + "\t" + "</update>\n");
				updateByIdCon.append("\t\t" + "where " + IdName + "= #{"
						+ beanIdName + ",jdbcType=" + IdJdbcType + "}\n" + "\t"
						+ "</update>\n");*/
				selectSel.append("\t" + "</select>\n");
				selectMap.append("\t" + "</select>\n");

				// 拼接(Mapper.xml）文件内容
				StringBuffer content = new StringBuffer();
				content.append(headCon);
				content.append(resultMapCon);
				content.append(baseColCon);
				content.append(insertRecordCon);
				content.append(insertRecordCons);
				content.append(delByIdCon);
				content.append(updateByIdSelCon);
				content.append(updateByIdCon);
				content.append(selectByIdCon);
				content.append(selectSel);
				content.append(selectMap);
				content.append("</mapper>");

				FileUtil.createFileAtPath(path + "/", fileName + ".xml",
						content.toString());
			}
			return true;
		}
		return false;
	}

}