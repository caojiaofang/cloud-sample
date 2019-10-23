package com.cloud.auto.dao;

/**
 * 生成Mapper.xml的dao层接口
 * 
 * @author
 */
public interface MapperXmlAutoDao {

	// 通过表名、字段名称、字段类型创建Mapper.xml
	public boolean createMapperXml();
}