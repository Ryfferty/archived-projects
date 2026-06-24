package com.xspaceagi.eco.market.spec.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum EcoMarketSubTabType {
    ALL(1, "全部"),
    ENABLED(2, "启用的"),
    MY_SHARE(3, "我的分享");

    private final Integer code;
    private final String desc;

    public static EcoMarketSubTabType getByCode(Integer code) {
        return Arrays.stream(EcoMarketSubTabType.values())
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

}
