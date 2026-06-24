package com.xspaceagi.eco.market.web.controller.dto.resp;

import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import com.xspaceagi.custompage.sdk.dto.SourceTypeEnum;
import com.xspaceagi.eco.market.domain.model.EcoMarketClientConfigModel;
import com.xspaceagi.system.application.util.DefaultIconUrlUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 客户端配置响应VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "客户端配置响应VO")
public class ClientConfigVo {

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    private Long id;

    /**
     * 唯一ID,分布式唯一UUID
     */
    @Schema(description = "唯一ID,分布式唯一UUID")
    private String uid;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;

    /**
     * 市场类型,默认插件,1:插件;2:模板;3:MCP
     */
    @Schema(description = "市场类型,默认插件,1:插件;2:模板;3:MCP")
    private Integer dataType;

    /**
     * 细分类型,比如: 插件,智能体,工作流
     */
    @Schema(description = "细分类型,比如: 插件,智能体,工作流")
    private String targetType;

    /**
     * 子类型
     * @see Published.TargetSubType
     */
    @Schema(description = "子类型")
    private String targetSubType;

    /**
     * 具体目标的id,可以智能体,工作流,插件,还有mcp等
     */
    @Schema(description = "具体目标的id,可以智能体,工作流,插件,还有mcp等")
    private Long targetId;

    /**
     * 分类编码,商业服务等,通过接口获取
     */
    @Schema(description = "分类编码,商业服务等,通过接口获取")
    private String categoryCode;

    /**
     * 分类名称,商业服务等,通过接口获取
     */
    @Schema(description = "分类名称,商业服务等,通过接口获取")
    private String categoryName;

    /**
     * 是否我的分享,0:否(生态市场获取的);1:是(我的分享)
     */
    @Schema(description = "是否我的分享,0:否(生态市场获取的);1:是(我的分享)")
    private Integer ownedFlag;

    /**
     * 分享状态,1:草稿;2:审核中;3:已发布;4:已下线;5:驳回
     */
    @Schema(description = "分享状态,1:草稿;2:审核中;3:已发布;4:已下线;5:驳回")
    private Integer shareStatus;

    /**
     * 使用状态,1:启用;2:禁用;
     */
    @Schema(description = "使用状态,1:启用;2:禁用;")
    private Integer useStatus;

    /**
     * 发布时间
     */
    @Schema(description = "发布时间")
    private LocalDateTime publishTime;

    /**
     * 下线时间
     */
    @Schema(description = "下线时间")
    private LocalDateTime offlineTime;

    /**
     * 版本号,自增,发布一次增加1,初始值为1
     */
    @Schema(description = "版本号,自增,发布一次增加1,初始值为1")
    private Long versionNumber;

    /**
     * 作者信息
     */
    @Schema(description = "作者信息")
    private String author;

    /**
     * 发布文档
     */
    @Schema(description = "发布文档")
    private String publishDoc;

    /**
     * 请求参数配置json,生态市场拉取的最新的配置
     */
    @Schema(description = "请求参数配置json")
    private String configParamJson;
    /**
     * 当前本地的原始配置
     */
    @Schema(description = "当前本地的原始配置")
    private String localConfigParamJson;

    /**
     * 服务器配置参数json,存储服务器配置的参数信息
     */
    @Schema(description = "服务器配置参数json,存储服务器配置的参数信息")
    private String serverConfigParamJson;

    /**
     * 配置json,存储插件的配置信息如果有其他额外的信息保存放这里
     */
    @Schema(description = "配置json,存储插件的配置信息如果有其他额外的信息保存放这里")
    private String configJson;

    /**
     * 当前本地的原始配置json
     */
    @Schema(description = "当前本地的原始配置json")
    private String localConfigJson;

    /**
     * 服务器配置json
     */
    @Schema(description = "服务器配置json")
    private String serverConfigJson;

    /**
     * 图标图片地址
     */
    @Schema(description = "图标图片地址")
    private String icon;

    /**
     * 审批信息
     */
    @Schema(description = "审批信息")
    private String approveMessage;
    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

    /**
     * 所属空间ID
     */
    @Schema(description = "所属空间ID")
    private Long spaceId;

    /**
     * 创建者的客户端ID
     */
    @Schema(description = "创建者的客户端ID")
    private String createClientId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime created;

    /**
     * 创建人id
     */
    @Schema(description = "创建人id")
    private Long creatorId;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String creatorName;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime modified;

    /**
     * 最后修改人id
     */
    @Schema(description = "最后修改人id")
    private Long modifiedId;

    /**
     * 最后修改人
     */
    @Schema(description = "最后修改人")
    private String modifiedName;

    /**
     * 是否有新版本,true:是;false:否
     */
    @Schema(description = "是否有新版本,true:是;false:否")
    private Boolean isNewVersion;

    /**
     * 服务器端最新版本号
     */
    @Schema(description = "服务器端最新版本号")
    private Long serverVersionNumber;

    @Schema(description = "封面图片")
    private String coverImg;

    @Schema(description = "图片图片来源")
    private SourceTypeEnum coverImgSourceType;

    /**
     * 将模型转换为DTO
     */
    public static ClientConfigVo convert2Dto(EcoMarketClientConfigModel model) {
        if (model == null) {
            return null;
        }
        ClientConfigVo clientConfigVo = ClientConfigVo.builder()
                .id(model.getId())
                .uid(model.getUid())
                .name(model.getName())
                .description(model.getDescription())
                .dataType(model.getDataType())
                .targetType(model.getTargetType())
                .targetSubType(model.getTargetSubType())
                .targetId(model.getTargetId())
                .categoryCode(model.getCategoryCode())
                .categoryName(model.getCategoryName())
                .ownedFlag(model.getOwnedFlag())
                .shareStatus(model.getShareStatus())
                .useStatus(model.getUseStatus())
                .publishTime(model.getPublishTime())
                .offlineTime(model.getOfflineTime())
                .versionNumber(model.getVersionNumber())
                .author(model.getAuthor())
                .publishDoc(model.getPublishDoc())
                .configParamJson(model.getConfigParamJson())
                .localConfigParamJson(model.getLocalConfigParamJson())
                .serverConfigParamJson(model.getServerConfigParamJson())
                .configJson(model.getConfigJson())
                .localConfigJson(model.getLocalConfigJson())
                .serverConfigJson(model.getServerConfigJson())
                .icon(model.getIcon())
                .approveMessage(model.getApproveMessage())
                .tenantId(model.getTenantId())
                .createClientId(model.getCreateClientId())
                .created(model.getCreated())
                .creatorId(model.getCreatorId())
                .creatorName(model.getCreatorName())
                .modified(model.getModified())
                .modifiedId(model.getModifiedId())
                .modifiedName(model.getModifiedName())
                .isNewVersion(model.getIsNewVersion())
                .serverVersionNumber(model.getServerVersionNumber())
                .coverImg(model.getCoverImg())
                .coverImgSourceType(model.getCoverImgSourceType())
                .build();
        clientConfigVo.setIcon(DefaultIconUrlUtil.setDefaultIconUrl(clientConfigVo.getIcon(), clientConfigVo.getName(), model.getTargetType()));
        return clientConfigVo;
    }
}