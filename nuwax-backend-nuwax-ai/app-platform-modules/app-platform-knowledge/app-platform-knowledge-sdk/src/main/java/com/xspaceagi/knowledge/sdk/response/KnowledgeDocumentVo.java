package com.xspaceagi.knowledge.sdk.response;

import com.xspaceagi.knowledge.sdk.enums.KnowledgeDocStatueEnum;
import com.xspaceagi.knowledge.sdk.enums.KnowledgePubStatusEnum;
import com.xspaceagi.knowledge.sdk.vo.SegmentConfigModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class KnowledgeDocumentVo implements Serializable {
    @Schema(description = "主键id")
    private Long id;

    @Schema(description = "文档所属知识库")
    private Long kbId;

    @Schema(description = "文档名称")
    private String name;

    @Schema(description = "文件URL")
    private String docUrl;

    @Schema(description = "发布状态")
    private KnowledgePubStatusEnum pubStatus;

    @Schema(description = "知识库文档状态,1:分析中;2:分析成功;10:分析失败;")
    private KnowledgeDocStatueEnum docStatus;

    @Schema(description = "知识库文档状态原因,失败的原因")
    private String docStatusReason;

    @Schema(description = "是否已经生成Q&A")
    private Boolean hasQa;

    @Schema(description = "是否已经完成嵌入")
    private Boolean hasEmbedding;

    @Schema(description = "文档分段方式")
    private SegmentConfigModel segmentConfig;

    @Schema(description = "所属空间ID")
    private Long spaceId;

    /**
     * 自定义文本内容,自定义添加会有
     */
    @Schema(description = "文件内容")
    private String fileContent;

    /**
     * 文件大小,单位字节byte
     */
    @Schema(description = "文件大小,单位字节byte")
    private Long fileSize;

    /**
     * 文件类型,1:URL访问文件;2:自定义文本内容
     */
    @Schema(description = "文件类型,1:URL访问文件;2:自定义文本内容")
    private Integer dataType;

    @Schema(description = "创建时间")
    private LocalDateTime created;

    @Schema(description = "创建人id")
    private Long creatorId;

    @Schema(description = "创建人")
    private String creatorName;

    @Schema(description = "更新时间")
    private LocalDateTime modified;

    @Schema(description = "最后修改人id")
    private Long modifiedId;

    @Schema(description = "最后修改人")
    private String modifiedName;

    /**
     * 租户ID
     */
    private Long tenantId;
}