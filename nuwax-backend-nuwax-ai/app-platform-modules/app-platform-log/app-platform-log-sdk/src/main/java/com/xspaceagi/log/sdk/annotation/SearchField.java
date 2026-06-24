package com.xspaceagi.log.sdk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchField {

    /**
     * 是否索引字段
     *
     * @return
     */
    boolean index() default true;

    /**
     * 是否为keyword字段
     *
     * @return
     */
    boolean keyword() default false;

    /**
     * 是否存储字段
     *
     * @return
     */
    boolean store() default false;

}
