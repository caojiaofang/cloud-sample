/*
 * 文件名：UserPOJO.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： UserPOJO.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年6月5日
 * 修改内容：新增
 */
package com.cloud.db.pojo;

import java.util.Date;

/**
 * @Title:  UserPOJO.java
 * @Package: com.cloud.web.user.pojo.UserPOJO.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年6月5日 下午3:18:47
 */
public class UserPOJO {

	private String userAcct;	//用户帐号
	private String userPwd;	//用户密码
	private String userNm;	//用户名称
	private String state;	//状态
	private String roleIds;	//角色ID串
	private String organIds;	//机构ID串
	private String psdChgFlg;	//是否需要修改密码标志，0不用修改；1需要修改密码
	private Date lastLoginTm;	//上次登录时间
	private Integer pwdErrCnt;	//密码错误次数
	private Date pwdEditTm;	//密码编辑时间
	private Date stopTm;	//停用时间
	private Date lockTm;	//冻结时间
	private String userMail;	//用户邮箱
	private String createUserAcct;	//创建用户
	private Date istTm;	//入表时间
	private Date updTm;	//更新时间
	private String organName;
	public String getUserAcct() {
		return userAcct;
	}
	public void setUserAcct(String userAcct) {
		this.userAcct = userAcct;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public String getOrganIds() {
		return organIds;
	}
	public void setOrganIds(String organIds) {
		this.organIds = organIds;
	}
	public String getPsdChgFlg() {
		return psdChgFlg;
	}
	public void setPsdChgFlg(String psdChgFlg) {
		this.psdChgFlg = psdChgFlg;
	}
	public Date getLastLoginTm() {
		return lastLoginTm;
	}
	public void setLastLoginTm(Date lastLoginTm) {
		this.lastLoginTm = lastLoginTm;
	}
	public Integer getPwdErrCnt() {
		return pwdErrCnt;
	}
	public void setPwdErrCnt(Integer pwdErrCnt) {
		this.pwdErrCnt = pwdErrCnt;
	}
	public Date getPwdEditTm() {
		return pwdEditTm;
	}
	public void setPwdEditTm(Date pwdEditTm) {
		this.pwdEditTm = pwdEditTm;
	}
	public Date getStopTm() {
		return stopTm;
	}
	public void setStopTm(Date stopTm) {
		this.stopTm = stopTm;
	}
	public Date getLockTm() {
		return lockTm;
	}
	public void setLockTm(Date lockTm) {
		this.lockTm = lockTm;
	}
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
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
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	@Override
	public String toString() {
		return "UserPOJO [userAcct=" + userAcct + ", userPwd=" + userPwd + ", userNm=" + userNm + ", state=" + state
				+ ", roleIds=" + roleIds + ", organIds=" + organIds + ", psdChgFlg=" + psdChgFlg + ", lastLoginTm="
				+ lastLoginTm + ", pwdErrCnt=" + pwdErrCnt + ", pwdEditTm=" + pwdEditTm + ", stopTm=" + stopTm
				+ ", lockTm=" + lockTm + ", userMail=" + userMail + ", createUserAcct=" + createUserAcct + ", istTm="
				+ istTm + ", updTm=" + updTm + ", organName=" + organName + "]";
	}
}
