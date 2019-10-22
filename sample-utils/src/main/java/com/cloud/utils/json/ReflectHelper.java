package com.cloud.utils.json;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator 反射工具
 */
public class ReflectHelper {

	private static Logger logger = LoggerFactory.getLogger(ReflectHelper.class); // 日志记录
	
	/**
	 * 使用Introspector，map集合成javabean
	 *
	 * @param map       map
	 * @param beanClass bean的Class类
	 * @return bean对象
	 */
	public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass) {

		if (map == null || map.isEmpty()) {
			return null;
		}

		try {
			T t = beanClass.newInstance();

			BeanInfo beanInfo = Introspector.getBeanInfo(t.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				Method setter = property.getWriteMethod();
				if (setter != null) {
					Object objectVlaue = map.get(toUpperCaseFirstOne(property.getName()));
					if (objectVlaue instanceof String && objectVlaue != null && !"".equals(objectVlaue)) {
						setter.invoke(t, objectVlaue);
					}
				}
			}
			return t;
		} catch (Exception ex) {
			logger.error("########map集合转javabean出错######，错误信息，{}", ex);
			throw new RuntimeException();
		}

	}

	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	/**
	 * 获取obj对象fieldName的Field
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			}
		}
		return null;
	}

	/**
	 * 获取obj对象fieldName的属性值
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getValueByFieldName(Object obj, String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field field = getFieldByFieldName(obj, fieldName);
		Object value = null;
		if (field != null) {
			if (field.isAccessible()) {
				value = field.get(obj);
			} else {
				field.setAccessible(true);
				value = field.get(obj);
				field.setAccessible(false);
			}
		}
		return value;
	}

	/**
	 * 设置obj对象fieldName的属性值
	 * 
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setValueByFieldName(Object obj, String fieldName, Object value) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field field = obj.getClass().getDeclaredField(fieldName);
		if (field.isAccessible()) {
			field.set(obj, value);
		} else {
			field.setAccessible(true);
			field.set(obj, value);
			field.setAccessible(false);
		}
	}

	/**
	 * 取字段，如果当前class没取到，则取父类
	 * 
	 * @param fName 字段名
	 * @param clz
	 * @return 字段
	 */
	public static Field getFied(String fName, Class<?> clz) {
		if (fName == null || (fName = fName.trim()).length() < 1)
			return null;
		Field f = null;
		try {
			f = clz.getDeclaredField(fName);// 当前class取
		} catch (SecurityException | NoSuchFieldException e) {
//			logger.error(fName+"字段：方法getFied()中处理异常");
			// this.logger.debug(clz.getName() + "没有字段：" + fName);
		}
		if (f == null) {
			clz = clz.getSuperclass();// 取父类
			if (clz != null && clz != Object.class)
				return getFied(fName, clz);
		}
		if (f != null)
			f.setAccessible(true);
		return f;
	}

	@SuppressWarnings("unchecked")
	public static void setVal(String fName, Object tag, Object val) {
		if (fName == null || (fName = fName.trim()).length() < 1)
			return;
		Object obj = null;
		Method m = null;
		String valstr = null;
		valstr = String.valueOf(val).trim();
		// String valstr = String.valueOf(val).trim();//2018-1-4 中文转码
		// 在接收到报文的时候已经处理，所以在设置值的时候不需要再次进行处理
		if (tag instanceof Map) {
			((Map<String, Object>) tag).put(fName, valstr);
			return;
		}
		try {
			Field f = getFied(fName, tag.getClass());
			if (f == null)
				return;
			if (f.getGenericType() instanceof Class) {
				Class<?> cl = (Class<?>) f.getGenericType();
				f.setAccessible(true);
				if (cl == BigDecimal.class) {
					// 当数值属性解析到的值出现为空时，直接赋值为0.00
					if (val != null && !"".equals(valstr) && !"null".equalsIgnoreCase(valstr)) {
						f.set(tag, new BigDecimal(valstr));
					} else {
						f.set(tag, new BigDecimal(0F));
					}

					return;
				}
				if (cl.isEnum()) {
					try {
						m = cl.getDeclaredMethod("getByValue", new Class<?>[] { String.class });
						obj = m.invoke(null, valstr);
					} catch (Exception e) {// 如果去查询值出现异常，直接赋值为null
						// logger.error(cl+"中未找到对应值为："+valstr, e);
						f.set(tag, null);
						return;
					}

					if (obj == null) {
						m = cl.getDeclaredMethod("valueOf", new Class<?>[] { String.class });
						try {
							obj = m.invoke(null, valstr);
						} catch (Exception e) {// 如果去查询值出现异常，直接赋值为null
							// logger.error(cl+"中未找到对应值为："+valstr, e);
							f.set(tag, null);
							return;
						}
					}
					if (obj != null)
						f.set(tag, obj);
					return;
				}
				if (cl == int.class || cl == Integer.class// int
				) {
					// 当数值属性解析到的值出现为空时，直接赋值为0
					if (val != null && !"".equals(valstr) && !"null".equalsIgnoreCase(valstr)) {
						f.set(tag, Integer.parseInt(valstr));
					} else {
						f.set(tag, 0);
					}
					return;
				}

				if (cl == long.class || cl == Long.class// long
				) {
					// 当数值属性解析到的值出现为空时，直接赋值为0
					if (val != null && !"".equals(valstr) && !"null".equalsIgnoreCase(valstr)) {
						f.set(tag, Long.parseLong(valstr));
					} else {
						f.set(tag, 0L);
					}
					return;
				}
				if (cl == String.class) {
					// 当数值属性解析到的值出现为空时，直接赋值为""
					if (val != null && !"".equals(valstr) && !"null".equalsIgnoreCase(valstr)) {
						f.set(tag, valstr);
					} else {
						f.set(tag, "");
					}
					return;
				}
			}

			// 当对象直接赋值
			f.set(tag, val);

		} catch (Exception e) {
			logger.error("报文赋值处理出现异常," + tag + "中" + fName + "属性赋值为" + valstr + "出错", e);
		}

	}
}