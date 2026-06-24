package com.xspaceagi.agent.core.spec.enums;

public enum AgentCategoryEnum {

    BusinessService("BusinessService", "商业服务"),
    TextCreation("TextCreation", "文本创作"),
    Education("Education", "学习教育"),
    CodeAssistant("CodeAssistant", "代码助手"),
    Lifestyle("Lifestyle", "生活方式"),
    ImageAndVideo("ImageAndVideo", "图像与音视频"),
    Other("Other", "其他");

    private String name;
    private String desc;

    AgentCategoryEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    //获取所有的name
    public static String[] getNames() {
        AgentCategoryEnum[] values = AgentCategoryEnum.values();
        String[] names = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            names[i] = values[i].getName();
        }
        return names;
    }

}
