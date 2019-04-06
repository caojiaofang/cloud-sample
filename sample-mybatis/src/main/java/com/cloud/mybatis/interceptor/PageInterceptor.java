package com.cloud.mybatis.interceptor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloud.mybatis.dialect.Dialect;
import com.cloud.mybatis.helper.BatisHelper;


/**
 * @Description: 
 * @author  lizhi 
 * @date  2019年4月6日 下午2:10:32
 */
@Intercepts(@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }))
public class PageInterceptor implements Interceptor {

	private final static Logger log = LoggerFactory.getLogger(PageInterceptor.class);
	// 数据库方言
	private Dialect dialect;

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		if (null == dialect) {
			return invocation.proceed();
		}
		if (!dialect.supportsLimitOffset()) {
			return invocation.proceed();
		}
		Object[] args = invocation.getArgs();
		RowBounds rowBounds = (RowBounds) args[2];
		if (rowBounds == null) {
			args[2] = RowBounds.DEFAULT;
		}
		// 是否是默认RowBounds
		if (rowBounds == null || BatisHelper.isDefaultRowBounds(rowBounds) || rowBounds.getLimit() < 0) {
			return invocation.proceed();
		}
		MappedStatement newMappedStatement = null;
		MappedStatement ms = (MappedStatement) args[0];
		Object parameter = args[1];
		BoundSql boundSql = ms.getBoundSql(parameter);

		// 分页增加对 bind foreach的支持
		Object additionalParameters = getAdditionalParameters(boundSql);
		if (additionalParameters != null && parameter != null) {
			try {
				Map<String, Object> map = (Map<String, Object>) additionalParameters;
				Map<String, Object> params = (Map<String, Object>) parameter;
				params.putAll(map);
				args[1] = params;
			} catch (Exception e) {
				log.error("PageInterceptor error!", e);
			}
		}

		String sql = boundSql.getSql();
		// 总记录数－COUNT查询
		if (rowBounds instanceof PageBounds) {
			PageBounds pageBounds = (PageBounds) rowBounds;
			newMappedStatement = makeCountMappedStatement(ms, BatisHelper.makeCountSql(sql), boundSql);
			args[0] = newMappedStatement;
			args[2] = RowBounds.DEFAULT;
			Object result = invocation.proceed();
			if (result instanceof Collection) {
				Iterator it = ((Collection) result).iterator();
				if (it.hasNext()) {
					pageBounds.setCountRows((Integer) it.next());
				}
			}
			// 无需执行查询
			if (pageBounds.getCountRows() == 0) {
				return new ArrayList<Object>();
			}
			// 无需执行分页查询
			if (pageBounds.getCountRows() < pageBounds.getLimit()) {
				args[0] = ms;
				args[2] = RowBounds.DEFAULT;
				return invocation.proceed();
			}
		}
		// 数据库方言－物理分页
		sql = dialect.getLimitString(sql, rowBounds.getOffset(), rowBounds.getLimit());
		newMappedStatement = makeMappedStatement(ms, sql, boundSql);
		args[0] = newMappedStatement;
		args[2] = RowBounds.DEFAULT;
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	@Override
	public void setProperties(Properties properties) {

	}

	private Object getAdditionalParameters(BoundSql boundSql) {
		Field[] fields = BoundSql.class.getDeclaredFields();
		Object value = null;
		for (Field f : fields) {
			if ("additionalParameters".equals(f.getName())) {
				boolean b = f.isAccessible();
				f.setAccessible(true);
				try {
					value = f.get(boundSql);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				} finally {
					f.setAccessible(b);
				}
				return value;
			}
		}
		return value;
	}

	/**
	 * 创建Count语句映射对象
	 */
	private MappedStatement makeCountMappedStatement(MappedStatement ms, String sql, BoundSql boundSql) {
		return createMappedStatement(ms, sql, boundSql, true);
	}

	/**
	 * 创建SQL语句映射对象
	 */
	private MappedStatement makeMappedStatement(MappedStatement ms, String sql, BoundSql boundSql) {
		return createMappedStatement(ms, sql, boundSql, false);
	}

	/**
	 * 创建SQL语句映射对象
	 * 
	 * @param ms 原MappedStatement
	 * @param sql SQL语句
	 * @param boundSql BoundSql
	 * @param isCountResultMap 是否是Count结果映射
	 * @return MappedStatement
	 */
	private MappedStatement createMappedStatement(MappedStatement ms, String sql, BoundSql boundSql, boolean isCountResultMap) {
		BoundSql newBoundSql = createBoundSql(ms.getConfiguration(), sql, boundSql);
		SqlSource sqlSource = new SqlSource() {
			@Override
			public BoundSql getBoundSql(Object parameterObject) {
				return newBoundSql;
			}
		};
		return createMappedStatement(ms, sqlSource, isCountResultMap);
	}

	/**
	 * 创建SQL语句对象
	 */
	private BoundSql createBoundSql(Configuration configuration, String sql, BoundSql boundSql) {
		return new BoundSql(configuration, sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
	}

	/**
	 * 创建SQL语句映射对象
	 * 
	 * @param ms 原MappedStatement
	 * @param newSqlSource 新SqlSource
	 * @param isCountResultMap 是否是Count结果映射
	 * @return MappedStatement
	 */
	private MappedStatement createMappedStatement(MappedStatement ms, SqlSource newSqlSource, boolean isCountResultMap) {
		String msId = ms.getId();
		if (isCountResultMap) {
			msId = ms.getId() + "_COUNT_";
		}
		Builder builder = new Builder(ms.getConfiguration(), msId, newSqlSource, ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultSetType(ms.getResultSetType());
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());
		// 结果映射
		if (!isCountResultMap) {
			builder.resultMaps(ms.getResultMaps());
		} else {
			ResultMap resultMap = createCountResultMap(ms);
			List<ResultMap> resultMaps = new ArrayList<ResultMap>();
			resultMaps.add(resultMap);
			builder.resultMaps(resultMaps);
		}

		String[] props = ms.getKeyProperties();
		if (props != null && props.length > 0) {
			StringBuilder build = new StringBuilder();
			for (String key : props) {
				build.append(key);
				build.append(",");
			}
			if (build.length() > 0) {
				build.deleteCharAt(build.length() - 1);
			}
			builder.keyProperty(build.toString());
		}
		return builder.build();
	}

	/**
	 * 创建count映射结果
	 */
	public ResultMap createCountResultMap(MappedStatement ms) {
		List<ResultMapping> resultMappings = new ArrayList<ResultMapping>();
		ResultMap resultMap = new ResultMap.Builder(ms.getConfiguration(), ms.getId(), int.class, resultMappings).build();
		return resultMap;
	}

}
