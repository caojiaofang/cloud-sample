/*
 * 文件名：ShellResult.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： 新增 ShellResult.java
 * 修改人：lizhi 
 * 修改时间：2019年8月19日
 * 修改内容：新增
 */
package com.cloud.utils.shell;

import java.io.Serializable;

/**
 * @Title:  ShellResult.java
 * @Package: com.ylink.utils.shell.ShellResult.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年8月19日 下午5:43:47
 */
public class ShellResult implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 脚本输出结果 */
    private String result;
    /** 异常输出结果 */
    private String errorMsg;
    /** 回话退出状态 */
    private int exitStatus;

    public ShellResult() {
    }

    public ShellResult(String result, String errorOut, int exitStatus) {
        this.result = result.trim();
        this.errorMsg = errorOut.trim();
        this.exitStatus = exitStatus;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result.trim();
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg.trim();
    }

    public int getExitStatus() {
        return exitStatus;
    }

    public void setExitStatus(int exitStatus) {
        this.exitStatus = exitStatus;
    }

    /** 是否成功关闭会话 */
    public boolean getSuccess() {
        return this.exitStatus == 0;
    }

	@Override
	public String toString() {
		return "ShellResult [result=" + result + ", errorMsg=" + errorMsg + ", exitStatus=" + exitStatus + "]";
	}
    
}
