package com.xspaceagi.system.sdk.retry.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RetryExecDto implements Serializable {

    private static final long serialVersionUID = -4162151240529044909L;

    /**
     * spring中的bean name
     */
    private String beanName;

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

}
