/*
 * 文件名：UserDao.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： UserDao.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年6月5日
 * 修改内容：新增
 */
package com.cloud.db.dao;

import com.cloud.db.pojo.UserPOJO;
import com.cloud.mybatis.config.SysMapper;

/**
 * @Title:  UserDao.java
 * @Package: com.cloud.db.dao.UserDao.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年6月5日 下午3:45:33
 */
@SysMapper
public interface UserDao {

	public UserPOJO selectById(String account);// 通过Id(主键)查询一条记录
}
