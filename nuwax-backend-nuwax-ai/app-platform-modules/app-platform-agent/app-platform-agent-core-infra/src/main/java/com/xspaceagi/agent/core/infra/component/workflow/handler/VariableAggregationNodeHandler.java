package com.xspaceagi.agent.core.infra.component.workflow.handler;

import com.xspaceagi.agent.core.adapter.dto.config.Arg;
import com.xspaceagi.agent.core.adapter.dto.config.workflow.WorkflowNodeDto;
import com.xspaceagi.agent.core.infra.component.workflow.WorkflowContext;
import org.apache.commons.collections4.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VariableAggregationNodeHandler extends AbstractNodeHandler {

    public Mono<Object> execute(WorkflowContext workflowContext, WorkflowNodeDto node) {
        List<Arg> inputArgs = node.getNodeConfig().getInputArgs();
        Map<String, Object> result = new HashMap<>();
        if (inputArgs != null) {
            inputArgs.forEach(arg -> {
                if (CollectionUtils.isNotEmpty(arg.getSubArgs())) {
                    for (Arg subArg : arg.getSubArgs()) {
                        Object o = extraBindValue(workflowContext, node, subArg);
                        if (o != null) {
                            // 第一个不为空的作为结果
                            result.put(arg.getName(), o);
                            break;
                        }
                    }
                }
            });
        }
        return Mono.just(result);
    }
}
