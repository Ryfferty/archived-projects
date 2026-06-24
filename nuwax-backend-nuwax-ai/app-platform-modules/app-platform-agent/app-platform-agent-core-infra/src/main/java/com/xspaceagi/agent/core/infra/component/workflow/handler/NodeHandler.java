package com.xspaceagi.agent.core.infra.component.workflow.handler;

import com.xspaceagi.agent.core.adapter.dto.config.workflow.WorkflowNodeDto;
import com.xspaceagi.agent.core.infra.component.workflow.WorkflowContext;
import reactor.core.publisher.Mono;

public interface NodeHandler {

    Mono<Object> execute(WorkflowContext workflowContext, WorkflowNodeDto node);
}
