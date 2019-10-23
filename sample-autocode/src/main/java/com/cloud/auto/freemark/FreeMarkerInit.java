package com.cloud.auto.freemark;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * 项目名称：autoGenerate
 * 类描述：Template初始化
 * 创建者：longhuaiyu
 * 创建时间：2018年8月1日上午9:49:38
 * 版本号：V1.0
 */
public class FreeMarkerInit {

    public static Template getDefinedTemplate(String templateName) throws Exception{
    	 Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
    	 cfg.setClassForTemplateLoading(FreeMarkerInit.class,"/template");
    	 cfg.setDefaultEncoding("UTF-8");
         cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
         return cfg.getTemplate(templateName);
    }

}
