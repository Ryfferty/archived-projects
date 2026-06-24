package com.xspaceagi.system.spec.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 操作日志(SysOperatorLog)实体类
 *
 * @author p1
 * @since 2024-11-01 11:16:02
 */
@Schema(description = "操作日志")
@Getter
@Setter
@Builder
@AllArgsConstructor  
@NoArgsConstructor  
public class OperatorLogContext {

    private Long tenantId;
    /**
     * 客户端请求时间
     */
    private Long clientTime;

    /**
     * 客户端ip
     */
    private String ip;

    @Schema(description = "1:操作类型;2:访问日志")
    private Long operateType;

    @Schema(description = "系统编码")
    private String systemCode;

    @Schema(description = "系统名称")
    private String systemName;

    @Schema(description = "操作对象,比如:用户表,角色表,菜单表")
    private String object;

    @Schema(description = "操作动作,比如:新增,删除,修改,查看")
    private String action;

    @Schema(description = "操作内容,比如评估页面")
    private String operateContent;

    @Schema(description = "额外的操作内容信息记录,比如:更新提交的数据内容")
    private String extraContent;

    @Schema(description = "操作人所属机构id")
    private Long orgId;

    @Schema(description = "操作人所属机构名称")
    private String orgName;

    @Schema(description = "创建人id")
    private Long userId;

    @Schema(description = "创建人名称")
    private String creator;


    @Schema(description = "操作状态,成功;失败")
    private String status;

}
