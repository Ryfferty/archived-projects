package com.xspaceagi.system.sdk.operate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 */
@AllArgsConstructor
@Getter
public enum SystemEnum {

    KNOWLEDGE_CONFIG("知识库基础配置"),
    AGENT("智能体"),
    REPORT("评估配置"),
    SYSTEM("系统配置"),
    LOG_PLATFORM("日志平台"),
    ECO_MARKET("生态市场"),
    DB_TABLE("数据表"),

    ;

    private final String desc;


}
