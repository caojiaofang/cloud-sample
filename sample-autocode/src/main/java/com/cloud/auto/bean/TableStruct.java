package com.cloud.auto.bean;

import java.util.List;

/**
 * 数据表的表结构
 * 
 * @author
 */
@SuppressWarnings("all")
public class TableStruct {

	private String tableName;// 表名
	private List Columns;// 所有的列
	private List pKeyColums;//所有的主键
	
	private String doName;
	private String daoName;
	
	private String beanName;
	

	public String getBeanName() {
		String[] split = this.tableName.split("_");
		StringBuffer sb = new StringBuffer();
		for (int i = 0 ; i < split.length ; i++ ) {
			sb.append(split[i].substring(0, 1).toUpperCase()+split[i].substring(1));
		}
		return sb.toString();
	}
	
	
	public String getDaoName() {
		return getBeanName() + "Dao";
	}
	
	public String getDoName() {
		return getBeanName() + "DO";
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List getColumns() {
		return Columns;
	}

	public void setColumns(List columns) {
		Columns = columns;
	}

	public List getpKeyColums() {
		return pKeyColums;
	}

	public void setpKeyColums(List pKeyColums) {
		this.pKeyColums = pKeyColums;
	}

	public TableStruct(String tableName, List columns) {
		super();
		this.tableName = tableName;
		Columns = columns;
	}

	public TableStruct() {
		super();
	}
	@Override
	public String toString() {
		return "TableStruct [tableName=" + tableName + ", Columns=" + Columns
				+ "]";
	}
}