package com.xspaceagi.system.sdk.retry.annotation;

import com.xspaceagi.system.sdk.retry.constant.CsConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Retry {

    /**
     * spring bean
     * 1.可以使用class全限定名 (默认)
     * 2.可以使用beanName
     */
    String bean() default CsConstants.CLASS_NAME;

    /**
     * 最大重试次数
     */
    int maxRetry() default 5;

    /**
     * 是否异步执行方法
     */
    boolean async() default false;

    /**
     * 打了重试注解的方法出现异常时是否向上抛出异常
     */
    boolean throwException() default true;

}
