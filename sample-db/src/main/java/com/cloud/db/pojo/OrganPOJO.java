/*
 * 文件名：OrganPOJO.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： OrganPOJO.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年6月5日
 * 修改内容：新增
 */
package com.cloud.db.pojo;

import java.util.Date;

/**
 * @Title:  OrganPOJO.java
 * @Package: com.cloud.web.organ.pojo.OrganPOJO.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年6月5日 下午3:21:19
 */
public class OrganPOJO {

	private String organId;	//机构编号
	private String organNm;	//机构名称
	private Integer organOdr;	//机构序号
	private String bkOrganId;	//行内机构号
	private String bkOrganNm;	//行内机构名称
	private String parentOrganId;	//父机构ID
	private Date stopTm;	//停用时间
	private String state;	//状态
	private String createUserAcct;	//创建用户
	private Date istTm;	//入表时间
	private Date updTm;	//更新时间
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getOrganNm() {
		return organNm;
	}
	public void setOrganNm(String organNm) {
		this.organNm = organNm;
	}
	public Integer getOrganOdr() {
		return organOdr;
	}
	public void setOrganOdr(Integer organOdr) {
		this.organOdr = organOdr;
	}
	public String getBkOrganId() {
		return bkOrganId;
	}
	public void setBkOrganId(String bkOrganId) {
		this.bkOrganId = bkOrganId;
	}
	public String getBkOrganNm() {
		return bkOrganNm;
	}
	public void setBkOrganNm(String bkOrganNm) {
		this.bkOrganNm = bkOrganNm;
	}
	public String getParentOrganId() {
		return parentOrganId;
	}
	public void setParentOrganId(String parentOrganId) {
		this.parentOrganId = parentOrganId;
	}
	public Date getStopTm() {
		return stopTm;
	}
	public void setStopTm(Date stopTm) {
		this.stopTm = stopTm;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreateUserAcct() {
		return createUserAcct;
	}
	public void setCreateUserAcct(String createUserAcct) {
		this.createUserAcct = createUserAcct;
	}
	public Date getIstTm() {
		return istTm;
	}
	public void setIstTm(Date istTm) {
		this.istTm = istTm;
	}
	public Date getUpdTm() {
		return updTm;
	}
	public void setUpdTm(Date updTm) {
		this.updTm = updTm;
	}
	@Override
	public String toString() {
		return "OrganPOJO [organId=" + organId + ", organNm=" + organNm + ", organOdr=" + organOdr + ", bkOrganId="
				+ bkOrganId + ", bkOrganNm=" + bkOrganNm + ", parentOrganId=" + parentOrganId + ", stopTm=" + stopTm
				+ ", state=" + state + ", createUserAcct=" + createUserAcct + ", istTm=" + istTm + ", updTm=" + updTm
				+ "]";
	}
}
