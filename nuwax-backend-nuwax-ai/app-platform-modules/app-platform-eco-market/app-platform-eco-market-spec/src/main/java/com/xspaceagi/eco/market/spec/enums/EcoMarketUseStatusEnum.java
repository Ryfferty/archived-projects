package com.xspaceagi.eco.market.spec.enums;

import lombok.Getter;

/**
 * 生态市场使用状态枚举
 */
public enum EcoMarketUseStatusEnum {
    
    /**
     * 启用
     */
    ENABLED(1, "启用"),
    
    /**
     * 禁用
     */
    DISABLED(2, "禁用");
    
    @Getter
    private final Integer code;
    
    @Getter
    private final String name;
    
    EcoMarketUseStatusEnum(Integer code, String name) {
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
        for (EcoMarketUseStatusEnum item : EcoMarketUseStatusEnum.values()) {
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
    public static EcoMarketUseStatusEnum getByCode(Integer code) {
        for (EcoMarketUseStatusEnum item : EcoMarketUseStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
} 