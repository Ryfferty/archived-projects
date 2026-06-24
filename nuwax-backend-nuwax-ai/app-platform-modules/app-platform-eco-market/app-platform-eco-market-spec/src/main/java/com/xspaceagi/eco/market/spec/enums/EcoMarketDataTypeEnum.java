package com.xspaceagi.eco.market.spec.enums;

import lombok.Getter;

/**
 * 生态市场数据类型枚举
 */
public enum EcoMarketDataTypeEnum {
    
    /**
     * 插件
     */
    PLUGIN(1, "插件"),
    
    /**
     * 模板
     */
    TEMPLATE(2, "模板"),
    
    /**
     * MCP
     */
    MCP(3, "MCP");
    
    @Getter
    private final Integer code;
    
    @Getter
    private final String name;
    
    EcoMarketDataTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    
    /**
     * 根据编码获取名称
     *
     * @param code 编码
     * @return 名称
     */
    public static String getNameByCode(Integer code) {
        for (EcoMarketDataTypeEnum item : EcoMarketDataTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getName();
            }
        }
        return null;
    }
    
    /**
     * 根据编码获取枚举
     *
     * @param code 编码
     * @return 枚举
     */
    public static EcoMarketDataTypeEnum getByCode(Integer code) {
        for (EcoMarketDataTypeEnum item : EcoMarketDataTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
} 