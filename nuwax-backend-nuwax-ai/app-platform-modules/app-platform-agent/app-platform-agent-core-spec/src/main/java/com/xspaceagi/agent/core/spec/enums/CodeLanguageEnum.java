package com.xspaceagi.agent.core.spec.enums;

public enum CodeLanguageEnum {

    Python("python"),
    JavaScript("js");

    private String name;

    CodeLanguageEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}