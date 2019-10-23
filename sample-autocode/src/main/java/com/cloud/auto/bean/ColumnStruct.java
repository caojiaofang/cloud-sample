package com.cloud.auto.bean;

/**
 * 数据字段实体类
 * 
 * @author
 */
public class ColumnStruct {

	private String columnName;// 列名
	private String dataType;// 数据类型
	private String culumnExplain;//字段注释
	private int colSize; //字段长度
	private int colScale; //字段位置
	
	

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public int getColSize() {
		return colSize;
	}

	public void setColSize(int colSize) {
		this.colSize = colSize;
	}

	public int getColScale() {
		return colScale;
	}

	public void setColScale(int colScale) {
		this.colScale = colScale;
	}
	
	public ColumnStruct(String columnName, String dataType,String culumnExplain,int colScale,int colSize) {
		super();
		this.columnName = columnName;
		this.dataType = dataType;
		this.culumnExplain = culumnExplain;
		this.colScale = colScale;
		this.colSize = colSize;
	}

	public ColumnStruct() {
		super();
	}

	public String getCulumnExplain() {
		return culumnExplain;
	}

	public void setCulumnExplain(String culumnExplain) {
		this.culumnExplain = culumnExplain;
	}

	@Override
	public String toString() {
		return "ColumnStruct [columnName=" + columnName + ", dataType="
				+ dataType + ", culumnExplain="+ culumnExplain +", colScale="+colScale+", colSize="+colSize+"]";
	}

	
}