package com.xspaceagi.system.sdk.retry.utils;

import java.lang.reflect.Field;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.ClassUtils;

public final class AopUtil {

    public static Class getTargetClass(Object bean) {
        Class clx;
        if (!AopUtils.isAopProxy(bean) || AopUtils.isCglibProxy(bean)) {
            clx = ClassUtils.getUserClass(bean);
        } else if (AopUtils.isJdkDynamicProxy(bean)) {
            clx = getJdkDynamicProxyTargetObject(bean).getClass();
        } else {
            clx = bean.getClass();
        }
        return clx;
    }


    private static Object getJdkDynamicProxyTargetObject(final Object bean) {
        Object o = null;
        try {
            Field h = bean.getClass().getSuperclass().getDeclaredField("h");
            h.setAccessible(true);
            AopProxy aopProxy = (AopProxy) h.get(bean);
            Field advised = aopProxy.getClass().getDeclaredField("advised");
            advised.setAccessible(true);
            o =  ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
        } catch (Exception e) {

        }
        return o;
    }


}
