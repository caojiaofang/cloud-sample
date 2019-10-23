package com.cloud.auto.util;

/**
 * 项目名称：autoGenerate
 * 类描述：mybatis的parameterType处理工具类
 * 创建者：longhuaiyu
 * 创建时间：2018年6月12日下午2:32:32
 * 版本号：V1.0
 */
public class ParamTypeUtil {

	public static String getParamType(String dataType) {
		String type = "";
		if ("String".equalsIgnoreCase(dataType)) {
			type = "java.lang.String";
		}
		if ("BigDecimal".equalsIgnoreCase(dataType)) {
			type = "java.math.BigDecimal";
		}
		if ("boolean".equalsIgnoreCase(dataType)) {
			type = "java.lang.Boolean";
		}
		if ("byte".equalsIgnoreCase(dataType)) {
			type = "java.lang.Byte";
		}
		if ("short".equalsIgnoreCase(dataType)) {
			type = "java.lang.Short";
		}
		if ("int".equalsIgnoreCase(dataType)) {
			type = "java.lang.Integer";
		}
		if ("Integer".equalsIgnoreCase(dataType)) {
			type = "java.lang.Integer";
		}
		if ("long".equalsIgnoreCase(dataType)) {
			type = "java.lang.Long";
		}
		if ("float".equalsIgnoreCase(dataType)) {
			type = "java.lang.Double";
		}
		if ("double".equalsIgnoreCase(dataType)) {
			type = "java.lang.Double";
		}
		if ("Date".equalsIgnoreCase(dataType)) {
			type = "java.util.Date";
		}
		if ("Time".equalsIgnoreCase(dataType)) {
			type = "java.sql.Time";
		}
		if ("Timestamp".equalsIgnoreCase(dataType)) {
			type = "java.sql.Timestamp";
		}
		if ("List".equalsIgnoreCase(dataType)) {
			type = "java.util.List";
		}
		if ("Map".equalsIgnoreCase(dataType)) {
			type = "java.util.Map";
		}
		return type;
	}
}