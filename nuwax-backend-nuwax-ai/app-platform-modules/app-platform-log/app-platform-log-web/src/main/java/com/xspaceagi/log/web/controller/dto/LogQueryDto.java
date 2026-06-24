package com.xspaceagi.log.web.controller.dto;

import com.xspaceagi.log.sdk.annotation.SearchField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LogQueryDto {
    @Schema(description = "请求唯一标识可以用于关联一次请求中所有相关的操作")
    @SearchField(keyword = true)
    private String requestId;

    @Schema(description = "日志产生对象所在的空间ID")
    private Long spaceId;

    @Schema(description = "请求发起的用户ID")
    private Long userId;

    @Schema(description = "用户名")
    @SearchField(keyword = true)
    private String userName;

    @Schema(description = "日志对象类型，Agent、Plugin、Workflow、Mcp")
    @SearchField(keyword = true)
    private String targetType;

    @Schema(description = "日志对象名称")
    @SearchField(keyword = true)
    private String targetName;

    @Schema(description = "日志对象ID")
    @SearchField(keyword = true)
    private String targetId;

    @Schema(description = "会话ID")
    @SearchField(keyword = true)
    private String conversationId;

    @Schema(description = "输入参数")
    @SearchField(store = true)
    private String input;

    @Schema(description = "执行结果")
    @SearchField(store = true)
    private String output;

    @Schema(description = "执行过程数据")
    @SearchField(store = true)
    private String processData;

    @Schema(description = "执行结果码 0000为成功")
    @SearchField(keyword = true)
    private String resultCode;

    @Schema(description = "日志产生时间（大于）")
    private Long createTimeGt;

    @Schema(description = "日志产生时间（小于）")
    private Long createTimeLt;

    @Schema(description = "日志产生来源，task_center 任务中心（从任务中心点击查看详情时带上该参数）")
    private String from;
}
