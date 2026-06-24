package com.xspaceagi.agent.core.infra.component.workflow.dto;

import com.xspaceagi.agent.core.adapter.dto.config.workflow.WorkflowNodeDto;
import com.xspaceagi.agent.core.infra.component.workflow.enums.NodeExecuteStatus;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class LoopNodeExecutingDto extends WorkflowNodeDto {

    private Map<String, NodeExecuteResult> nodeExecuteResultMap;
    private Map<String, NodeExecuteStatus> nodeExecuteStatusMap;

    private Map<String, List<Object>> inputValueMap;

    private Map<String, Object> currentLoopItemValueMap;

    private int index;

    private boolean continueLoop;

    private boolean breakLoop;

    private boolean done;

    public void setBreakLoop(boolean breakLoop) {
        this.breakLoop = breakLoop;
        this.done = true;
    }

    public void setContinueLoop(boolean continueLoop) {
        this.continueLoop = continueLoop;
        this.done = true;
    }

    public Map<String, NodeExecuteResult> getNodeExecuteResultMap() {
        return nodeExecuteResultMap == null ? nodeExecuteResultMap = new ConcurrentHashMap<>() : nodeExecuteResultMap;
    }

    public Map<String, NodeExecuteStatus> getNodeExecuteStatusMap() {
        return nodeExecuteStatusMap == null ? nodeExecuteStatusMap = new ConcurrentHashMap<>() : nodeExecuteStatusMap;
    }

    public String toString() {
        return "LoopNodeExecutingDto(nodeExecuteInfoMap=" + this.getNodeExecuteResultMap() + ", inputValueMap=" + this.getInputValueMap() + ", currentLoopItemValueMap=" + this.getCurrentLoopItemValueMap() + ", index=" + this.getIndex() + ", continueLoop=" + this.isContinueLoop() + ", breakLoop=" + this.isBreakLoop() + ")";
    }
}
