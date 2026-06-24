package com.xspaceagi.agent.core.infra.component.workflow.handler;

import com.xspaceagi.agent.core.adapter.dto.config.workflow.TableDataQueryNodeConfigDto;
import com.xspaceagi.agent.core.adapter.dto.config.workflow.WorkflowNodeDto;
import com.xspaceagi.agent.core.infra.component.table.TableExecutor;
import com.xspaceagi.agent.core.infra.component.workflow.WorkflowContext;
import reactor.core.publisher.Mono;

import java.util.Map;

public class TableDataQueryNodeHandler extends AbstractTableNodeHandler {

    @Override
    public Mono<Object> execute(WorkflowContext workflowContext, WorkflowNodeDto node) {
        TableDataQueryNodeConfigDto tableNodeConfigDto = (TableDataQueryNodeConfigDto) node.getNodeConfig();
        Map<String, Object> params = extractConditionArgs(workflowContext, node, tableNodeConfigDto.getConditionArgs());
        String sql = TableExecutor.generateQuerySql(tableNodeConfigDto.getTableFields(), tableNodeConfigDto.getConditionType(), tableNodeConfigDto.getConditionArgs(), tableNodeConfigDto.getLimit());
        return executeSQL(workflowContext, tableNodeConfigDto, sql, params);
    }
}
