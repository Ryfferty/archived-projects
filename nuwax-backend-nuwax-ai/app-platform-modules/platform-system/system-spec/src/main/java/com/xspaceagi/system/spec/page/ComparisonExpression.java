package com.xspaceagi.system.spec.page;

import lombok.Data;

import java.io.Serializable;

/**
 * 筛选字段
 *
 * @author soddy
 */
@Data
public class ComparisonExpression implements Serializable {


    /**
     * 字段
     */
    private String column;

    /**
     * 筛选方式 ,比如: "=",">"
     */
    private String operator;

    /**
     * 字段值
     */
    private Object value;

}
