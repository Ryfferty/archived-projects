package com.xspaceagi.agent.core.infra.component.workflow.handler;

import com.xspaceagi.agent.core.adapter.dto.AgentOutputDto;
import com.xspaceagi.agent.core.adapter.dto.ChatMessageDto;
import com.xspaceagi.agent.core.adapter.dto.config.workflow.ProcessOutputNodeConfigDto;
import com.xspaceagi.agent.core.adapter.dto.config.workflow.WorkflowNodeDto;
import com.xspaceagi.agent.core.infra.component.model.dto.CallMessage;
import com.xspaceagi.agent.core.infra.component.workflow.WorkflowContext;
import com.xspaceagi.agent.core.spec.enums.MessageTypeEnum;
import com.xspaceagi.agent.core.spec.utils.PlaceholderParser;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Consumer;

public class ProcessOutputNodeHandler extends AbstractNodeHandler {

    @Override
    public Mono<Object> execute(WorkflowContext workflowContext, WorkflowNodeDto node) {
        ProcessOutputNodeConfigDto outputNodeConfigDto = (ProcessOutputNodeConfigDto) node.getNodeConfig();
        Map<String, Object> params = extraBindValueMap(workflowContext, node, node.getNodeConfig().getOutputArgs());
        String output = PlaceholderParser.resoleAndReplacePlaceholder(params, outputNodeConfigDto.getContent());
        Consumer<AgentOutputDto> outputDtoConsumer = workflowContext.getAgentContext().getOutputConsumer();
        if (outputDtoConsumer != null) {
            if (output != null) {
                AgentOutputDto agentOutputDto = new AgentOutputDto();
                agentOutputDto.setEventType(AgentOutputDto.EventTypeEnum.MESSAGE);
                CallMessage callMessage = new CallMessage();
                callMessage.setType(MessageTypeEnum.PROCESS_OUTPUT);
                callMessage.setRole(ChatMessageDto.Role.ASSISTANT);
                callMessage.setText(output + "\n\n");
                callMessage.setId(workflowContext.getRequestId());
                callMessage.setFinished(false);
                agentOutputDto.setRequestId(workflowContext.getRequestId());
                agentOutputDto.setData(callMessage);
                outputDtoConsumer.accept(agentOutputDto);
            }
        }
        return Mono.just(Map.of("output", output == null ? "" : output));
    }
}
