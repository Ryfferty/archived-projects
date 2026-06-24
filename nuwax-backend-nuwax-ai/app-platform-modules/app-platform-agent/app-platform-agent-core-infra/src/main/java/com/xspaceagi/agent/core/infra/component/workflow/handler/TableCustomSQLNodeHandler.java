package com.xspaceagi.agent.core.infra.component.workflow.handler;

import com.xspaceagi.agent.core.adapter.dto.config.Arg;
import com.xspaceagi.agent.core.adapter.dto.config.workflow.TableCustomSqlNodeConfigDto;
import com.xspaceagi.agent.core.adapter.dto.config.workflow.WorkflowNodeDto;
import com.xspaceagi.agent.core.infra.component.workflow.WorkflowContext;
import com.xspaceagi.agent.core.spec.enums.DataTypeEnum;
import reactor.core.publisher.Mono;

import java.util.Map;

public class TableCustomSQLNodeHandler extends AbstractTableNodeHandler {

    @Override
    public Mono<Object> execute(WorkflowContext workflowContext, WorkflowNodeDto node) {
        TableCustomSqlNodeConfigDto tableNodeConfigDto = (TableCustomSqlNodeConfigDto) node.getNodeConfig();
        Map<String, Object> params = extraBindValueMap(workflowContext, node, tableNodeConfigDto.getInputArgs());
        if (tableNodeConfigDto.getInputArgs() != null) {
            for (Arg inputArgDto : tableNodeConfigDto.getInputArgs()) {
                if (inputArgDto.getName() != null && params.get(inputArgDto.getName()) == null && inputArgDto.getDataType() == DataTypeEnum.String) {
                    params.put(inputArgDto.getName(), "");
                }
            }
        }
        return executeSQL(workflowContext, tableNodeConfigDto, tableNodeConfigDto.getSql(), params);
    }
}
