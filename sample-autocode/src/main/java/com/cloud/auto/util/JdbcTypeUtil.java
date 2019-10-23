package com.cloud.auto.util;

/**
 * 项目名称：autoGenerate
 * 类描述：mybatis的JdbcType处理工具类
 * 创建者：longhuaiyu
 * 创建时间：2018年6月12日下午2:32:13
 * 版本号：V1.0
 */
public class JdbcTypeUtil {

	public static String getJdbcType(String dataType) {
		String type = "";
		if ("String".equalsIgnoreCase(dataType)) {
			type = "VARCHAR";
		}
		if ("BigDecimal".equalsIgnoreCase(dataType)) {
			type = "NUMERIC";
		}
		if ("boolean".equalsIgnoreCase(dataType)) {
			type = "BOOLEAN";
		}
		if ("byte".equalsIgnoreCase(dataType)) {
			type = "TINYINT";
		}
		if ("short".equalsIgnoreCase(dataType)) {
			type = "SMALLINT";
		}
		if ("int".equalsIgnoreCase(dataType)) {
			type = "INTEGER";
		}
		if ("Integer".equalsIgnoreCase(dataType)) {
			type = "INTEGER";
		}
		if ("long".equalsIgnoreCase(dataType)) {
			type = "BIGINT";
		}
		if ("float".equalsIgnoreCase(dataType)) {
			type = "DOUBLE";
		}
		if ("double".equalsIgnoreCase(dataType)) {
			type = "DOUBLE";
		}
		if ("Date".equalsIgnoreCase(dataType)) {
			type = "DATE";
		}
		if ("Time".equalsIgnoreCase(dataType)) {
			type = "TIME";
		}
		if ("Timestamp".equalsIgnoreCase(dataType)) {
			type = "TIMESTAMP";
		}
		return type;
	}
}