//package com.xspaceagi.system.spec.enums;
//
//import lombok.Getter;
//
///**
// * 系统内置用户组
// */
//@Getter
//public enum SysBuiltinGroupEnum {
//
//    DEFAULT_GROUP("default_group", "默认用户组", "系统默认用户组", 1);
//
//    /**
//     * 用户组编码
//     */
//    private final String code;
//
//    /**
//     * 用户组名称
//     */
//    private final String name;
//
//    /**
//     * 用户组描述
//     */
//    private final String description;
//
//    /**
//     * 排序索引
//     */
//    private final Integer sortIndex;
//
//    SysBuiltinGroupEnum(String code, String name, String description, Integer sortIndex) {
//        this.code = code;
//        this.name = name;
//        this.description = description;
//        this.sortIndex = sortIndex;
//    }
//
//    /**
//     * 根据code获取枚举
//     */
//    public static SysBuiltinGroupEnum getByCode(String code) {
//        if (code == null) {
//            return null;
//        }
//        for (SysBuiltinGroupEnum groupEnum : values()) {
//            if (groupEnum.getCode().equals(code)) {
//                return groupEnum;
//            }
//        }
//        return null;
//    }
//}
