package com.cloud.mybatis.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Description: Mapper注解，使用sqlSessionFactory
 * @author  lizhi 
 * @date  2019年4月6日 下午2:10:32
 */
public class SampleMapperScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

	private ResourceLoader resourceLoader;

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);
		if (this.resourceLoader != null) {
			scanner.setResourceLoader(this.resourceLoader);
		}
		scanner.setSqlSessionFactoryBeanName("sqlSessionFactory");
		scanner.setAnnotationClass(Mapper.class);
		scanner.registerFilters();
		scanner.doScan("com.dm");
	}

}
