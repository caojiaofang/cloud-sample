package com.cloud.auto.util;

import org.apache.commons.lang3.StringUtils;

import com.cloud.auto.bean.ColumnStruct;

/**
 * 数据类型转换工具类
 */
public class DataTypeUtil {

	public static String getType(String dataType) {
		String type = "";

		if ("tinyint".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "Byte";
		}
		if ("smallint".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "Short";
		}
		if ("mediumint".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "Integer";
		}
		if ("int".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "Integer";
		}
		if ("integer".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "Integer";
		}
		if ("bigint".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "Long";
		}
		if ("bit".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "Boolean";
		}
		if ("double".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "Double";
		}
		if ("float".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "Float";
		}
		if ("decimal".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "Long";
		}
		if ("char".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "String";
		}
		if ("varchar".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "String";
		}
		if ("date".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "Date";
		}
		if ("time".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "Date";
		}
		if ("year".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "Date";
		}
		if ("timestamp".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "Timestamp";
		}
		if ("datetime".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "Timestamp";
		}
		if ("tinytext".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "String";
		}
		if ("enum".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "String";
		}
		if ("set".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "String";
		}
		if ("text".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "String";
		}
		if ("mediumtext".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "String";
		}
		if ("longtext".equals(StringUtils.substringBefore(dataType, "(").toLowerCase())) {
			type = "String";
		}
		return type;
	}
	public static String sqlType2JavaType(String sqlType , int colSize , int colScale) {

        if (sqlType.equalsIgnoreCase("bit") || sqlType.equalsIgnoreCase("boolean")) {
            return "Boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "Byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "Short";
        } else if (sqlType.equalsIgnoreCase("int") || sqlType.equalsIgnoreCase("integer")
                || (sqlType.equalsIgnoreCase("decimal") && colSize < 11 && 0 >= colScale)
                || (sqlType.equalsIgnoreCase("number") && colSize < 11 && 0 >= colScale)) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("bigint") || (sqlType.equalsIgnoreCase("number") && 0 >= colScale)
                || (sqlType.equalsIgnoreCase("decimal") && 0 >= colScale)) {
            return "Long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "Float";
        } else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("number")) {
            return "BigDecimal";
        } else if (sqlType.equalsIgnoreCase("money") || sqlType.equalsIgnoreCase("smallmoney")) {
            return "Double";
        } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("varchar2")
                || sqlType.equalsIgnoreCase("char") || sqlType.equalsIgnoreCase("nvarchar")
                || sqlType.equalsIgnoreCase("nchar") || sqlType.equalsIgnoreCase("NVARCHAR2")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("timestamp")
                || sqlType.equalsIgnoreCase("date") || sqlType.equalsIgnoreCase("time")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("image")) {
            return "Blob";
        } else if (sqlType.equalsIgnoreCase("text")) {
            return "Clob";
        } else if (sqlType.equalsIgnoreCase("Blob") ) {
			return "Blob";
		} else if (sqlType.equalsIgnoreCase("Clob")) {
			return "Clob";
		}
        return null;
    }
	
	public static String sqlType2JavaType(ColumnStruct columnStruct) {

        
        return sqlType2JavaType(columnStruct.getDataType(),columnStruct.getColSize(),columnStruct.getColScale());
    }

}