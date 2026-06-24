//package com.xspaceagi.system.spec.enums;
//
//import lombok.Getter;
//
///**
// * 系统内置角色
// */
//@Getter
//public enum SysBuiltinRoleEnum {
//
//    SUPER_ADMIN("super_admin", "超级管理员", "系统超级管理员，拥有所有权限", 1),
//    NORMAL_USER("normal_user", "普通用户", "系统普通用户", 2);
//
//    /**
//     * 角色编码
//     */
//    private final String code;
//
//    /**
//     * 角色名称
//     */
//    private final String name;
//
//    /**
//     * 角色描述
//     */
//    private final String description;
//
//    /**
//     * 排序索引
//     */
//    private final Integer sortIndex;
//
//    SysBuiltinRoleEnum(String code, String name, String description, Integer sortIndex) {
//        this.code = code;
//        this.name = name;
//        this.description = description;
//        this.sortIndex = sortIndex;
//    }
//
//    /**
//     * 根据code获取枚举
//     */
//    public static SysBuiltinRoleEnum getByCode(String code) {
//        if (code == null) {
//            return null;
//        }
//        for (SysBuiltinRoleEnum roleEnum : values()) {
//            if (roleEnum.getCode().equals(code)) {
//                return roleEnum;
//            }
//        }
//        return null;
//    }
//}
