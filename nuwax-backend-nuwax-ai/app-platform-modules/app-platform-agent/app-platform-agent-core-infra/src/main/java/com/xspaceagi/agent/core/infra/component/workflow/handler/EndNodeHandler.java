package com.xspaceagi.agent.core.infra.component.workflow.handler;

import com.xspaceagi.agent.core.adapter.dto.config.workflow.EndNodeConfigDto;
import com.xspaceagi.agent.core.adapter.dto.config.workflow.WorkflowNodeDto;
import com.xspaceagi.agent.core.infra.component.workflow.WorkflowContext;
import com.xspaceagi.agent.core.spec.utils.PlaceholderParser;

import java.util.Map;

public class EndNodeHandler extends AbstractNodeHandler {

    @Override
    protected Object executeNode(WorkflowContext workflowContext, WorkflowNodeDto node) {
        Map<String, Object> result = extraBindValueMap(workflowContext, node, node.getNodeConfig().getOutputArgs());
        EndNodeConfigDto endNodeConfigDto = (EndNodeConfigDto) node.getNodeConfig();
        if (endNodeConfigDto.getReturnType() == EndNodeConfigDto.ReturnType.TEXT) {
            String content = PlaceholderParser.resoleAndReplacePlaceholder(result, endNodeConfigDto.getContent());
            workflowContext.setEndNodeContent(content);
            workflowContext.setEndNodeRequireStreamOut(endNodeConfigDto.getStreamOut());
        }
        return result;
    }
}
