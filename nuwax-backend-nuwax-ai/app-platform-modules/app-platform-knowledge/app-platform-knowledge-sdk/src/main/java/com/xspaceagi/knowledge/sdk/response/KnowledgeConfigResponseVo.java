package com.xspaceagi.knowledge.sdk.response;

import com.xspaceagi.system.spec.page.SuperPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 知识库表
 *
 * @TableName knowledge_config
 */
@Getter
@Setter
public class KnowledgeConfigResponseVo {

    @Schema(description = "知识库分页查询结果")
    private SuperPage<KnowledgeConfigVo> configPage;
}