package com.xspaceagi.system.spec.annotation;

import com.xspaceagi.system.spec.enums.ResourceEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 资源权限注解，支持方法级别和类级别
 * - 方法级别：仅该方法的接口受此注解限制
 * - 类级别：该类的所有接口方法都受此注解限制，方法上的注解优先于类上的
 * 支持传入多个资源码
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireResource {
    
    /**
     * 资源枚举（支持多个，用户拥有任意一个即可）
     */
    ResourceEnum[] value() default {};
    
    /**
     * 资源码（支持多个，用户拥有任意一个即可）
     * 当 value 与 codes 同时指定时，会合并校验
     */
    String[] codes() default {};
    
    /**
     * 权限描述信息
     */
    String description() default "需要资源权限";
    
    /**
     * 是否允许跳过权限检查（用于测试等场景）
     */
    boolean skipCheck() default false;
}

