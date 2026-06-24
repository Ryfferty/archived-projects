package com.xspaceagi.agent.core.infra.component.workflow.handler;

import com.xspaceagi.agent.core.adapter.dto.config.workflow.TableDataDeleteNodeConfigDto;
import com.xspaceagi.agent.core.adapter.dto.config.workflow.WorkflowNodeDto;
import com.xspaceagi.agent.core.infra.component.table.TableExecutor;
import com.xspaceagi.agent.core.infra.component.workflow.WorkflowContext;
import reactor.core.publisher.Mono;

import java.util.Map;

public class TableDataDeleteNodeHandler extends AbstractTableNodeHandler {

    @Override
    public Mono<Object> execute(WorkflowContext workflowContext, WorkflowNodeDto node) {
        TableDataDeleteNodeConfigDto tableNodeConfigDto = (TableDataDeleteNodeConfigDto) node.getNodeConfig();
        Map<String, Object> params = extractConditionArgs(workflowContext, node, tableNodeConfigDto.getConditionArgs());
        String sql = TableExecutor.generateDeleteSql(tableNodeConfigDto.getTableFields(), tableNodeConfigDto.getConditionType(), tableNodeConfigDto.getConditionArgs());
        return executeSQL(workflowContext, tableNodeConfigDto, sql, params);
    }
}
