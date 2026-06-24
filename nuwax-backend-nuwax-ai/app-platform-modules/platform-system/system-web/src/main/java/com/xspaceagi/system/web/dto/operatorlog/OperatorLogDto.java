package com.xspaceagi.system.web.dto.operatorlog;

import com.xspaceagi.system.infra.dao.model.OperatorLogModel;
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

public class OperatorLogDto {
    /**
     * 自增主键
     */
    @Schema(description = "自增主键", example = "1")
    private Long id;
    /**
     * 1:操作类型;2:访问日志
     */
    @Schema(description = "操作类型；1:操作类型;2:访问日志", example = "1")
    private Long operateType;
    /**
     * 系统编码
     */
    @Schema(description = "系统编码", example = "SYS001")
    private String systemCode;
    /**
     * 系统名称
     */
    @Schema(description = "系统名称", example = "系统管理")
    private String systemName;
    /**
     * 操作对象,比如:用户表,角色表,菜单表
     */
    @Schema(description = "操作对象，比如:用户表,角色表,菜单表", example = "用户表")
    private String object;
    /**
     * 操作动作,比如:新增,删除,修改,查看
     */
    @Schema(description = "操作动作，比如:新增,删除,修改,查看", example = "新增")
    private String action;
    /**
     * 操作内容,比如评估页面
     */
    @Schema(description = "操作内容，比如评估页面", example = "评估页面")
    private String operateContent;
    /**
     * 额外的操作内容信息记录,比如:更新提交的数据内容
     */
    @Schema(description = "额外的操作内容信息记录，比如:更新提交的数据内容", example = "更新了用户信息")
    private String extraContent;
    /**
     * 操作人所属机构id
     */
    @Schema(description = "操作人所属机构id", example = "1")
    private Long orgId;
    /**
     * 操作人所属机构名称
     */
    @Schema(description = "操作人所属机构名称", example = "技术部")
    private String orgName;
    /**
     * 创建人id
     */
    @Schema(description = "创建人id", example = "1")
    private Long creatorId;
    /**
     * 创建人名称
     */
    @Schema(description = "创建人名称", example = "张三")
    private String creator;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "2024-11-01T11:16:03")
    private LocalDateTime created;


    public static OperatorLogDto convertToDto(OperatorLogModel model) {
        OperatorLogDto operatorLogDto = new OperatorLogDto();
        operatorLogDto.setId(model.getId());
        operatorLogDto.setOperateType(model.getOperateType());
        operatorLogDto.setSystemCode(model.getSystemCode());
        operatorLogDto.setSystemName(model.getSystemName());
        operatorLogDto.setObject(model.getObjectOp());
        operatorLogDto.setAction(model.getAction());
        operatorLogDto.setOperateContent(model.getOperateContent());
        operatorLogDto.setExtraContent(model.getExtraContent());
        operatorLogDto.setOrgId(model.getOrgId());
        operatorLogDto.setOrgName(model.getOrgName());
        operatorLogDto.setCreatorId(model.getCreatorId());
        operatorLogDto.setCreator(model.getCreator());
        operatorLogDto.setCreated(model.getCreated());
        return operatorLogDto;

    }
}

