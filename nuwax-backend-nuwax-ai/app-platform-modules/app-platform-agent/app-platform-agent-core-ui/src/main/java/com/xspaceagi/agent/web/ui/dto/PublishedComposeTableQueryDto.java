package com.xspaceagi.agent.web.ui.dto;

import com.xspaceagi.agent.core.adapter.dto.PublishUserDto;
import com.xspaceagi.agent.core.adapter.dto.PublishedDto;
import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import com.xspaceagi.compose.sdk.vo.define.TableDefineVo;
import com.xspaceagi.system.application.util.DefaultIconUrlUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 组件-数据表查询
 */
@Data
public class PublishedComposeTableQueryDto implements Serializable {

    @Schema(description = "目标类型，Agent,Plugin,Workflow", hidden = true)
    private Published.TargetType targetType;

    @Schema(description = "页码")
    private Integer page;

    @Schema(description = "每页数量")
    private Integer pageSize;

    @Schema(description = "分类名称")
    private String category;

    @Schema(description = "关键字搜索")
    private String kw;

    @Schema(description = "空间ID（可选）需要通过空间过滤时有用")
    private Long spaceId;

    @Schema(description = "发布渠道", hidden = true)
    private Published.PublishChannel channel;

    public static PublishedDto convertFromComposeTableVo(TableDefineVo tableDefineVo) {
        PublishedDto publishedDto = new PublishedDto();
        publishedDto.setTenantId(tableDefineVo.getTenantId());
        publishedDto.setSpaceId(tableDefineVo.getSpaceId());
        publishedDto.setTargetType(Published.TargetType.Table);
        publishedDto.setTargetId(tableDefineVo.getId());
        publishedDto.setName(tableDefineVo.getTableName());
        publishedDto.setDescription(tableDefineVo.getTableDescription());
        publishedDto.setIcon(DefaultIconUrlUtil.setDefaultIconUrl(tableDefineVo.getIcon(), tableDefineVo.getTableName(), "table"));
        publishedDto.setRemark(null);
        publishedDto.setConfig(null);
        LocalDateTime modified = tableDefineVo.getModified();
        if (modified != null) {
            publishedDto.setModified(Date.from(modified.atZone(ZoneId.systemDefault()).toInstant()));
        }
        LocalDateTime created = tableDefineVo.getCreated();
        if (created != null) {
            publishedDto.setCreated(Date.from(created.atZone(ZoneId.systemDefault()).toInstant()));
        }
        publishedDto.setStatistics(null);

        PublishUserDto publishUserDto = new PublishUserDto();
        publishUserDto.setUserId(tableDefineVo.getCreatorId());
        publishUserDto.setUserName(tableDefineVo.getCreatorName());
        publishUserDto.setNickName(tableDefineVo.getCreatorNickName());
        publishUserDto.setAvatar(tableDefineVo.getCreatorAvatar());

        publishedDto.setPublishUser(publishUserDto);
        publishedDto.setCollect(false);
        publishedDto.setScope(Published.PublishScope.Space);
        publishedDto.setCategory(null);
        return publishedDto;

    }
}
