package com.cloud.utils.tools;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BeanCopyUtil {

	private static final Logger log = LoggerFactory.getLogger(BeanCopyUtil.class);

	public static void copy(Object src, Object dest) {
		BeanCopier copier = BeanCopier.create(src.getClass(), dest.getClass(), false);
		copier.copy(src, dest, (Converter) null);
	}

	public static <T> T mapToObject(Map<String, Object> map, Class<T> t) {
		if (map == null) {
			return null;
		} else {
			try {
				T obj = t.newInstance();
				BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
				PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
				PropertyDescriptor[] var5 = propertyDescriptors;
				int var6 = propertyDescriptors.length;

				for (int var7 = 0; var7 < var6; ++var7) {
					PropertyDescriptor property = var5[var7];
					Method setter = property.getWriteMethod();
					if (setter != null) {
						setter.invoke(obj, map.get(property.getName()));
					}
				}

				return obj;
			} catch (Exception var10) {
				log.error("", var10);
				return null;
			}
		}
	}

	public static <T> Map<String, Object> objectToMap(T obj) {
		if (obj == null) {
			return null;
		} else {
			try {
				Map<String, Object> map = new HashMap();
				BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
				PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
				PropertyDescriptor[] var4 = propertyDescriptors;
				int var5 = propertyDescriptors.length;

				for (int var6 = 0; var6 < var5; ++var6) {
					PropertyDescriptor property = var4[var6];
					String key = property.getName();
					if (!key.equalsIgnoreCase("class")) {
						Method getter = property.getReadMethod();
						Object value = getter != null ? getter.invoke(obj) : null;
						char c = key.charAt(0);
						if (Character.isUpperCase(c)) {
							key = Character.toLowerCase(c) + key.substring(1);
						}

						map.put(key, value);
					}
				}

				return map;
			} catch (Exception var12) {
				log.error("", var12);
				return null;
			}
		}
	}

	public static <T> T mapToObject(Map<String, Object> map, T obj) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			PropertyDescriptor[] var4 = propertyDescriptors;
			int var5 = propertyDescriptors.length;

			for (int var6 = 0; var6 < var5; ++var6) {
				PropertyDescriptor property = var4[var6];
				Method setter = property.getWriteMethod();
				if (setter != null) {
					setter.invoke(obj, map.get(property.getName()));
				}
			}
		} catch (Exception var9) {
			log.error("", var9);
		}

		return obj;
	}

	public static <V, T> T setValue(String name, V val, T obj) {
		if (obj == null) {
			log.error("Bean为空。");
		}

		if (name == null) {
			log.error("Bean属性为空。");
		}

		PropertyDescriptor propertyDescriptor;
		try {
			propertyDescriptor = new PropertyDescriptor(name, obj.getClass());
		} catch (Exception var6) {
			log.error("Bean属性[{}]的set/get方法不存在。", name);
			return obj;
		}

		try {
			Method method = propertyDescriptor.getWriteMethod();
			method.invoke(obj, val);
		} catch (Exception var5) {
			log.error("Bean属性[{}]赋值失败。", name);
			log.debug("", var5);
		}

		return obj;
	}

	@SuppressWarnings("unchecked")
	public static <V, T> V getValue(String name, T obj) {
		if (obj == null) {
			log.error("Bean为空。");
		}

		if (name == null) {
			log.error("Bean属性为空。");
		}

		PropertyDescriptor propertyDescriptor;
		try {
			propertyDescriptor = new PropertyDescriptor(name, obj.getClass());
		} catch (Exception var5) {
			log.error("Bean属性[{}]的set/get方法不存在。", name);
			return null;
		}

		try {
			Method method = propertyDescriptor.getReadMethod();
			return (V) method.invoke(obj);
		} catch (Exception var4) {
			log.error("Bean属性[{}]取值失败。", name);
			log.debug("", var4);
			return null;
		}
	}
}
