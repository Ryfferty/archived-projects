package com.xspaceagi.system.infra.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("pf_retry_data")
public class RetryData {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 项目代码(系统编码,对应AMP系统编码)
     */
    private String projectCode;

    /**
     * app代码(应用编码对应AMP应用编码)
     */
    private String appCode;

    /**
     * 服务接口
     */
    private String beanName;

    /**
     * 接口方法
     */
    private String methodName;

    /**
     * tid
     */
    private String tid;

    /**
     * 状态，1待重试，2重试成功，3重试失败，4禁止重试
     */
    private int status;

    /**
     * 最大重试次数
     */
    private Integer maxRetryCnt;

    /**
     * 已重试次数
     */
    private Integer retryCnt;

    /**
     * 参数类型名称组
     */
    private String argClassNames;

    /**
     * 参数数组JSONString格式
     */
    private String argStr;

    /**
     * 方法响应
     */
    private String result;

    /**
     * 扩展信息
     */
    private String ext;

    /**
     * 重试锁定时间
     */
    private Date lockTime;

}
