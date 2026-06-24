package com.xspaceagi.system.sdk.retry.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 重试提报数据
 */
@Data
public class RetrySubmission implements Serializable {

    private static final long serialVersionUID = -4162151240529044909L;

    /**
     * 暂时不用
     * projectCode
     */
    private String projectCode;

    /**
     * appCode
     */
    private String appCode;

    /**
     * tid
     */
    private String tid;

    /**
     * uid
     */
    private String uid;

    /**
     * spring中的bean name
     */
    private String beanName;

    /**
     * 最大重试次数
     */
    private Integer maxRetryCnt;

    /**
     * 待重试的方法
     */
    private String methodName;

    /**
     * 参数类名称
     */
    private String[] argClassNames;

    /**
     * 参数数组JSONString格式
     */
    private String argStr;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 扩展JSON信息
     */
    private String ext;

}
