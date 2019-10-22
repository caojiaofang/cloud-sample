/*
 * 文件名：WebContextPOJO.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： WebContextPOJO.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年6月5日
 * 修改内容：新增
 */
package com.cloud.context;

import java.util.List;
import java.util.Map;

import com.cloud.db.pojo.MenuLevelPOJO;
import com.cloud.db.pojo.OrganPOJO;
import com.cloud.db.pojo.UserPOJO;

/**
 * @Title:  WebContextPOJO.java
 * @Package: com.cloud.context.WebContextPOJO.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年6月5日 下午3:17:19
 */
public class WebContextPOJO {

	private String userAcct; // 用户账号

	private String userNm; // 用户名称

	private String loginTime; // 登录时间

	private UserPOJO userPOJO;

	private List<OrganPOJO> organPOJOs;

	private List<MenuLevelPOJO> menus;

	private String key;

	private Map<String, List<MenuLevelPOJO>> payChlMenus;

	public String getUserAcct() {
		return userAcct;
	}

	public void setUserAcct(String userAcct) {
		this.userAcct = userAcct;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public UserPOJO getUserPOJO() {
		return userPOJO;
	}

	public void setUserPOJO(UserPOJO userPOJO) {
		this.userPOJO = userPOJO;
	}

	public List<OrganPOJO> getOrganPOJOs() {
		return organPOJOs;
	}

	public void setOrganPOJOs(List<OrganPOJO> organPOJOs) {
		this.organPOJOs = organPOJOs;
	}

	public List<MenuLevelPOJO> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuLevelPOJO> menus) {
		this.menus = menus;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map<String, List<MenuLevelPOJO>> getPayChlMenus() {
		return payChlMenus;
	}

	public void setPayChlMenus(Map<String, List<MenuLevelPOJO>> payChlMenus) {
		this.payChlMenus = payChlMenus;
	}

	@Override
	public String toString() {
		return "WebContextPOJO [userAcct=" + userAcct + ", userNm=" + userNm + ", loginTime=" + loginTime
				+ ", userPOJO=" + userPOJO + ", organPOJOs=" + organPOJOs + ", menus=" + menus + ", key=" + key
				+ ", payChlMenus=" + payChlMenus + "]";
	}
}
