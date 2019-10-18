/*
 * 文件名：MenuPOJO.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： MenuPOJO.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年6月5日
 * 修改内容：新增
 */
package com.cloud.db.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title:  MenuPOJO.java
 * @Package: com.cloud.web.menu.pojo.MenuPOJO.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年6月5日 下午3:23:57
 */
public class MenuLevelPOJO {
	
	private String menuId;

	private String menuNm;

	private String menuUrl;

	private Integer menuOdr;

	private String buttonIds;

	private List<MenuLevelPOJO> childMenu = new ArrayList<>();

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

	public Integer getMenuOdr() {
		return menuOdr;
	}

	public void setMenuOdr(Integer menuOdr) {
		this.menuOdr = menuOdr;
	}

	public String getButtonIds() {
		return buttonIds;
	}

	public void setButtonIds(String buttonIds) {
		this.buttonIds = buttonIds;
	}

	public List<MenuLevelPOJO> getChildMenu() {
		return childMenu;
	}

	public void setChildMenu(List<MenuLevelPOJO> childMenu) {
		this.childMenu = childMenu;
	}

	@Override
	public String toString() {
		return "MenuLevelPOJO [menuId=" + menuId + ", menuNm=" + menuNm + ", menuUrl=" + menuUrl + ", menuOdr="
				+ menuOdr + ", buttonIds=" + buttonIds + ", childMenu=" + childMenu + "]";
	}
}
