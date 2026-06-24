package com.xspaceagi.custompage.infra.dao.entity;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xspaceagi.custompage.infra.dao.typehandler.VersionInfoListTypeHandler;
import com.xspaceagi.custompage.sdk.dto.VersionInfoDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName(value = "custom_page_build", autoResultMap = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomPageBuild {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long projectId;

    // 1:运行中
    private Integer devRunning;

    private Integer devPid;

    private Integer devPort;

    // 最后保活时间
    private Date lastKeepAliveTime;

    // 1:运行中
    private Integer buildRunning;

    private Date buildTime;

    private Integer buildVersion;

    private Integer codeVersion;

    @TableField(value = "version_info", typeHandler = VersionInfoListTypeHandler.class)
    private List<VersionInfoDto> versionInfo;

    // 上次对话模型ID
    private Long lastChatModelId;

    // 上次多模态ID
    private Long lastMultiModelId;

    @TableField(value = "_tenant_id")
    private Long tenantId;

    private Long spaceId;

    private Date created;

    private Long creatorId;

    private String creatorName;

    private Date modified;

    private Long modifiedId;

    private String modifiedName;

    // 1:有效; -1:无效
    private Integer yn;

}
