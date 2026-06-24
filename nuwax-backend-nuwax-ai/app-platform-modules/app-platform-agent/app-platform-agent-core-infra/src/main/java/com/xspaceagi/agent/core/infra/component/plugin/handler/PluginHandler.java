package com.xspaceagi.agent.core.infra.component.plugin.handler;

import com.xspaceagi.agent.core.infra.component.plugin.PluginContext;
import reactor.core.publisher.Mono;

public interface PluginHandler {

    Mono<Object> execute(PluginContext pluginContext);
}
