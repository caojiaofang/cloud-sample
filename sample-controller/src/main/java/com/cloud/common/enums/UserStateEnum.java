/*
 * 文件名：UserStateEnum.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： UserStateEnum.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年10月24日
 * 修改内容：新增
 */
package com.cloud.common.enums;
/**
 * @Title:  UserStateEnum.java
 * @Package: com.cloud.common.enums.UserStateEnum.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年10月24日 下午3:23:57
 */
public enum UserStateEnum {

	/**
	 * 正常
	 */
	NORMAL("正常", "1"),
	/**
	 * 冻结
	 */
	LOCK("冻结", "2"),
	/**
	 * 密码过期
	 */
	OUTDATE("密码过期", "3"),
	/**
	 * 停用
	 */
	STOP("停用", "4"),
	/**
	 * 删除
	 */
	DEL("删除", "5"),
	;

	/**
	 * 名称s
	 */
	private String enName;
	/**
	 * 值
	 */
	private String enValue;
	
	private UserStateEnum(String enName,String enValue){
		this.enName = enName;
		this.enValue = enValue;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getEnValue() {
		return enValue;
	}

	public void setEnValue(String enValue) {
		this.enValue = enValue;
	}
	
	public String toString(){
		return this.enValue;
	}
}
