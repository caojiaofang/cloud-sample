package com.cloud.mybatis.dialect;

/**
 * @Description: MySQL数据库方言
 * @author  lizhi 
 * @date  2019年4月6日 下午2:10:32
 */
public class MySQLDialect implements Dialect {

	@Override
	public boolean supportsLimitOffset() {
		return true;
	}

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		StringBuffer buf = new StringBuffer(sql.length() + 20);
		buf.append(sql);
		if (limit > 0) {
			buf.append(" limit ");
			buf.append(offset);
			buf.append(" , ");
			buf.append(limit);
		} else {
			buf.append(" limit ");
			buf.append(offset);
		}
		return buf.toString();
	}

}
