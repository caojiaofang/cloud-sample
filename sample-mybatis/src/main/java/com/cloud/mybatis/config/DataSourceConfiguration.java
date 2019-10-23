package com.cloud.mybatis.config;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.util.ObjectUtils;

import com.cloud.mybatis.helper.BatisHelper;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 数据源配置
 */
@Configuration
@Import({ MapperScannerRegistrar.class, SysMapperScannerRegistrar.class })
public class DataSourceConfiguration {

	@Bean("sysDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public DataSource dataSource2(DataSourceProperties properties) {
		DataSource sysDataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
		return sysDataSource;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(MybatisProperties properties, DataSource dataSource,
			ObjectProvider<Interceptor[]> interceptorsProvider) throws Exception {
		properties.setMapperLocations(
				new String[] { "classpath*:/com/cloud/**/mapper-*.xml", "classpath*:/mapper/**/mapper-*.xml", "classpath*:/mapper/mapper-*.xml", "classpath*:mapper-*.xml" });
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(dataSource);
		factory.setVfs(SpringBootVFS.class);
		Interceptor[] interceptors = interceptorsProvider.getIfAvailable();
		if (!ObjectUtils.isEmpty(interceptors)) {
			factory.setPlugins(interceptors);
		}
		Resource[] resources = properties.resolveMapperLocations();
		if (!ObjectUtils.isEmpty(resources)) {
			factory.setMapperLocations(resources);
		}
		return factory.getObject();
	}

	@Bean("sqlSessionFactory2")
	public SqlSessionFactory sqlSessionFactory2(MybatisProperties properties, DataSource sysDataSource,
			ObjectProvider<Interceptor[]> interceptorsProvider) throws Exception {
		properties.setMapperLocations(
				new String[] { "classpath*:/com/cloud/**/mapper-*.xml", "classpath*:/mapper/**/mapper-*.xml", "classpath*:/mapper/mapper-*.xml", "classpath*:mapper-*.xml" });
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(sysDataSource);
		factory.setVfs(SpringBootVFS.class);
		Interceptor[] interceptors = interceptorsProvider.getIfAvailable();
		if (!ObjectUtils.isEmpty(interceptors)) {
			factory.setPlugins(interceptors);
		}
		Resource[] resources = properties.resolveMapperLocations();
		if (!ObjectUtils.isEmpty(resources)) {
			factory.setMapperLocations(resources);
		}
		return factory.getObject();
	}

	@Bean
	public Interceptor interceptor(DataSourceProperties properties) {
		return BatisHelper.getInterceptor(properties.determineDriverClassName());
	}

}
