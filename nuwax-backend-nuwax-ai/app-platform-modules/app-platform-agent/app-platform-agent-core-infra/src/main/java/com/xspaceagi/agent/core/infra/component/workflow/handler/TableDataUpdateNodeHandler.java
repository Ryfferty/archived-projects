package com.xspaceagi.agent.core.infra.component.workflow.handler;

import com.xspaceagi.agent.core.adapter.dto.config.workflow.TableDataUpdateNodeConfigDto;
import com.xspaceagi.agent.core.adapter.dto.config.workflow.WorkflowNodeDto;
import com.xspaceagi.agent.core.infra.component.table.TableExecutor;
import com.xspaceagi.agent.core.infra.component.workflow.WorkflowContext;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Map;

public class TableDataUpdateNodeHandler extends AbstractTableNodeHandler {

    @Override
    public Mono<Object> execute(WorkflowContext workflowContext, WorkflowNodeDto node) {
        TableDataUpdateNodeConfigDto tableNodeConfigDto = (TableDataUpdateNodeConfigDto) node.getNodeConfig();
        Map<String, Object> params = extractConditionArgs(workflowContext, node, tableNodeConfigDto.getConditionArgs());
        Map<String, Object> updateParams = extraBindValueMap(workflowContext, node, tableNodeConfigDto.getInputArgs());
        //for (updateParams.entrySet())
        for (Map.Entry<String, Object> entry : updateParams.entrySet()) {
            params.put("_" + entry.getKey(), entry.getValue());
        }
        String sql = TableExecutor.generateUpdateSql(new ArrayList<>(updateParams.keySet()), tableNodeConfigDto.getTableFields(), tableNodeConfigDto.getConditionType(), tableNodeConfigDto.getConditionArgs());
        return executeSQL(workflowContext, tableNodeConfigDto, sql, params);
    }
}
