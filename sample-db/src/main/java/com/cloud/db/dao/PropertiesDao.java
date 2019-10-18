/*
 * 文件名：PropertiesDao.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： PropertiesDao.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年4月6日
 * 修改内容：新增
 */
package com.cloud.db.dao;

import com.cloud.db.pojo.PropertiesPOJO;
import com.cloud.mybatis.config.SysMapper;

/**
 * @Title:  PropertiesDao.java
 * @Package: com.dm.db.dao.PropertiesDao.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年4月6日 下午2:11:05
 */
@SysMapper
public interface PropertiesDao {
	
	public PropertiesPOJO selectById(Long id);// 通过Id(主键)查询一条记录
	
}
