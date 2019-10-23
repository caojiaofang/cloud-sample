/*
 * 文件名：SQLServerDialect.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： SQLServerDialect.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年10月22日
 * 修改内容：新增
 */
package com.cloud.mybatis.dialect;
/**
 * @Title:  SQLServerDialect.java
 * @Package: com.cloud.mybatis.dialect.SQLServerDialect.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年10月22日 下午3:01:07
 */
public class SQLServerDialect implements Dialect{

	public boolean supportsLimitOffset() {
		return true;
	}

	public String getLimitString(String sql, int offset, int limit) {
		if (offset > 0) {
			throw new UnsupportedOperationException("不支持偏移量方式查询！");
		}
		StringBuffer buf = new StringBuffer(sql.length() + 8);
		buf.append(sql);
		buf.insert(this.getTopInsertPoint(sql), " top " + limit);
		return buf.toString();
	}

	public int getTopInsertPoint(String sql) {
		int selectIndex = sql.toLowerCase().indexOf("select");
		int distinctIndex = sql.toLowerCase().indexOf("select distinct");
		return selectIndex + (distinctIndex == selectIndex ? 15 : 6);
	}
}
