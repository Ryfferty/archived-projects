package com.xspaceagi.system.spec.annotation;

import java.lang.annotation.*;

/**
 * 敏感字段处理
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface SensitiveField {

    /**
     * 参考:SensitiveFieldEnum 枚举
     */
    SensitiveFieldEnum type();

}
