package com.xspaceagi.agent.web.ui.dto;

import com.xspaceagi.agent.core.adapter.dto.PublishUserDto;
import com.xspaceagi.agent.core.adapter.dto.PublishedDto;
import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import com.xspaceagi.knowledge.sdk.response.KnowledgeConfigVo;
import com.xspaceagi.system.application.util.DefaultIconUrlUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 知识库查询
 */
@Data
public class PublishedKnowledgeQueryDto implements Serializable {

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


    @Schema(description = "数据类型,默认文本,1:文本;2:表格")
    private Integer dataType;


    public static PublishedDto convertFromKnowledgeConfigVo(KnowledgeConfigVo knowledgeConfigVo) {
        PublishedDto publishedDto = new PublishedDto();
        publishedDto.setSpaceId(knowledgeConfigVo.getSpaceId());
        publishedDto.setTargetType(Published.TargetType.Knowledge);
        publishedDto.setTargetId(knowledgeConfigVo.getId());
        publishedDto.setName(knowledgeConfigVo.getName());
        publishedDto.setDescription(knowledgeConfigVo.getDescription());
        publishedDto.setIcon(DefaultIconUrlUtil.setDefaultIconUrl(knowledgeConfigVo.getIcon(), knowledgeConfigVo.getName(), "knowledge"));
        publishedDto.setRemark(null);
        publishedDto.setConfig(null);
        LocalDateTime modified = knowledgeConfigVo.getModified();
        if (modified != null) {
            publishedDto.setModified(Date.from(modified.atZone(ZoneId.systemDefault()).toInstant()));
        }
        LocalDateTime created = knowledgeConfigVo.getCreated();
        if (created != null) {
            publishedDto.setCreated(Date.from(created.atZone(ZoneId.systemDefault()).toInstant()));
        }
        publishedDto.setStatistics(null);

        PublishUserDto publishUserDto = new PublishUserDto();
        publishUserDto.setUserId(knowledgeConfigVo.getCreatorId());
        publishUserDto.setUserName(knowledgeConfigVo.getCreatorName());
        publishUserDto.setNickName(knowledgeConfigVo.getCreatorNickName());
        publishUserDto.setAvatar(knowledgeConfigVo.getCreatorAvatar());

        publishedDto.setPublishUser(publishUserDto);
        publishedDto.setCollect(false);
        publishedDto.setScope(Published.PublishScope.Space);
        publishedDto.setCategory(null);
        return publishedDto;

    }
}
