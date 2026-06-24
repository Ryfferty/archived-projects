package com.xspaceagi.knowledge.domain.docparser;

import com.xspaceagi.knowledge.sdk.vo.SegmentConfigModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 文件解析请求参数
 */
@Builder
@Getter
@Setter
public class FileParseRequest {
    /**
     * 知识库ID
     */
    private Long kbId;
    /**
     * 文档ID
     */
    private Long docId;

    /**
     * 空间ID
     */
    private Long spaceId;

    /**
     * 文档内容
     */
    private String content;
    /**
     * 分段配置
     */
    private SegmentConfigModel segmentConfig;


}
