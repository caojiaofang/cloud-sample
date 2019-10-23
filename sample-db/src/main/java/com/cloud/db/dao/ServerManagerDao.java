/*
 * 文件名：ServerManagerDao.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ServerManagerDao.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年10月18日
 * 修改内容：新增
 */
package com.cloud.db.dao;

import org.apache.ibatis.annotations.Param;

import com.cloud.config.SysMapper;
import com.cloud.db.pojo.ServerManagerPOJO;

/**
 * @Title:  ServerManagerDao.java
 * @Package: com.cloud.db.dao.ServerManagerDao.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年10月18日 下午3:25:15
 */
@SysMapper
public interface ServerManagerDao {

	public ServerManagerPOJO selectById(@Param("ip")String ip,@Param("port")String port);//通过Id(主键)查询一条记录
}
