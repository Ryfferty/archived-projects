package com.xspaceagi.eco.market.domain.assembler;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.xspaceagi.eco.market.domain.dto.resp.ServerConfigDetailRespDTO;
import com.xspaceagi.eco.market.domain.model.EcoMarketClientConfigModel;
import com.xspaceagi.eco.market.domain.model.EcoMarketClientPublishConfigModel;
import com.xspaceagi.eco.market.spec.enums.EcoMarketOwnedFlagEnum;
import com.xspaceagi.eco.market.spec.enums.EcoMarketShareStatusEnum;
import com.xspaceagi.eco.market.spec.enums.EcoMarketUseStatusEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * 生态市场配置对象转换器
 * 用于不同配置对象之间的转换
 */
@Slf4j
@Component
public class EcoMarketConfigTranslator {

    /**
     * 将服务器配置详情转换为客户端配置模型
     * 
     * @param serverConfig 服务器配置详情
     * @param tenantId     租户ID
     * @return 客户端配置模型
     */
    public EcoMarketClientConfigModel translateServerConfigToClientConfig(ServerConfigDetailRespDTO serverConfig,
            Long tenantId) {
        if (serverConfig == null) {
            return null;
        }

        EcoMarketClientConfigModel configModel = new EcoMarketClientConfigModel();
        configModel.setUid(serverConfig.getUid());
        configModel.setName(serverConfig.getName());
        configModel.setDescription(serverConfig.getDescription());

        // 设置配置内容
        configModel.setConfigJson(serverConfig.getConfigJson());

        // 设置其他字段
        if (serverConfig.getVersionNumber() != null) {
            configModel.setVersionNumber(serverConfig.getVersionNumber());
        } else {
            configModel.setVersionNumber(1L);
        }

        // 设置其他必要字段
        configModel.setShareStatus(EcoMarketShareStatusEnum.PUBLISHED.getCode());
        configModel.setUseStatus(EcoMarketUseStatusEnum.DISABLED.getCode());
        configModel.setOwnedFlag(EcoMarketOwnedFlagEnum.NO.getCode()); // 不是自己分享的
        configModel.setTenantId(null);

        // 复制其他可用字段
        configModel.setDataType(serverConfig.getDataType());

        configModel.setTargetType(serverConfig.getTargetType());
        configModel.setTargetSubType(serverConfig.getTargetSubType());

        configModel.setTargetId(serverConfig.getTargetId());

        configModel.setCategoryCode(serverConfig.getCategoryCode());

        configModel.setCategoryName(serverConfig.getCategoryName());

        configModel.setAuthor(serverConfig.getAuthor());

        configModel.setPublishDoc(serverConfig.getPublishDoc());

        configModel.setConfigParamJson(serverConfig.getConfigParamJson());

        configModel.setIcon(serverConfig.getIcon());

        configModel.setPageZipUrl(serverConfig.getPageZipUrl());

        // 设置发布时间
        configModel.setPublishTime(LocalDateTime.now());

        return configModel;
    }

    /**
     * 将客户端配置转换为发布配置
     * 
     * @param clientConfig 客户端配置模型
     * @return 发布配置模型
     */
    public EcoMarketClientPublishConfigModel translateClientConfigToPublishConfig(
            EcoMarketClientConfigModel clientConfig) {
        if (clientConfig == null) {
            return null;
        }

        EcoMarketClientPublishConfigModel publishConfig = new EcoMarketClientPublishConfigModel();
        publishConfig.setUid(clientConfig.getUid());
        publishConfig.setName(clientConfig.getName());
        publishConfig.setDescription(clientConfig.getDescription());
        publishConfig.setDataType(clientConfig.getDataType());
        publishConfig.setTargetType(clientConfig.getTargetType());
        publishConfig.setTargetSubType(clientConfig.getTargetSubType());
        publishConfig.setTargetId(clientConfig.getTargetId());
        publishConfig.setCategoryCode(clientConfig.getCategoryCode());
        publishConfig.setCategoryName(clientConfig.getCategoryName());
        publishConfig.setOwnedFlag(clientConfig.getOwnedFlag());
        publishConfig.setShareStatus(clientConfig.getShareStatus());
        publishConfig.setUseStatus(clientConfig.getUseStatus());
        publishConfig.setPublishTime(clientConfig.getPublishTime());
        publishConfig.setVersionNumber(clientConfig.getVersionNumber());
        publishConfig.setAuthor(clientConfig.getAuthor());
        publishConfig.setPublishDoc(clientConfig.getPublishDoc());
        publishConfig.setConfigParamJson(clientConfig.getConfigParamJson());
        publishConfig.setConfigJson(clientConfig.getConfigJson());
        publishConfig.setIcon(clientConfig.getIcon());
        publishConfig.setTenantId(clientConfig.getTenantId());
        publishConfig.setCreateClientId(clientConfig.getCreateClientId());
        publishConfig.setPageZipUrl(clientConfig.getPageZipUrl());
        return publishConfig;
    }
}