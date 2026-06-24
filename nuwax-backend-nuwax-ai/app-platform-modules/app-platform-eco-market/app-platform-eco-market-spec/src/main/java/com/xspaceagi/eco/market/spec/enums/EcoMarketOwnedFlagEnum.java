package com.xspaceagi.eco.market.spec.enums;

import lombok.Getter;

/**
 * 生态市场拥有标识枚举
 */
public enum EcoMarketOwnedFlagEnum {
    
    /**
     * 否
     */
    NO(0, "否"),
    
    /**
     * 是
     */
    YES(1, "是");
    
    @Getter
    private final Integer code;
    
    @Getter
    private final String name;
    
    EcoMarketOwnedFlagEnum(Integer code, String name) {
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
        for (EcoMarketOwnedFlagEnum item : EcoMarketOwnedFlagEnum.values()) {
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
    public static EcoMarketOwnedFlagEnum getByCode(Integer code) {
        for (EcoMarketOwnedFlagEnum item : EcoMarketOwnedFlagEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
} 