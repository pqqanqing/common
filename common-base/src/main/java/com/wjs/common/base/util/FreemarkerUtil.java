package com.wjs.common.base.util;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.Map;

/**
 * Created by panqingqing on 16/7/8.
 */
public class FreemarkerUtil {
    private static final String STRING_TEMPLATE = "stringTemplate";
    private static Configuration configuration = new Configuration();

    public static String parseString4Map(String context, Map<String, Object> params) {
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate(STRING_TEMPLATE, context);
        configuration.setTemplateLoader(stringTemplateLoader);
        StringWriter stringWriter = null;
        try {
            Template template = configuration.getTemplate(STRING_TEMPLATE, "utf-8");
            stringWriter = new StringWriter();
            template.process(params, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException("模板:" + context + "解析参数:" + params + "异常信息:" + e.getMessage(), e);
        } finally {
            CloseableUtil.close(stringWriter);
        }
    }
}
