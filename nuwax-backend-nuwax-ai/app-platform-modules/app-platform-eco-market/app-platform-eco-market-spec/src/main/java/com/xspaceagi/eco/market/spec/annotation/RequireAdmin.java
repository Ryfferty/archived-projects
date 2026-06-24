package com.xspaceagi.eco.market.spec.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 管理员权限注解
 * 用于标记需要管理员权限才能访问的接口
 * 
 * @author soddy
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireAdmin {
    
    /**
     * 权限描述信息
     * @return 权限描述
     */
    String value() default "需要管理员权限";
    
    /**
     * 是否允许跳过权限检查（用于测试环境等特殊场景）
     * @return 是否允许跳过
     */
    boolean skipCheck() default false;
}