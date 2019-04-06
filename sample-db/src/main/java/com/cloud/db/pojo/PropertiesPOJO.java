/*
 * 文件名：PropertiesPOJO.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： PropertiesPOJO.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年4月6日
 * 修改内容：新增
 */
package com.cloud.db.pojo;
/**
 * @Title:  PropertiesPOJO.java
 * @Package: com.dm.db.pojo.PropertiesPOJO.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年4月6日 下午2:10:32
 */
public class PropertiesPOJO {

	private int id;	//
	private String akey;	//
	private String avalue;	//
	private String application;	//
	private String aprofile;	//
	private String label;	//
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAkey() {
		return akey;
	}
	public void setAkey(String akey) {
		this.akey = akey;
	}
	public String getAvalue() {
		return avalue;
	}
	public void setAvalue(String avalue) {
		this.avalue = avalue;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getAprofile() {
		return aprofile;
	}
	public void setAprofile(String aprofile) {
		this.aprofile = aprofile;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	@Override
	public String toString() {
		return "PropertiesPOJO [id=" + id + ", akey=" + akey + ", avalue=" + avalue + ", application=" + application
				+ ", aprofile=" + aprofile + ", label=" + label + "]";
	}
}
