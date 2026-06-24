package com.xspaceagi.system.spec.utils;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取bean工具
 *
 **/
@Component
public class MySpringBeanContextUtil implements ApplicationContextAware {


    @Getter
    protected static ApplicationContext context;

    @SuppressWarnings("unchecked")
    public static <T> T get(String name) {
        return (T) context.getBean(name);
    }

    public static <T> T get(Class<T> tClass) {
        return context.getBean(tClass);
    }

    public static <T> T get(Class<T> tClass, Object... args) {
        return context.getBean(tClass, args);
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

}
