package com.xspaceagi.eco.market.spec.app.assembler;

import java.util.Objects;

import com.xspaceagi.eco.market.domain.dto.req.ServerConfigSaveReqDTO;
import com.xspaceagi.eco.market.domain.dto.resp.ServerConfigDetailRespDTO;
import com.xspaceagi.eco.market.domain.dto.resp.ServerConfigListRespDTO;
import com.xspaceagi.eco.market.domain.model.EcoMarketClientConfigModel;
import com.xspaceagi.eco.market.domain.model.EcoMarketClientPublishConfigModel;
import com.xspaceagi.eco.market.spec.enums.EcoMarketOwnedFlagEnum;
import com.xspaceagi.eco.market.spec.enums.EcoMarketUseStatusEnum;

/**
 * 生态市场客户端配置转换器
 * 负责各种数据对象之间的转换
 */
public class EcoMarketClientConfigTranslator {

    /**
     * 将客户端配置模型转换为服务器配置保存请求DTO
     * 
     * @param model        客户端配置模型
     * @param clientId     客户端ID
     * @param clientSecret 客户端密钥
     * @return 服务器配置保存请求DTO
     */
    public static ServerConfigSaveReqDTO toServerConfigSaveReqDTO(EcoMarketClientConfigModel model, String clientId,
            String clientSecret) {
        if (model == null) {
            return null;
        }

        ServerConfigSaveReqDTO reqDTO = new ServerConfigSaveReqDTO();
        reqDTO.setUid(model.getUid());
        reqDTO.setName(model.getName());
        reqDTO.setDescription(model.getDescription());
        reqDTO.setDataType(model.getDataType());
        reqDTO.setTargetType(model.getTargetType());
        reqDTO.setTargetSubType(model.getTargetSubType());
        reqDTO.setTargetId(model.getTargetId());
        reqDTO.setCategoryCode(model.getCategoryCode());
        reqDTO.setCategoryName(model.getCategoryName());
        reqDTO.setAuthor(model.getAuthor());
        reqDTO.setPublishDoc(model.getPublishDoc());
        reqDTO.setConfigJson(model.getConfigJson());
        reqDTO.setConfigParamJson(model.getConfigParamJson());
        reqDTO.setIcon(model.getIcon());
        reqDTO.setClientId(clientId);
        reqDTO.setClientSecret(clientSecret);
        reqDTO.setPageZipUrl(model.getPageZipUrl());
        return reqDTO;
    }

    /**
     * 将客户端配置模型转换为客户端发布配置模型
     * 
     * @param model 客户端配置模型
     * @return 客户端发布配置模型
     */
    public static EcoMarketClientPublishConfigModel toClientPublishConfigModel(EcoMarketClientConfigModel model) {
        if (model == null) {
            return null;
        }

        EcoMarketClientPublishConfigModel ecoMarketClientPublishConfigModel = EcoMarketClientPublishConfigModel
                .builder()
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
                .configJson(model.getConfigJson())
                .icon(model.getIcon())
                .tenantId(model.getTenantId())
                .createClientId(model.getCreateClientId())
                .created(model.getCreated())
                .creatorId(model.getCreatorId())
                .creatorName(model.getCreatorName())
                .modified(model.getModified())
                .modifiedId(model.getModifiedId())
                .modifiedName(model.getModifiedName())
                .build();
        return ecoMarketClientPublishConfigModel;

    }

    /**
     * 将服务器配置详情转换为客户端发布配置模型
     * 
     * @param model 发布配置详情
     * @return 客户端配置模型
     */
    public static EcoMarketClientConfigModel toClientConfigModel(EcoMarketClientPublishConfigModel model) {
        if (model == null) {
            return null;
        }

        EcoMarketClientConfigModel ecoMarketClientConfigModel = EcoMarketClientConfigModel.builder()
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
                .localConfigParamJson(model.getConfigParamJson())
                .serverConfigParamJson(null)
                .configJson(model.getConfigJson())
                .icon(model.getIcon())
                .tenantId(model.getTenantId())
                .createClientId(model.getCreateClientId())
                .created(model.getCreated())
                .creatorId(model.getCreatorId())
                .creatorName(model.getCreatorName())
                .modified(model.getModified())
                .modifiedId(model.getModifiedId())
                .modifiedName(model.getModifiedName())
                .isNewVersion(false)
                .serverVersionNumber(null)
                .build();
        return ecoMarketClientConfigModel;

    }

