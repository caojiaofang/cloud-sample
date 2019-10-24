/*
 * 文件名：MD5Util.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： MD5Util.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年10月24日
 * 修改内容：新增
 */
package com.cloud.utils.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title:  MD5Util.java
 * @Package: com.cloud.utils.tools.MD5Util.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年10月24日 下午3:54:25
 */
public class MD5Util {

	private static final Logger log = LoggerFactory.getLogger(MD5Util.class);

	public static byte[] getMD5(byte[] message) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return md.digest(message);
		} catch (NoSuchAlgorithmException var2) {
			log.error("", var2);
			return null;
		}
	}

	public static String getMD5(String message) {
		byte[] b = getMD5(message.getBytes());
		return byteToHexStringSingle(b);
	}

	public static String getMD5(String message, String charset) {
		try {
			byte[] m = message.getBytes(charset);
			byte[] b = getMD5(m);
			return byteToHexStringSingle(b);
		} catch (UnsupportedEncodingException var4) {
			log.error("", var4);
			return null;
		}
	}

	public static String getMD5(InputStream is) throws NoSuchAlgorithmException, IOException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] buffer = new byte[1024];
		boolean var3 = true;

		int length;
		while ((length = is.read(buffer)) != -1) {
			md.update(buffer, 0, length);
		}

		byte[] b = md.digest();
		return byteToHexStringSingle(b);
	}

	public static String getMD5(File file) {
		try {
			InputStream is = new FileInputStream(file);
			Throwable var2 = null;

			String var3;
			try {
				var3 = getMD5((InputStream) is);
			} catch (Throwable var13) {
				var2 = var13;
				throw var13;
			} finally {
				if (is != null) {
					if (var2 != null) {
						try {
							is.close();
						} catch (Throwable var12) {
							var2.addSuppressed(var12);
						}
					} else {
						is.close();
					}
				}

			}

			return var3;
		} catch (IOException | NoSuchAlgorithmException var15) {
			log.error("", var15);
			return null;
		}
	}

	public static String byteToHexStringSingle(byte[] byteArray) {
		if (byteArray == null) {
			return null;
		} else {
			StringBuffer md5StrBuff = new StringBuffer();

			for (int i = 0; i < byteArray.length; ++i) {
				String s = Integer.toHexString(255 & byteArray[i]);
				if (s.length() == 1) {
					md5StrBuff.append("0").append(s);
				} else {
					md5StrBuff.append(s);
				}
			}

			return md5StrBuff.toString();
		}
	}
}
