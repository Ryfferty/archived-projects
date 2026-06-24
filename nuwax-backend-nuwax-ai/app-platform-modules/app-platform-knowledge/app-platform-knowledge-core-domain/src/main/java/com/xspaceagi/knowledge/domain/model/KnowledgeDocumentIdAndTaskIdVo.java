package com.xspaceagi.knowledge.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 新增文档,记录文档id和任务id
 */
@Builder
@Getter
@Setter
public class KnowledgeDocumentIdAndTaskIdVo {
    /**
     * 文档id
     */
    private Long docId;
    /**
     * 任务id
     */
    private Long taskId;
}
