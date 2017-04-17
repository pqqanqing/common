package com.wjs.common.base.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Created by panqingqing on 16/5/30.
 */
public class SpringBeanWired extends SpringBeanAutowiringSupport {

    private static SpringBeanWired instance = new SpringBeanWired();

    /**
     * 自动装配注解会让Spring通过类型匹配为beanFactory注入示例
     */
    @Autowired
    private BeanFactory beanFactory;

    private SpringBeanWired() {

    }

    public static SpringBeanWired getInstance() {
        return instance;
    }

    public <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    public Object getBeanById(String beanId) {
        return beanFactory.getBean(beanId);
    }
}