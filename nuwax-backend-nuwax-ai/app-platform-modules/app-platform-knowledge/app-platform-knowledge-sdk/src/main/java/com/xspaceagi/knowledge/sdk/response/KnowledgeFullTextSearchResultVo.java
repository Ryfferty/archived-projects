package com.xspaceagi.knowledge.sdk.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 知识库全文检索单个结果 VO
 * 
 * @author system
 * @date 2025-03-31
 */
@Getter
@Setter
@Schema(description = "知识库全文检索结果")
public class KnowledgeFullTextSearchResultVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "原始分段ID")
    private Long rawSegmentId;

    @Schema(description = "文档ID")
    private Long docId;

    @Schema(description = "知识库ID")
    private Long kbId;

    @Schema(description = "原始分段文本（匹配的内容）")
    private String rawText;

    @Schema(description = "分段排序索引")
    private Integer sortIndex;

    @Schema(description = "相关性分数（BM25算法计算，值越大越相关）")
    private Float score;

    @Schema(description = "文档名称（扩展字段）")
    private String documentName;

}

