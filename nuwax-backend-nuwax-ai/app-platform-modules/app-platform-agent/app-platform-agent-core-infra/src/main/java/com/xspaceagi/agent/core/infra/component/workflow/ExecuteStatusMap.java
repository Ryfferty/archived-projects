package com.xspaceagi.agent.core.infra.component.workflow;

import com.xspaceagi.agent.core.infra.component.workflow.enums.NodeExecuteStatus;

import java.util.LinkedHashMap;
import java.util.function.Consumer;

public class ExecuteStatusMap extends LinkedHashMap<String, NodeExecuteStatus> {

    private Consumer<String> consumer;

    @Override
    public NodeExecuteStatus put(String key, NodeExecuteStatus value) {
        NodeExecuteStatus executeStatus = super.put(key, value);
        if (consumer != null) {
            consumer.accept(key);
        }
        return executeStatus;
    }

    public void setConsumer(Consumer<String> consumer) {
        this.consumer = consumer;
    }
}
