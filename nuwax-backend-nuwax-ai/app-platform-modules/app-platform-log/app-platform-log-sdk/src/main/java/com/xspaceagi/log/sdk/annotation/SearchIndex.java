package com.xspaceagi.log.sdk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchIndex {

    /**
     * 索引名称，默认类名
     */
    String indexName() default "";

    /**
     * 分片数量
     */
    int shards() default 3;

    /**
     * 副本数量
     */
    int replicas() default 1;

}
