package com.cloud.db.pojo;


public class SampleUserPOJO{

	private String userAcct;	//用户账号
	private String userPwd;	//用户密码
	private String userNm;	//用户名称
	private String userState;	//账号状态
	private String roleIds;	//角色串
	private String organIds;	//机构串
	private String pwdChgFlg;	//是否需要修改密码（00：不需要；01：需要）
	private String lastLoginTm;	//上次登录时间
	private Integer pwdErrCnt;	//密码错误次数
	private String pwdEdtTm;	//密码修改时间
	private String stopTm;	//停用时间
	private String lockTm;	//冻结时间
	private String userEmail;	//用户邮箱
	private String createUserAcct;	//创建用户
	private String istTm;	//入表时间
	private String updTm;	//更新时间
	private String sessionId;	//登录ip
	
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
	public String getUserState() {
		return userState;
	}
	public void setUserState(String userState) {
		this.userState = userState;
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
	public String getPwdChgFlg() {
		return pwdChgFlg;
	}
	public void setPwdChgFlg(String pwdChgFlg) {
		this.pwdChgFlg = pwdChgFlg;
	}
	public String getLastLoginTm() {
		return lastLoginTm;
	}
	public void setLastLoginTm(String lastLoginTm) {
		this.lastLoginTm = lastLoginTm;
	}
	public Integer getPwdErrCnt() {
		return pwdErrCnt;
	}
	public void setPwdErrCnt(Integer pwdErrCnt) {
		this.pwdErrCnt = pwdErrCnt;
	}
	public String getPwdEdtTm() {
		return pwdEdtTm;
	}
	public void setPwdEdtTm(String pwdEdtTm) {
		this.pwdEdtTm = pwdEdtTm;
	}
	public String getStopTm() {
		return stopTm;
	}
	public void setStopTm(String stopTm) {
		this.stopTm = stopTm;
	}
	public String getLockTm() {
		return lockTm;
	}
	public void setLockTm(String lockTm) {
		this.lockTm = lockTm;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	@Override
	public String toString() {
		return "SampleUserDO [userAcct=" + userAcct + ", userPwd=" + userPwd + ", userNm=" + userNm + ", userState="
				+ userState + ", roleIds=" + roleIds + ", organIds=" + organIds + ", pwdChgFlg=" + pwdChgFlg
				+ ", lastLoginTm=" + lastLoginTm + ", pwdErrCnt=" + pwdErrCnt + ", pwdEdtTm=" + pwdEdtTm + ", stopTm="
				+ stopTm + ", lockTm=" + lockTm + ", userEmail=" + userEmail + ", createUserAcct=" + createUserAcct
				+ ", istTm=" + istTm + ", updTm=" + updTm + ", sessionId=" + sessionId + "]";
	}

}