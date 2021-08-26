package com.lmj.vueblog.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {
    public static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    /**
     * 获取context
     */
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    /**
     * 通过 bean 名称获取context中的 bean
     */
    public static Object getBean(String beanName){
        return context.getBean(beanName);
    }

    /**
     * 通过类型获取上下文中的bean
     */
    public static Object getBean(Class<?> requiredType) {
        return context.getBean(requiredType);
    }

}
