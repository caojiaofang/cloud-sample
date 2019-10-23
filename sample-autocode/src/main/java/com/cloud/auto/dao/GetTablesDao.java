package com.cloud.auto.dao;

import java.util.List;

/**
 * 项目名称：autoGenerate
 * 类描述：获取数据表及其结构的dao层接口
 * 创建者：longhuaiyu
 * 创建时间：2018年6月12日下午2:33:02
 * 版本号：V1.0
 */
@SuppressWarnings("all")
public interface GetTablesDao {

	// 获得数据库的所有表名
	public String[] getTablesName();

	// 获得数据表中的字段名称、字段类型
	public List getTablesStruct();
}
