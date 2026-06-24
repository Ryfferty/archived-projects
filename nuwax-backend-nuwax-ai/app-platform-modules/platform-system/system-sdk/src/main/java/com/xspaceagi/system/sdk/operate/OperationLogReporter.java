package com.xspaceagi.system.sdk.operate;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OperationLogReporter {


    /**
     * 1:操作类型;2:访问日志
     *
     * @return
     */
    String operateType() default "1";

    /**
     * 系统编码
     *
     * @return
     */
    SystemEnum systemCode();

    /**
     * 行为类型
     *
     * @return
     */
    ActionType actionType();

    /**
     * 行为动作
     * 例如：查询用于余额
     *
     * @return
     */
    String action();


    /**
     * 行为对象类型
     * 例如: 档期商品/供应商/报表/订单等等
     *
     * @return
     */
    String objectType() default "";

    /**
     * 行为对象的中文名称
     *
     * @return
     */
    String objectName() default "";

    /**
     * 资源码
     *
     * @return
     */
    String privilegeResourceCode() default "";

    /**
     * SpEL 表达式，用于提取方法参数中的特定数据记录到日志的 extraContent 字段
     * 支持访问方法参数，使用 #参数名 的方式
     * 例如："#request.tableId" 或 "#request"
     * 留空则使用默认的 HTTP 请求参数
     *
     * @return SpEL 表达式
     */
    String spelExpression() default "";
}
