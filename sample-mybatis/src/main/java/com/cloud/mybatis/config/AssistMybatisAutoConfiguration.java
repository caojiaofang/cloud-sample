/*
 * 文件名：AssistMybatisAutoConfiguration.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： AssistMybatisAutoConfiguration.java
 * 修改人：lizhi 1708029
 * 修改时间：2019年10月22日
 * 修改内容：新增
 */
package com.cloud.mybatis.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloud.mybatis.helper.BatisHelper;

/**
 * @Title:  AssistMybatisAutoConfiguration.java
 * @Package: com.cloud.mybatis.config.AssistMybatisAutoConfiguration.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年10月22日 下午3:07:58
 */
@Configuration
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@EnableConfigurationProperties({MybatisProperties.class})
public class AssistMybatisAutoConfiguration {

	  @Bean({"defaultInterceptor"})
	  @ConditionalOnMissingBean({Interceptor.class})
	  public Interceptor interceptor(DataSource dataSource) {
	    String str = null;
	    try {
	      str = dataSource.getConnection().getMetaData().getDatabaseProductName();
	      if (str != null)
	        str = str.toLowerCase();
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }
	    return BatisHelper.getInterceptor(str);
	  }
}
