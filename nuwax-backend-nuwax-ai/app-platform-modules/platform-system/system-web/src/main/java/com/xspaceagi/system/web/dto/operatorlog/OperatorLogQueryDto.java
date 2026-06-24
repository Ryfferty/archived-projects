package com.xspaceagi.system.web.dto.operatorlog;

import com.xspaceagi.system.sdk.operate.OperateTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "操作日志查询条件")
@Getter
@Setter
public class OperatorLogQueryDto implements Serializable {


    @Schema(description = "系统名称")
    private List<String> systemName;

    @Schema(description = "系统编码")
    private List<String> systemCode;

    @Schema(description = "操作动作,比如:新增,删除,修改,查看")
    private List<String> action;

    @Schema(description = "创建人ID")
    private List<Long> creatorId;

    /**
     * @see OperateTypeEnum
     */
    @Schema(description = "操作日志类型")
    private Integer operateType;

    @Schema(description = "操作方式")
    private String actionType;

    @Schema(description = "对象名称")
    private String object;

    @Schema(description = "操作内容")
    private String operateContent;

    @Schema(description = "额外内容(请求参数)")
    private String extraContent;

    @Schema(description = "创建人名称")
    private String creator;

    @Schema(description = "创建时间-开始(毫秒时间戳)")
    private Long createTimeGt;

    @Schema(description = "创建时间-结束(毫秒时间戳)")
    private Long createTimeLt;

    @Schema(description = "创建时间范围(内部使用)")
    private List<LocalDateTime> created;

}
