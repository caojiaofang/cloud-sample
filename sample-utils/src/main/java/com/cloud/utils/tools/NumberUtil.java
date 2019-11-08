/*
 * 文件名：NumberUtil.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： NumberUtil.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年10月29日
 * 修改内容：新增
 */
package com.cloud.utils.tools;
/**
 * @Title:  NumberUtil.java
 * @Package: com.cloud.utils.tools.NumberUtil.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年10月29日 上午11:55:34
 */
import java.text.DecimalFormat;
import java.util.Objects;

public class NumberUtil {
	public static String format(Number number, String pattern) {
		Objects.requireNonNull(pattern);
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		return decimalFormat.format(number);
	}

	public static int parseInt(String str, int defaultValue) {
		if (str != null) {
			try {
				return Integer.parseInt(str);
			} catch (Exception var3) {
				;
			}
		}

		return defaultValue;
	}

	public static long parseLong(String str, long defaultValue) {
		if (str != null) {
			try {
				return Long.parseLong(str);
			} catch (Exception var4) {
				;
			}
		}

		return defaultValue;
	}

	public static int parseInt(Object obj, int defaultValue) {
		if (obj != null) {
			if (obj instanceof Number) {
				return ((Number) obj).intValue();
			}

			if (obj instanceof String) {
				return parseInt((String) obj, defaultValue);
			}
		}

		return defaultValue;
	}

	public static long parseLong(Object obj, long defaultValue) {
		if (obj != null) {
			if (obj instanceof Number) {
				return ((Number) obj).longValue();
			}

			if (obj instanceof String) {
				return parseLong((String) obj, defaultValue);
			}
		}

		return defaultValue;
	}
}