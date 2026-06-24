package com.xspaceagi.knowledge.sdk.enums;

import java.util.Arrays;

import lombok.Getter;

/**
 * 知识库文档状态,1:分析中;2:分析成功;10:分析失败;
 */
@Getter
public enum KnowledgeDocStatueEnum {
    ANALYZING(1, "分析中", "分析中"),
    ANALYZED(2, "分析成功", "分析成功"),
    ANALYZING_RAW(3, "分析中", "分段生成中"),
    ANALYZED_QA(4, "分析中", "问答生成中"),
    ANALYZED_EMBEDDING(5, "分析中", "向量化中"),
    ANALYZE_FAILED(10, "分析失败", "分析失败");

    private final Integer code;
    /**
     * 描述
     */
    private final String desc;
    /**
     * 具体步骤描述
     */
    private final String stepDesc;

    KnowledgeDocStatueEnum(Integer code, String desc, String stepDesc) {
        this.code = code;
        this.desc = desc;
        this.stepDesc = stepDesc;
    }

    public static KnowledgeDocStatueEnum getByCode(Integer code) {
        return Arrays.stream(KnowledgeDocStatueEnum.values())
                .filter(e -> e.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}
