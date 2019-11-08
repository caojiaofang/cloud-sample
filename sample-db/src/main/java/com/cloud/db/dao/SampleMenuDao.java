package com.cloud.db.dao;

import	com.cloud.db.pojo.SampleMenuPOJO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.annotations.Mapper;

import com.cloud.mybatis.interceptor.PageBounds;

@Mapper
public interface SampleMenuDao{

	public int insertRecord(SampleMenuPOJO record);//添加一条完整记录

	public int deleteById(@Param("menuId")String menuId);//通过Id(主键)删除一条记录

	public int updateByIdSelective(SampleMenuPOJO record);//按Id(主键)修改指定列的值

	public int updateById(SampleMenuPOJO record);//按Id(主键)修改所有列的值

	public SampleMenuPOJO selectById(@Param("menuId")String menuId);//通过Id(主键)查询一条记录

	public List<SampleMenuPOJO> selectAll(SampleMenuPOJO record,PageBounds pageBounds);//根据条件查询

	public List<SampleMenuPOJO> selectByMap(Map<String,Object> paramMap,PageBounds pageBounds);//根据条件查询


}