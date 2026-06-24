package com.xspaceagi.eco.market.domain.model;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.xspaceagi.agent.core.adapter.repository.entity.Published;

import com.xspaceagi.custompage.sdk.dto.SourceTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.EnumTypeHandler;

/**
 * 生态市场配置
 * 
 * @TableName eco_market_client_config
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EcoMarketClientConfigModel {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 唯一ID,分布式唯一UUID
     */
    private String uid;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 市场类型,默认插件,1:插件;2:模板;3:MCP
     */
    private Integer dataType;

    /**
     * 细分类型,比如: 插件,智能体,工作流
     * 
     * @see Published.TargetType
     */
    private String targetType;

    /**
     * 子类型
     * @see Published.TargetSubType
     */
    private String targetSubType;

    /**
     * 具体目标的id,可以智能体,工作流,插件,还有mcp等
     */
    private Long targetId;

    /**
     * 分类编码,商业服务等,通过接口获取
     */
    private String categoryCode;

    /**
     * 分类名称,商业服务等,通过接口获取
     */
    private String categoryName;

    /**
     * 是否我的分享,0:否(生态市场获取的);1:是(我的分享)
     */
    private Integer ownedFlag;

    /**
     * 分享状态,1:草稿;2:审核中;3:已发布;4:已下线;5:驳回
     */
    private Integer shareStatus;

    /**
     * 使用状态,1:启用;2:禁用;
     */
    private Integer useStatus;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 下线时间
     */
    private LocalDateTime offlineTime;

    /**
     * 版本号,自增,发布一次增加1,初始值为1
     */
    private Long versionNumber;

    /**
     * 作者信息
     */
    private String author;

    /**
     * 发布文档
     */
    private String publishDoc;

    /**
     * 请求参数配置json,生态市场拉取的最新的配置,或者本地保存的配置;
     * <p> 因为有的数据,是从生态市场拉取的,本地没有,所以这个字段给前端使用,是多重含义 </p>
     */
    private String configParamJson;

    /**
     * 当前本地的原始配置
     */
    private String localConfigParamJson;
    /**
     * 服务器配置参数json,存储服务器配置的参数信息
     */
    private String serverConfigParamJson;

    /**
     * 配置json,存储插件的配置信息如果有其他额外的信息保存放这里
     */
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
    private String icon;

    /**
     * 审批信息
     */
    private String approveMessage;

    /**
     * 是否租户自动启用插件,1:租户自动启用;0:非租户自动启用
     */
    private Boolean tenantEnabled;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 创建者的客户端ID
     */
    private String createClientId;

    /**
     * 创建时间
     */
    private LocalDateTime created;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 创建人
     */
    private String creatorName;

    /**
     * 更新时间
     */
    private LocalDateTime modified;

    /**
     * 最后修改人id
     */
    private Long modifiedId;

    /**
     * 最后修改人
     */
    private String modifiedName;

    // ======== 下面是扩展字段======

    /**
     * 是否是新版本
     */
    private Boolean isNewVersion;

    /**
     * 服务器版本号 ,serverVersionNumber 和 client端的 versionNumber 比较,如果serverVersionNumber
     * > versionNumber,则isNewVersion为true
     */
    private Long serverVersionNumber;

    /**
     * 页面压缩包地址
     */
    private String pageZipUrl;

    private String coverImg;

    private SourceTypeEnum coverImgSourceType;
}