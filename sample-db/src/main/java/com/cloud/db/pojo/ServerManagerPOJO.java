/*
 * 文件名：ServerManagerPOJO.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ServerManagerPOJO.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年10月18日
 * 修改内容：新增
 */
package com.cloud.db.pojo;
/**
 * @Title:  ServerManagerPOJO.java
 * @Package: com.cloud.db.pojo.ServerManagerPOJO.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年10月18日 下午3:23:56
 */
public class ServerManagerPOJO {

	private String username;	//用户名
	private String userpwd;	//用户密码
	private String ip;	//ip地址
	private String port;	//端口
	private String filepath;	//文件路径
	private String servicename;	//服务名
	private String execustatus;	//执行状态（1、可执行，2、不可执行）
	private String rmk;	//备注
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getServicename() {
		return servicename;
	}
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
	public String getExecustatus() {
		return execustatus;
	}
	public void setExecustatus(String execustatus) {
		this.execustatus = execustatus;
	}
	public String getRmk() {
		return rmk;
	}
	public void setRmk(String rmk) {
		this.rmk = rmk;
	}
	@Override
	public String toString() {
		return "ServerManagerPOJO [username=" + username + ", userpwd=" + userpwd + ", ip=" + ip + ", port=" + port
				+ ", filepath=" + filepath + ", servicename=" + servicename + ", execustatus=" + execustatus + ", rmk="
				+ rmk + "]";
	}
}
