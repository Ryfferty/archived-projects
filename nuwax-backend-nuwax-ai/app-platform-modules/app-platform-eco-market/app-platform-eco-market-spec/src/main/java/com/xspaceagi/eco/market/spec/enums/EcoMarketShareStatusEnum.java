package com.xspaceagi.eco.market.spec.enums;

import lombok.Getter;

/**
 * 生态市场分享状态枚举
 */
public enum EcoMarketShareStatusEnum {
    
    /**
     * 草稿
     */
    DRAFT(1, "草稿"),
    
    /**
     * 审核中
     */
    REVIEWING(2, "审核中"),
    
    /**
     * 已发布
     */
    PUBLISHED(3, "已发布"),
    
    /**
     * 已下线
     */
    OFFLINE(4, "已下线"),
    
    /**
     * 驳回
     */
    REJECTED(5, "驳回");
    
    @Getter
    private final Integer code;
    
    @Getter
    private final String name;
    
    EcoMarketShareStatusEnum(Integer code, String name) {
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
        for (EcoMarketShareStatusEnum item : EcoMarketShareStatusEnum.values()) {
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
    public static EcoMarketShareStatusEnum getByCode(Integer code) {
        for (EcoMarketShareStatusEnum item : EcoMarketShareStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}