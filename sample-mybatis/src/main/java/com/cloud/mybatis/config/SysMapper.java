package com.cloud.mybatis.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: SysMapper注解，使用sqlSessionFactory
 * @author  lizhi 
 * @date  2019年4月6日 下午2:10:32
 */

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface SysMapper {

}
