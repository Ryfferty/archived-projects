package com.xspaceagi.agent.core.application.service;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xspaceagi.agent.core.adapter.application.AgentTempChatApplicationService;
import com.xspaceagi.agent.core.adapter.dto.AgentTempChatDto;
import com.xspaceagi.agent.core.adapter.repository.AgentTempChatRepository;
import com.xspaceagi.agent.core.adapter.repository.entity.AgentTempChat;
import com.xspaceagi.system.sdk.service.UserAccessKeyApiService;
import com.xspaceagi.system.sdk.service.dto.UserAccessKeyDto;
import com.xspaceagi.system.spec.common.RequestContext;
import com.xspaceagi.system.application.dto.TenantConfigDto;
import jakarta.annotation.Resource;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AgentTempChatApplicationServiceImpl implements AgentTempChatApplicationService {

    @Resource
    private AgentTempChatRepository agentTempChatRepository;

    @Resource
    private UserAccessKeyApiService userAccessKeyApiService;

    @Override
    @DSTransactional
    public AgentTempChatDto createTempChat(Long agentId) {
        AgentTempChat agentTempChat = new AgentTempChat();
        agentTempChat.setAgentId(agentId);
        agentTempChat.setChatKey("ck-" + UUID.randomUUID().toString().replace("-", ""));
        agentTempChat.setRequireLogin(1);
        agentTempChat.setUserId(RequestContext.get().getUserId());
        agentTempChatRepository.save(agentTempChat);
        userAccessKeyApiService.newAccessKey(RequestContext.get().getUserId(), UserAccessKeyDto.AKTargetType.TempChat, agentId.toString(), agentTempChat.getChatKey());
        return convertToAgentTempChatDto(agentTempChat);
    }

    private AgentTempChatDto convertToAgentTempChatDto(AgentTempChat agentTempChat) {
        if (agentTempChat == null) {
            return null;
        }
        TenantConfigDto tenantConfigDto = (TenantConfigDto) RequestContext.get().getTenantConfig();
        String siteUrl = tenantConfigDto.getSiteUrl();
        siteUrl = siteUrl.trim().endsWith("/") ? siteUrl.trim() : siteUrl.trim() + "/";
        String chatUrl = siteUrl + "chat-temp/" + agentTempChat.getChatKey();
        String qrCodeUrl = siteUrl + "api/qr/" + Base64.encodeBase64String(chatUrl.getBytes());
        return AgentTempChatDto.builder()
                .id(agentTempChat.getId())
                .agentId(agentTempChat.getAgentId())
                .chatKey(agentTempChat.getChatKey())
                .chatUrl(chatUrl)
                .requireLogin(agentTempChat.getRequireLogin())
                .expire(agentTempChat.getExpire())
                .userId(agentTempChat.getUserId())
                .qrCodeUrl(qrCodeUrl)
                .build();
    }

    @Override
    public void deleteTempChat(Long agentId, Long id) {
        agentTempChatRepository.remove(new QueryWrapper<>(AgentTempChat.builder().id(id).agentId(agentId).build()));
    }

    @Override
    public void updateTempChat(AgentTempChatDto agentTempChatDto) {
        AgentTempChat agentTempChat = AgentTempChat.builder()
                .id(agentTempChatDto.getId())
                .agentId(agentTempChatDto.getAgentId())
                .chatKey(agentTempChatDto.getChatKey())
                .requireLogin(agentTempChatDto.getRequireLogin())
                .expire(agentTempChatDto.getExpire())
                .build();
        agentTempChatRepository.updateById(agentTempChat);
    }

    @Override
    public List<AgentTempChatDto> queryTempChatList(Long agentId) {
        List<AgentTempChat> agentTempChatList = agentTempChatRepository.list(new QueryWrapper<>(AgentTempChat.builder().agentId(agentId).build()));
        return agentTempChatList.stream().map(this::convertToAgentTempChatDto).collect(Collectors.toList());
    }

    @Override
    public AgentTempChatDto queryTempChatByChatKey(String chatKey) {
        AgentTempChat agentTempChat = agentTempChatRepository.getOne(new QueryWrapper<>(AgentTempChat.builder().chatKey(chatKey).build()));
        return convertToAgentTempChatDto(agentTempChat);
    }
}
