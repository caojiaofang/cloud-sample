/*
 * 文件名：Base64Util.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： Base64Util.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年10月24日
 * 修改内容：新增
 */
package com.cloud.utils.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Objects;

/**
 * @Title:  Base64Util.java
 * @Package: com.cloud.utils.tools.Base64Util.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年10月24日 下午3:45:42
 */
public class Base64Util {

	private static final int BYTE_K = 4096;

	public static String encodeByte(byte[] bytes) {
		return bytes != null && bytes.length != 0 ? Base64.getEncoder().encodeToString(bytes) : null;
	}

	public static void encodeByte(byte[] bytes, OutputStream out) throws IOException {
		OutputStream base64Out = Base64.getEncoder().wrap(out);
		Throwable var3 = null;

		try {
			base64Out.write(bytes);
		} catch (Throwable var12) {
			var3 = var12;
			throw var12;
		} finally {
			if (base64Out != null) {
				if (var3 != null) {
					try {
						base64Out.close();
					} catch (Throwable var11) {
						var3.addSuppressed(var11);
					}
				} else {
					base64Out.close();
				}
			}

		}

	}

	public static void encodeStreamUnClose(InputStream in, OutputStream out) throws IOException {
		OutputStream base64Out = Base64.getEncoder().wrap(out);
		byte[] bytes = new byte[4096];
		boolean var4 = false;

		int len;
		while (-1 != (len = in.read(bytes))) {
			base64Out.write(bytes, 0, len);
		}

	}

	public static byte[] encodeStreamUnClose(InputStream in) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Throwable var2 = null;

		byte[] var3;
		try {
			encodeStreamUnClose(in, bos);
			var3 = bos.toByteArray();
		} catch (Throwable var12) {
			var2 = var12;
			throw var12;
		} finally {
			if (bos != null) {
				if (var2 != null) {
					try {
						bos.close();
					} catch (Throwable var11) {
						var2.addSuppressed(var11);
					}
				} else {
					bos.close();
				}
			}

		}

		return var3;
	}

	public static void encodeFileUnClose(File file, OutputStream out) throws IOException {
		FileInputStream in = new FileInputStream(file);
		Throwable var3 = null;

		try {
			encodeStreamUnClose(in, out);
		} catch (Throwable var12) {
			var3 = var12;
			throw var12;
		} finally {
			if (in != null) {
				if (var3 != null) {
					try {
						in.close();
					} catch (Throwable var11) {
						var3.addSuppressed(var11);
					}
				} else {
					in.close();
				}
			}

		}

	}

	public static byte[] encodeFile(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		Throwable var2 = null;

		byte[] var3;
		try {
			var3 = encodeStreamUnClose(in);
		} catch (Throwable var12) {
			var2 = var12;
			throw var12;
		} finally {
			if (in != null) {
				if (var2 != null) {
					try {
						in.close();
					} catch (Throwable var11) {
						var2.addSuppressed(var11);
					}
				} else {
					in.close();
				}
			}

		}

		return var3;
	}

	public static ByteBuffer encodeByteBuffer(ByteBuffer byteBuffer) {
		return Base64.getEncoder().encode(byteBuffer);
	}

	public static void encodeByteBuffer(ByteBuffer byteBuffer, OutputStream out) throws IOException {
		ByteBuffer buffer = encodeByteBuffer(byteBuffer);
		out.write(buffer.array());
	}

	public static byte[] decodeString(String str) {
		Objects.requireNonNull(str);
		return Base64.getDecoder().decode(str);
	}

	public static void decodeString(String str, OutputStream out) throws IOException {
		Objects.requireNonNull(out);
		byte[] bytes = decodeString(str);
		out.write(bytes);
	}

	public static void decodeStreamUnClose(InputStream in, OutputStream out) throws IOException {
		InputStream base64In = Base64.getDecoder().wrap(in);
		byte[] bytes = new byte[4096];
		boolean var4 = false;

		int len;
		while (-1 != (len = base64In.read(bytes))) {
			out.write(bytes, 0, len);
		}

	}

	public static byte[] decodeStreamUnClose(InputStream in) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Throwable var2 = null;

		byte[] var3;
		try {
			decodeStreamUnClose(in, os);
			var3 = os.toByteArray();
		} catch (Throwable var12) {
			var2 = var12;
			throw var12;
		} finally {
			if (os != null) {
				if (var2 != null) {
					try {
						os.close();
					} catch (Throwable var11) {
						var2.addSuppressed(var11);
					}
				} else {
					os.close();
				}
			}

		}

		return var3;
	}
}
