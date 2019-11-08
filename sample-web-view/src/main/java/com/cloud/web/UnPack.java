/*
 * 文件名：UnPack.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： UnPack.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年10月31日
 * 修改内容：新增
 */
package com.cloud.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @Title:  UnPack.java
 * @Package: com.cloud.web.UnPack.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年10月31日 下午4:33:36
 */
public class UnPack {

	public static final String UTF_8 = "UTF-8";
	public static final String GBK = "GBK";
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String string = "123456";
		String gbk= new String(string.getBytes(UTF_8),UTF_8);  
		System.out.println(gbk);
		
		// 加密
		//String string = "123456";
		string = URLEncoder.encode(string,UTF_8);
		byte[] packResult = null;
		try {
			packResult = string.getBytes(UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		EncryptionKeyer encoder = new EncryptionKeyer();
		byte[] resultByte = null;
		resultByte = encoder.addKeyToByte(packResult);

		int reqlen = resultByte.length;
		byte[] packResultReq = new byte[4 + reqlen];
		byte[] headbyte = new byte[4];
		headbyte = (fillStr(String.valueOf(reqlen), "0", 4)).getBytes();
		System.arraycopy(headbyte, 0, packResultReq, 0, 4);
		System.arraycopy(resultByte, 0, packResultReq, 4, reqlen);
		System.err.println("得到的组包报文:" + new String(packResultReq));

		// 解密
		String string2 = new String(packResultReq);
		byte[] resBytes = null;
		try {
			resBytes = string2.getBytes(UTF_8);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		EncryptionKeyer encryptionKeyer = new EncryptionKeyer();
		encryptionKeyer.subkey(resBytes, resBytes.length);
		try {
			System.err.println("得到的解包报文:" + new String(resBytes, UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public static String fillStr(String value, String fillChar, int len) {

		while (value.length() < len) {
			value = fillChar + value;
		}

		return value;
	}
}
