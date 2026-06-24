package com.xspaceagi.agent.core.infra.component.workflow.handler;

import com.xspaceagi.agent.core.adapter.dto.config.workflow.TableNodeConfigDto;
import com.xspaceagi.agent.core.adapter.dto.config.workflow.WorkflowNodeDto;
import com.xspaceagi.agent.core.infra.component.table.TableExecutor;
import com.xspaceagi.agent.core.infra.component.workflow.WorkflowContext;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Map;

public class TableDataAddNodeHandler extends AbstractTableNodeHandler {

    @Override
    public Mono<Object> execute(WorkflowContext workflowContext, WorkflowNodeDto node) {
        TableNodeConfigDto tableNodeConfigDto = (TableNodeConfigDto) node.getNodeConfig();
        Map<String, Object> params = extraBindValueMap(workflowContext, node, tableNodeConfigDto.getInputArgs());
        params.put("uid", workflowContext.getAgentContext().getUid());
        if (workflowContext.getAgentContext().getUser() != null) {
            params.put("user_name", workflowContext.getAgentContext().getUser().getUserName());
            params.put("nick_name", workflowContext.getAgentContext().getUser().getNickName());
        }
        if (workflowContext.getAgentContext().getAgentConfig() != null) {
            params.put("agent_id", workflowContext.getAgentContext().getAgentConfig().getId());
            params.put("agent_name", workflowContext.getAgentContext().getAgentConfig().getName());
        }
        String sql = TableExecutor.generateInsertSql(new ArrayList<>(params.keySet()), tableNodeConfigDto.getTableFields());
        return executeSQL(workflowContext, tableNodeConfigDto, sql, params);
    }
}
