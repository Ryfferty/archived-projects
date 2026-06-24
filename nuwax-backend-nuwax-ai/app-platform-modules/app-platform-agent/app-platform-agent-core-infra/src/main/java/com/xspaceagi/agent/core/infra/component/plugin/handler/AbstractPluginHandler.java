package com.xspaceagi.agent.core.infra.component.plugin.handler;

import com.xspaceagi.agent.core.adapter.dto.config.Arg;
import com.xspaceagi.agent.core.infra.component.BaseComponent;
import com.xspaceagi.agent.core.infra.component.plugin.PluginContext;
import jakarta.annotation.PreDestroy;
import org.apache.commons.collections4.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;

public abstract class AbstractPluginHandler extends BaseComponent implements PluginHandler {
    public Mono<Object> execute(PluginContext pluginContext) {
        return Mono.create(emitter -> executorService.submit(() -> {
            try {
                Object object = execute0(pluginContext);
                emitter.success(object);
            } catch (Exception e) {
                emitter.error(e);
            }
        }));
    }

    protected Object execute0(PluginContext pluginContext) {
        return new Object();
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
    }

    protected void resetRequireToFalse(List<Arg> outputArgs) {
        if (CollectionUtils.isNotEmpty(outputArgs)) {
            outputArgs.forEach(arg -> {
                if (arg.isRequire()) {
                    arg.setRequire(false);
                }
                resetRequireToFalse(arg.getSubArgs());
            });
        }
    }
}
