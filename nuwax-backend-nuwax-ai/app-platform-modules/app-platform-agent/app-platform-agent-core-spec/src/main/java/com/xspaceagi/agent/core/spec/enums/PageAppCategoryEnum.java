package com.xspaceagi.agent.core.spec.enums;

public enum PageAppCategoryEnum {

    EDUCATION("Education", "教育"),
    WEBSITE("Website", "网站"),
    MARKETING("Marketing", "营销"),
    OFFICE("Office", "办公"),
    ECOMMERCE("Ecommerce", "电商"),
    TOOLS("Tools", "工具"),
    GAME("Game", "游戏"),
    SURVEY("Survey", "问卷"),
    RESEARCH("Research", "研究"),
    OTHER("Other", "其他");
    private String name;
    private String desc;
    PageAppCategoryEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
    public String getName() {
        return name;
    }
    public String getDesc() {
        return desc;
    }
}
