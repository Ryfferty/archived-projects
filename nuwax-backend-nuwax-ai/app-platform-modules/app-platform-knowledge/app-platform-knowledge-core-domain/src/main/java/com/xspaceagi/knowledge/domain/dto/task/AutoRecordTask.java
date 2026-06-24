package com.xspaceagi.knowledge.domain.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 知识库自动记录解析文档的任务信息
 */
@Builder
@Getter
@Setter
public class AutoRecordTask {

    @Schema(description = "文档ID")
    private Long docId;
    @Schema(description = "所属空间ID")
    private Long spaceId;

    @Schema(description = "文档所属知识库")
    private Long kbId;
}
