package com.xspaceagi.system.domain.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogPrint {

    /**
     * 门店ID
     */
    String localeId() default "";

    /**
     * 业务逻辑名字，方便区分日志
     */
    String step() default "";

    /**
     * 单号位置
     */
    String billNo() default "";


    /**
     * topic
     */
    String topic() default "";


    /**
     * messageId
     */
    String messageId() default "";


    /**
     * 业务类型
     *
     * @return
     */
    String businessType() default "";


    /**
     * 自定义的消息
     *
     * @return
     */
    String content() default "";


    /**
     * 发生异常后是否发送自定义告警,默认不发送
     */
    boolean alarmWhenException() default false;


    /**
     * 日志记录类型，默认
     *
     * @return
     */
    LogType logType() default LogType.NORMAL;
}
