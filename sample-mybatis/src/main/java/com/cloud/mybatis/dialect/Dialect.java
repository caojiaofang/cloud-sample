package com.cloud.mybatis.dialect;
/**
 * @Description: 数据库方言
 * @author  lizhi 
 * @date  2019年4月6日 下午2:10:32
 */
public interface Dialect {

	/**
	 * 是否支持LIMIT分页查询
	 */
	public boolean supportsLimitOffset();

	/**
	 * 获取LIMIT分页查询语句
	 * 
	 * @param sql原SQL语句
	 * @param offset开始记录偏移量
	 * @param limit获取记录数
	 * @return String
	 */
	public String getLimitString(String sql, int offset, int limit);

}