    /**
     * 将服务器配置详情转换为客户端配置模型
     * 
     * @param serverData 服务器配置详情
     * @param tenantId   租户ID
     * @return 客户端配置模型
     */
    public static EcoMarketClientConfigModel serverDetailToClientConfig(ServerConfigDetailRespDTO serverData,
            Long tenantId) {
        if (serverData == null) {
            return null;
        }

        EcoMarketClientConfigModel ecoMarketClientConfigModel = EcoMarketClientConfigModel.builder()
                .id(serverData.getId())
                .uid(serverData.getUid())
                .name(serverData.getName())
                .description(serverData.getDescription())
                .dataType(serverData.getDataType())
                .targetType(serverData.getTargetType())
                .targetSubType(serverData.getTargetSubType())
                .targetId(serverData.getTargetId())
                .categoryCode(serverData.getCategoryCode())
                .categoryName(serverData.getCategoryName())
                .ownedFlag(serverData.getOwnedFlag())
                .shareStatus(serverData.getShareStatus())
                .useStatus(serverData.getUseStatus())
                .publishTime(serverData.getPublishTime())
                .offlineTime(serverData.getOfflineTime())
                .versionNumber(serverData.getVersionNumber())
                .author(serverData.getAuthor())
                .publishDoc(serverData.getPublishDoc())
                .configParamJson(serverData.getConfigParamJson())
                .localConfigParamJson(null)
                .configJson(serverData.getConfigJson())
                .icon(serverData.getIcon())
                // 设置租户ID为空,避免后续客户端使用
                .tenantId(null)
                .createClientId(serverData.getCreateClientId())
                .created(serverData.getCreated())
                .creatorId(serverData.getCreatorId())
                .creatorName(serverData.getCreatorName())
                .modified(serverData.getModified())
                .modifiedId(serverData.getModifiedId())
                .modifiedName(serverData.getModifiedName())
                .isNewVersion(false)
                .serverVersionNumber(serverData.getVersionNumber())
                .build();

        // 设置其他必要字段
        ecoMarketClientConfigModel.setShareStatus(serverData.getShareStatus());
        ecoMarketClientConfigModel.setUseStatus(EcoMarketUseStatusEnum.DISABLED.getCode());
        ecoMarketClientConfigModel.setOwnedFlag(EcoMarketOwnedFlagEnum.NO.getCode()); // 不是自己分享的

        return ecoMarketClientConfigModel;

    }

    /**
     * 将服务器配置列表项转换为客户端配置模型
     * 
     * @param serverConfig 服务器配置列表项
     * @param tenantId     租户ID
     * @return 客户端配置模型
     */
    public static EcoMarketClientConfigModel serverListItemToClientConfig(ServerConfigListRespDTO serverConfig,
            Long tenantId) {
        if (serverConfig == null) {
            return null;
        }

        EcoMarketClientConfigModel newModel = new EcoMarketClientConfigModel();
        newModel.setUid(serverConfig.getUid());
        newModel.setName(serverConfig.getName());
        newModel.setDescription(serverConfig.getDescription());
        newModel.setDataType(serverConfig.getDataType());
        newModel.setTargetType(serverConfig.getTargetType());
        newModel.setTargetSubType(serverConfig.getTargetSubType());
        newModel.setTargetId(serverConfig.getTargetId());
        newModel.setCategoryCode(serverConfig.getCategoryCode());
        newModel.setCategoryName(serverConfig.getCategoryName());
        newModel.setShareStatus(serverConfig.getShareStatus());
        newModel.setVersionNumber(serverConfig.getVersionNumber());
        newModel.setAuthor(serverConfig.getAuthor());
        newModel.setPublishDoc(serverConfig.getPublishDoc());
        newModel.setIcon(serverConfig.getIcon());
        newModel.setOwnedFlag(serverConfig.getOwnedFlag());
        newModel.setUseStatus(serverConfig.getUseStatus());
        // 新配置无需比较版本
        newModel.setIsNewVersion(false);
        newModel.setServerVersionNumber(serverConfig.getVersionNumber());
        newModel.setTenantId(tenantId);

        return newModel;
    }

    /**
     * 比较本地配置与服务器配置的版本
     * 
     * @param clientModel 本地配置
     * @param serverModel 服务器配置
     */
    public static void compareVersion(EcoMarketClientConfigModel clientModel, EcoMarketClientConfigModel serverModel) {
        if (clientModel == null) {
            return;
        }

        boolean isOwnShare = Objects.equals(clientModel.getOwnedFlag(), EcoMarketOwnedFlagEnum.YES.getCode());

        if (!isOwnShare) {
            // 不是自己分享的，需要与服务器版本比较
            if (serverModel != null) {
                // 比较版本
                if (serverModel.getVersionNumber() > clientModel.getVersionNumber()) {
                    clientModel.setIsNewVersion(true);
                    clientModel.setServerVersionNumber(serverModel.getVersionNumber());
                } else {
                    clientModel.setIsNewVersion(false);
                    clientModel.setServerVersionNumber(serverModel.getVersionNumber());
                }
            }
        } else {
            // 设置默认标记（没有新版本）
            clientModel.setIsNewVersion(false);
        }
    }

}