package com.cloud.mybatis.helper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.cloud.mybatis.dialect.Dialect;
import com.cloud.mybatis.dialect.MySQLDialect;
import com.cloud.mybatis.dialect.OracleDialect;
import com.cloud.mybatis.interceptor.PageInterceptor;

/**
 * @Description: 
 * @author  lizhi 
 * @date  2019年4月6日 下午2:10:32
 */
public abstract class BatisHelper {

	/**
	 * 获取映射语句
	 * 
	 * @param namespace-命名空间
	 * @param sqlId-语句ID
	 * @return String
	 */
	public static String getStatement(final String namespace, final String sqlId) {
		StringBuilder build = new StringBuilder(namespace.length() + 10);
		build.append(namespace);
		build.append(".");
		build.append(sqlId);
		return build.toString();
	}

	/**
	 * 是否是默认RowBounds
	 * 
	 * @param rowBounds
	 * @return boolean
	 */
	public static boolean isDefaultRowBounds(RowBounds rowBounds) {
		if (RowBounds.DEFAULT.getOffset() == rowBounds.getOffset() && RowBounds.DEFAULT.getLimit() == rowBounds.getLimit()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 可变变量转Map
	 * 
	 * @param values
	 * @return Map<String ,Object>
	 */
	public static Map<String, Object> toArrayVarAsMap(Object... values) {
		if (values == null || values.length == 0)
			return null;
		Map<String, Object> params = new HashMap<String, Object>();
		for (int i = 0; i < values.length; i++) {
			params.put(String.valueOf(i), values[i]);
		}
		return params;
	}

	/**
	 * 获取子类的泛型类型
	 * 
	 * @param clazz
	 * @return Class<T>
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getChildActualType(Class<?> clazz) {
		Type type = clazz.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] types = ((ParameterizedType) type).getActualTypeArguments();
			if (types != null && types.length > 0) {
				return ((Class<T>) types[0]);
			}
		}
		return null;
	}

	/**
	 * 构造COUNT查询语句
	 * 
	 * @param hql
	 * @return String
	 */
	public static String makeCountSql(final String sql) {
		if (sql == null || sql.length() == 0) {
			throw new IllegalArgumentException("分页查询语句不能为null或空！");
		}

		String query = sql;
		// count函数统计
		StringBuffer buf = new StringBuffer();
		if (query.toLowerCase().indexOf("from") == query.toLowerCase().lastIndexOf("from")) {// sql只有一个from
			buf.append(" SELECT COUNT(*) ");
			buf.append(query.substring(query.toLowerCase().indexOf("from")));
			buf.append(" ");
		} else {
			buf.append(" SELECT COUNT(*)  FROM ( ");
			buf.append(query);
			buf.append(" ) TEMP");
		}
		return buf.toString();
	}

	/**
	 * 删除order by部分后的语句
	 * 
	 * @param sql
	 * @return String
	 */
	public static String removeOrderBy(final String sql) {
		if (sql == null || sql.length() == 0)
			return null;
		int orderIndex = sql.lastIndexOf("order ");
		if (-1 == orderIndex) {
			orderIndex = sql.lastIndexOf("ORDER ");
		}
		if (-1 != orderIndex) {
			return sql.substring(0, orderIndex);
		}
		return sql;
	}

	/**
	 * 创建PageInterceptor
	 * 
	 * @param String
	 * @return PageInterceptor
	 */
	public static PageInterceptor getInterceptor(final String driverClassName) {
		Dialect dialect = null;
		if (driverClassName == null) {
			dialect = null;
		} else if (driverClassName.contains("mysql.")) {
			dialect = new MySQLDialect();
		} else if (driverClassName.contains("oracle.")) {
			dialect = new OracleDialect();
		} 
		PageInterceptor interceptor = new PageInterceptor();
		interceptor.setDialect(dialect);
		return interceptor;
	}

}
