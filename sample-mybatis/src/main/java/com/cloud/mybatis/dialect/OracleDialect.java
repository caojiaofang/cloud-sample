package com.cloud.mybatis.dialect;
/**
 * @Description: Oracle数据库方言
 * @author  lizhi 
 * @date  2019年4月6日 下午2:10:32
 */
public class OracleDialect implements Dialect {

	@Override
	public boolean supportsLimitOffset() {
		return true;
	}

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		String forUpdateClause = null;
		boolean isForUpdate = false;
		final int forUpdateIndex = sql.toLowerCase().lastIndexOf("for update");
		if (forUpdateIndex > -1) {
			forUpdateClause = sql.substring(forUpdateIndex);
			sql = sql.substring(0, forUpdateIndex - 1);
			isForUpdate = true;
		}

		StringBuffer buf = new StringBuffer(sql.length() + 100);
		if (limit > 0) {
			buf.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			buf.append("select * from ( ");
		}
		buf.append(sql);
		if (limit > 0) {
			int end = offset + limit;
			buf.append(" ) row_ where rownum <= ");
			buf.append(end);
			buf.append(" ) where rownum_ > ");
			buf.append(offset);
		} else {
			buf.append(" ) where rownum <= ");
			buf.append(offset);
		}

		if (isForUpdate) {
			buf.append(" ");
			buf.append(forUpdateClause);
		}
		return buf.toString();
	}

}
