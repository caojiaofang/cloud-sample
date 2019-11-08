package com.cloud.db.pojo;


public class SampleMenuPOJO{

	private String menuId;	//菜单编号
	private String menuNm;	//菜单名称
	private String menuUrl;	//菜单url
	private String menuOdr;	//菜单序号
	private String parentMenuId;	//父菜单编号
	private String createUserAcct;	//创建用户
	private String istTm;	//入表时间
	private String updTm;	//更新时间
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getMenuNm() {
		return menuNm;
	}
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getMenuOdr() {
		return menuOdr;
	}
	public void setMenuOdr(String menuOdr) {
		this.menuOdr = menuOdr;
	}
	public String getParentMenuId() {
		return parentMenuId;
	}
	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}
	public String getCreateUserAcct() {
		return createUserAcct;
	}
	public void setCreateUserAcct(String createUserAcct) {
		this.createUserAcct = createUserAcct;
	}
	public String getIstTm() {
		return istTm;
	}
	public void setIstTm(String istTm) {
		this.istTm = istTm;
	}
	public String getUpdTm() {
		return updTm;
	}
	public void setUpdTm(String updTm) {
		this.updTm = updTm;
	}
	@Override
	public String toString() {
		return "SampleMenuPOJO [menuId=" + menuId + ", menuNm=" + menuNm + ", menuUrl=" + menuUrl + ", menuOdr="
				+ menuOdr + ", parentMenuId=" + parentMenuId + ", createUserAcct=" + createUserAcct + ", istTm=" + istTm
				+ ", updTm=" + updTm + "]";
	}

}