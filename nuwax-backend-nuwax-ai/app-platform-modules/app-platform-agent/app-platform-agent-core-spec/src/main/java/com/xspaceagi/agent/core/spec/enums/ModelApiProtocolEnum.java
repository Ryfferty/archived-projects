package com.xspaceagi.agent.core.spec.enums;

public enum ModelApiProtocolEnum {

    OpenAI("OpenAI", "Open AI API protocol"),
    Ollama("Ollama", "Ollama API protocol"),
    Zhipu("Zhipu", "ZhipuAI API protocol"),
    Anthropic("Anthropic", "anthropic API protocol");

    private String name;

    private String description;

    ModelApiProtocolEnum(String name, String description) {
    }
}
