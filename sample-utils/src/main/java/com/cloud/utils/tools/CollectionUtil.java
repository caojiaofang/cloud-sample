/*
 * 文件名：CollectionUtil.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： CollectionUtil.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年10月29日
 * 修改内容：新增
 */
package com.cloud.utils.tools;

import java.lang.reflect.Array;
import java.util.Collection;
import org.springframework.util.CollectionUtils;

/**
 * @Title:  CollectionUtil.java
 * @Package: com.cloud.utils.tools.CollectionUtil.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年10月29日 上午11:48:06
 */
public class CollectionUtil {

	public static boolean isNEmpty(Collection<?> collection) {
		return !CollectionUtils.isEmpty(collection);
	}

	@SuppressWarnings("unchecked")
	public <T> T[] convertToArray(Collection<T> collection) {
		if (CollectionUtils.isEmpty(collection)) {
			return null;
		} else {
			T[] arr = (T[]) ((Object[]) Array.newInstance(collection.iterator().next().getClass(),
					collection.size()));
			return collection.toArray(arr);
		}
	}
}
