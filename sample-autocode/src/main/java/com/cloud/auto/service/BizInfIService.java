package com.cloud.auto.service;

import java.util.List;

import com.cloud.auto.bean.BizInfBean;

public interface BizInfIService{

	public int insertRecord(BizInfBean record);//添加一条完整记录

	public int insertSelective(BizInfBean record);//添加指定列的数据

	public int deleteById(String sgnno);//通过Id(主键)删除一条记录

	public int updateByIdSelective(BizInfBean record);//按Id(主键)修改指定列的值

	public int updateById(BizInfBean record);//按Id(主键)修改指定列的值

	public int countRecord();//计算表中的总记录数

	public int countSelective(BizInfBean record);//根据条件计算记录条数

	public int maxId();//获得表中的最大Id

	public BizInfBean selectById(String sgnno);//通过Id(主键)查询一条记录

	public List selectAll();//查询所有记录


}