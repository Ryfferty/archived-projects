package com.xspaceagi.log.api.convert;

import com.xspaceagi.domain.model.AgentLogModel;
import com.xspaceagi.domain.model.valueobj.AgentLogEntry;
import com.xspaceagi.domain.model.valueobj.AgentLogSearchParams;
import com.xspaceagi.log.sdk.reponse.AgentLogModelResponse;
import com.xspaceagi.log.sdk.request.AgentLogEntryRequest;
import com.xspaceagi.log.sdk.request.AgentLogSearchParamsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AgentLogEntryConvert {

    public AgentLogEntry convertFrom(AgentLogEntryRequest request) {
        AgentLogEntry agentLogEntry = AgentLogEntry.builder()
                .requestId(request.getRequestId())
                .messageId(request.getMessageId())
                .conversationId(request.getConversationId())
                .agentId(request.getAgentId())
                .userUid(request.getUserUid())
                .tenantId(request.getTenantId())
                .spaceId(request.getSpaceId())
                .userInput(request.getUserInput())
                .output(request.getOutput())
                .executeResult(request.getExecuteResult())
                .inputToken(request.getInputToken())
                .outputToken(request.getOutputToken())
                .requestStartTime(request.getRequestStartTime())
                .requestEndTime(request.getRequestEndTime())
                .elapsedTimeMs(request.getElapsedTimeMs())
                .nodeType(request.getNodeType())
                .status(request.getStatus())
                .nodeName(request.getNodeName())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .userId(request.getUserId())
                .userName(request.getUserName())
                .build();
        return agentLogEntry;

    }

    public AgentLogSearchParams convertFrom(AgentLogSearchParamsRequest request) {
        AgentLogSearchParams agentLogSearchParams = AgentLogSearchParams.builder()
                .requestId(request.getRequestId())
                .conversationId(request.getConversationId())
                .messageId(request.getMessageId())
                .agentId(request.getAgentId())
                .userUid(request.getUserUid())
                .userInput(request.getUserInput())
                .output(request.getOutput())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .tenantId(request.getTenantId())
                .spaceId(request.getSpaceId())
                .build();
        return agentLogSearchParams;

    }

    public AgentLogModelResponse convertTo(AgentLogModel agentLogModel) {
        AgentLogModelResponse agentLogModelResponse = AgentLogModelResponse.builder()
                .requestId(agentLogModel.getRequestId())
                .messageId(agentLogModel.getMessageId())
                .agentId(agentLogModel.getAgentId())
                .conversationId(agentLogModel.getConversationId())
                .userUid(agentLogModel.getUserUid())
                .tenantId(agentLogModel.getTenantId())
                .spaceId(agentLogModel.getSpaceId())
                .userInput(agentLogModel.getUserInput())
                .output(agentLogModel.getOutput())
                .executeResult(agentLogModel.getExecuteResult())
                .inputToken(agentLogModel.getInputToken())
                .outputToken(agentLogModel.getOutputToken())
                .requestStartTime(agentLogModel.getRequestStartTime())
                .requestEndTime(agentLogModel.getRequestEndTime())
                .elapsedTimeMs(agentLogModel.getElapsedTimeMs())
                .nodeType(agentLogModel.getNodeType())
                .status(agentLogModel.getStatus())
                .nodeName(agentLogModel.getNodeName())
                .createdAt(agentLogModel.getCreatedAt())
                .updatedAt(agentLogModel.getUpdatedAt())
                .userId(agentLogModel.getUserId())
                .userName(agentLogModel.getUserName())
                .build();
        return agentLogModelResponse;

    }

}
