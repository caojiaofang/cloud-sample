/*
 * 文件名：DB2Dialect.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： DB2Dialect.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年10月22日
 * 修改内容：新增
 */
package com.cloud.mybatis.dialect;
/**
 * @Title:  DB2Dialect.java
 * @Package: com.cloud.mybatis.dialect.DB2Dialect.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年10月22日 下午3:00:06
 */
public class DB2Dialect implements Dialect{

	public boolean supportsLimitOffset() {
		return true;
	}

	public String getLimitString(String sql, int offset, int limit) {
		String lsql = sql.toLowerCase();
		int startOfSelect = lsql.indexOf("select");
		StringBuffer buf = new StringBuffer(sql.length() + 100);
		buf.append(sql.substring(0, startOfSelect));
		buf.append("select * from ( select ");
		buf.append(this.getRowNumber(sql, lsql));
		if (this.hasDistinct(lsql)) {
			buf.append(" row_.* from ( ");
			buf.append(sql.substring(startOfSelect));
			buf.append(" ) as row_");
		} else {
			buf.append(sql.substring(startOfSelect + 6));
		}
		buf.append(" ) as temp_ where rownumber_ ");
		if (limit > 0) {
			int start = offset + 1;
			int end = offset + limit;
			buf.append(" between ");
			buf.append(start);
			buf.append(" and ");
			buf.append(end);
		} else {
			buf.append(" <= ");
			buf.append(offset);
		}
		return buf.toString();
	}

	private String getRowNumber(String sql, String lsql) {
		StringBuffer rownumber = new StringBuffer(50);
		rownumber.append("rownumber() over(");
		int orderByIndex = lsql.indexOf("order by");
		if (orderByIndex > 0 && !this.hasDistinct(lsql)) {
			rownumber.append(sql.substring(orderByIndex));
		}
		rownumber.append(") as rownumber_,");
		return rownumber.toString();
	}

	private boolean hasDistinct(String sql) {
		return sql.indexOf("select distinct") >= 0;
	}
}
