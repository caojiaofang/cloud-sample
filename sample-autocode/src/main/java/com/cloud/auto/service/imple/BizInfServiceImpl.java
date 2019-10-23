package com.cloud.auto.service.imple;

import java.util.List;

import com.cloud.auto.bean.BizInfBean;
import com.cloud.auto.dao.BizInfIDao;
import com.cloud.auto.service.BizInfIService;

public class BizInfServiceImpl implements BizInfIService{

	private BizInfIDao bizInfIDao;

	public BizInfIDao getBizInfIDao(){
		return	bizInfIDao;
	}

	public BizInfIDao setBizInfIDao(BizInfIDao bizInfIDao){
		return this.bizInfIDao=bizInfIDao;
	}

	//添加一条完整记录
	public int insertRecord(BizInfBean record){
		return	bizInfIDao.insertRecord(record);
	}

	//添加指定列的数据
	public int insertSelective(BizInfBean record){
		return	bizInfIDao.insertSelective(record);
	}

	//通过Id(主键)删除一条记录
	public int deleteById(String sgnno){
		return	bizInfIDao.deleteById(sgnno);
	}

	//按Id(主键)修改指定列的值
	public int updateByIdSelective(BizInfBean record){
		return	bizInfIDao.updateByIdSelective(record);
	}

	//按Id(主键)修改指定列的值
	public int updateById(BizInfBean record){
		return	bizInfIDao.updateById(record);
	}

	//计算表中的总记录数
	public int countRecord(){
		return	bizInfIDao.countRecord();
	}

	//根据条件计算记录条数
	public int countSelective(BizInfBean record){
		return	bizInfIDao.countSelective(record);
	}

	//获得表中的最大Id
	public int maxId(){
		return	bizInfIDao.maxId();
	}

	//通过Id(主键)查询一条记录
	public BizInfBean selectById(String sgnno){
		return bizInfIDao.selectById(sgnno);
	}

	//查询所有记录
	public List selectAll(){
		return	bizInfIDao.selectAll();
	}


}