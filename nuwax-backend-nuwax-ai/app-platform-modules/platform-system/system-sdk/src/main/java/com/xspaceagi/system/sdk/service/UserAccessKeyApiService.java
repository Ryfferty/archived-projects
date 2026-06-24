package com.xspaceagi.system.sdk.service;

import com.xspaceagi.system.sdk.service.dto.UserAccessKeyDto;

import java.util.List;

public interface UserAccessKeyApiService {

    UserAccessKeyDto newAccessKey(Long userId, UserAccessKeyDto.AKTargetType targetType, String targetId, String accessKey);

    UserAccessKeyDto newAccessKey(Long userId, UserAccessKeyDto.AKTargetType targetType, String targetId);

    UserAccessKeyDto newAccessKey(Long tenantId, Long userId, UserAccessKeyDto.AKTargetType targetType, String targetId, UserAccessKeyDto.UserAccessKeyConfig userAccessKeyConfig);

    void deleteAccessKey(Long userId, String accessKey);

    void deleteAccessKeyWithAgentId(Long agentId, String accessKey);

    List<UserAccessKeyDto> queryAccessKeyList(Long userId, UserAccessKeyDto.AKTargetType targetType, String targetId);

    /**
     * 查询用户访问密钥，仅返回一个
     */
    UserAccessKeyDto queryAccessKey(Long userId, UserAccessKeyDto.AKTargetType targetType, String targetId);

    UserAccessKeyDto queryAccessKey(String accessKey);

    UserAccessKeyDto refreshAccessKey(Long id);

    void updateAgentDevMode(Long userId, Long agentId, String accessKey, Integer devMode);

    void updateUserAccessKeyConfig(Long id, UserAccessKeyDto.UserAccessKeyConfig userAccessKeyConfig);
}
