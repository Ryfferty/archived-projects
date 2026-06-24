package com.xspaceagi.knowledge.man.ui.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 同步结果 VO
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
@Schema(description = "同步结果")
public class SyncResultVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "是否成功")
    private Boolean success;

    @Schema(description = "结果消息")
    private String message;

    @Schema(description = "同步的分段数量")
    private Long syncedCount;
}
