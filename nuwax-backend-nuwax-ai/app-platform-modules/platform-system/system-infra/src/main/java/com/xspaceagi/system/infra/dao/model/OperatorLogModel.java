package com.xspaceagi.system.infra.dao.model;

import com.xspaceagi.system.spec.common.OperatorLogContext;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 操作日志(SysOperatorLog)实体类
 *
 * @author p1
 * @since 2024-11-01 11:16:02
 */
@Schema(description = "操作日志")
@Getter
@Setter
public class OperatorLogModel {

    @Schema(description = "自增主键")
    private Long id;

    private Long tenantId;

    @Schema(description = "1:操作类型;2:访问日志")
    private Long operateType;

    @Schema(description = "系统编码")
    private String systemCode;

    @Schema(description = "系统名称")
    private String systemName;

    @Schema(description = "操作对象,比如:用户表,角色表,菜单表")
    private String objectOp;

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
    private Long creatorId;

    @Schema(description = "创建人名称")
    private String creator;

    @Schema(description = "创建时间")
    private LocalDateTime created;

    @Schema(description = "修改时间")
    private LocalDateTime modified;


    public static OperatorLogModel convertToModel(OperatorLogContext operatorLogContext) {
        OperatorLogModel operatorLogModel = new OperatorLogModel();
        operatorLogModel.setId(null);
        operatorLogModel.setTenantId(operatorLogContext.getTenantId());
        operatorLogModel.setOperateType(operatorLogContext.getOperateType());
        operatorLogModel.setSystemCode(operatorLogContext.getSystemCode());
        operatorLogModel.setSystemName(operatorLogContext.getSystemName());
        operatorLogModel.setObjectOp(operatorLogContext.getObject());
        operatorLogModel.setAction(operatorLogContext.getAction());
        operatorLogModel.setOperateContent(operatorLogContext.getOperateContent());
        operatorLogModel.setExtraContent(operatorLogContext.getExtraContent());
        operatorLogModel.setOrgId(operatorLogContext.getOrgId());
        operatorLogModel.setOrgName(operatorLogContext.getOrgName());
        operatorLogModel.setCreatorId(operatorLogContext.getUserId());
        operatorLogModel.setCreator(operatorLogContext.getCreator());
        operatorLogModel.setCreated(LocalDateTime.now());
        operatorLogModel.setModified(LocalDateTime.now());
        return operatorLogModel;

    }

}
