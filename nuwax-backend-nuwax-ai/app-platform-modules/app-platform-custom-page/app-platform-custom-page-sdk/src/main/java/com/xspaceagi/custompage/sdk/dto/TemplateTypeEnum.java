package com.xspaceagi.custompage.sdk.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TemplateTypeEnum {
    REACT("react"),
    VUE3("vue3");

    private final String value;

    TemplateTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonValue
    public String toJson() {
        return value;
    }

    @JsonCreator
    public static TemplateTypeEnum fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (TemplateTypeEnum type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unsupported templateType: " + value);
    }

    public static TemplateTypeEnum defaultType(TemplateTypeEnum templateType) {
        return templateType == null ? REACT : templateType;
    }
}
