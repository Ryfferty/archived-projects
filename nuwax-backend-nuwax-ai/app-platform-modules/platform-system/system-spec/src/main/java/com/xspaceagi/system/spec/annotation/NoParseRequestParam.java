package com.xspaceagi.system.spec.annotation;

import java.lang.annotation.*;

/**
 * 请求参数,不需要解析转换到QueryMap的字段
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface NoParseRequestParam {


}
