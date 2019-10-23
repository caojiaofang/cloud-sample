package com.cloud.auto.bean;

import java.util.Date;

import java.util.Date;

public class BizInfBean{

	private String sgnno;	//签约协议号
	private String instgacct;	//支付机构的支付账户编号
	private String instgid;	//支付机构的机构标识
	private String idno;	//证件号码
	private String idtp;	//证件类型
	private String acctissrid;	//商业银行的机构标识
	private String acctid;	//银行账户号
	private String accnm;	//银行账户对应的名称
	private String accttp;	//账户类型
	private String acctlvl;	//银行账户等级(可空，身份认证时为空)
	private String mobno;	//预留手机号
	private String sgnstate;	//签约协议状态
	private Date wsttm;	//入库时间
	private Date uptm;	//最后业务状态更新时间

	public String getSgnno(){
		return sgnno;
	}
	public String getInstgacct(){
		return instgacct;
	}
	public String getInstgid(){
		return instgid;
	}
	public String getIdno(){
		return idno;
	}
	public String getIdtp(){
		return idtp;
	}
	public String getAcctissrid(){
		return acctissrid;
	}
	public String getAcctid(){
		return acctid;
	}
	public String getAccnm(){
		return accnm;
	}
	public String getAccttp(){
		return accttp;
	}
	public String getAcctlvl(){
		return acctlvl;
	}
	public String getMobno(){
		return mobno;
	}
	public String getSgnstate(){
		return sgnstate;
	}
	public Date getWsttm(){
		return wsttm;
	}
	public Date getUptm(){
		return uptm;
	}
	public void setSgnno(String sgnno){
		this.sgnno = sgnno;
	}
	public void setInstgacct(String instgacct){
		this.instgacct = instgacct;
	}
	public void setInstgid(String instgid){
		this.instgid = instgid;
	}
	public void setIdno(String idno){
		this.idno = idno;
	}
	public void setIdtp(String idtp){
		this.idtp = idtp;
	}
	public void setAcctissrid(String acctissrid){
		this.acctissrid = acctissrid;
	}
	public void setAcctid(String acctid){
		this.acctid = acctid;
	}
	public void setAccnm(String accnm){
		this.accnm = accnm;
	}
	public void setAccttp(String accttp){
		this.accttp = accttp;
	}
	public void setAcctlvl(String acctlvl){
		this.acctlvl = acctlvl;
	}
	public void setMobno(String mobno){
		this.mobno = mobno;
	}
	public void setSgnstate(String sgnstate){
		this.sgnstate = sgnstate;
	}
	public void setWsttm(Date wsttm){
		this.wsttm = wsttm;
	}
	public void setUptm(Date uptm){
		this.uptm = uptm;
	}
	public	BizInfBean(){
		super();
	}
	public BizInfBean(String sgnno,String instgacct,String instgid,String idno,String idtp,String acctissrid,String acctid,String accnm,String accttp,String acctlvl,String mobno,String sgnstate,Date wsttm,Date uptm){
		super();
		this.sgnno = sgnno;
		this.instgacct = instgacct;
		this.instgid = instgid;
		this.idno = idno;
		this.idtp = idtp;
		this.acctissrid = acctissrid;
		this.acctid = acctid;
		this.accnm = accnm;
		this.accttp = accttp;
		this.acctlvl = acctlvl;
		this.mobno = mobno;
		this.sgnstate = sgnstate;
		this.wsttm = wsttm;
		this.uptm = uptm;
	}
}