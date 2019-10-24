package com.cloud.db.dao;

import	com.cloud.db.pojo.SampleUserPOJO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.annotations.Mapper;

import com.cloud.mybatis.interceptor.PageBounds;

@Mapper
public interface SampleUserDao{

	public int insertRecord(SampleUserPOJO record);//添加一条完整记录

	public int deleteById(@Param("userAcct")String userAcct);//通过Id(主键)删除一条记录

	public int updateByIdSelective(SampleUserPOJO record);//按Id(主键)修改指定列的值

	public int updateById(SampleUserPOJO record);//按Id(主键)修改所有列的值

	public SampleUserPOJO selectById(@Param("userAcct")String userAcct);//通过Id(主键)查询一条记录

	public List<SampleUserPOJO> selectAll(SampleUserPOJO record,PageBounds pageBounds);//根据条件查询

	public List<SampleUserPOJO> selectByMap(Map<String,Object> paramMap,PageBounds pageBounds);//根据条件查询


}