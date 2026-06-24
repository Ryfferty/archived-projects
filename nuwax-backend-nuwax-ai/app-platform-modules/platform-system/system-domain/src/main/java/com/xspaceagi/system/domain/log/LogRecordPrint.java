package com.xspaceagi.system.domain.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 打印请求入参,和结果
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogRecordPrint {


    /**
     * 自定义的消息
     *
     * @return
     */
    String content() default "";


    /**
     * 是否发送告警,默认不发送告警
     */
    boolean sendAlarm() default false;


    /**
     * 告警自定义编码code,用于区分告警
     */
    String alarmCode() default "";
}
