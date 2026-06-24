//package com.xspaceagi.system.spec.enums;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import lombok.Getter;
//
///**
// * 系统内置角色-菜单关联
// */
//@Getter
//public enum SysBuiltinRoleMenuEnum {
//
//    SUPER_ADMIN("super_admin", "root", BindTypeEnum.ALL),
//    NORMAL_USER("normal_user", "root", BindTypeEnum.ALL);
//
//    //PLACEHOLDER("__PLACEHOLDER__", "__PLACEHOLDER__", BindTypeEnum.NONE, BindTypeEnum.NONE);
//
//    /**
//     * 角色编码
//     */
//    private final String roleCode;
//
//    /**
//     * 菜单编码
//     */
//    private final String menuCode;
//
//    /**
//     * 子菜单绑定类型
//     */
//    private final BindTypeEnum menuBindType;
//
//    SysBuiltinRoleMenuEnum(String roleCode, String menuCode, BindTypeEnum menuBindType) {
//        this.roleCode = roleCode;
//        this.menuCode = menuCode;
//        this.menuBindType = menuBindType;
//    }
//
//    /**
//     * 根据角色编码获取关联的菜单列表
//     */
//    public static List<SysBuiltinRoleMenuEnum> getByRoleCode(String roleCode) {
//        if (roleCode == null) {
//            return List.of();
//        }
//        return Arrays.stream(values())
//                .filter(enumItem -> enumItem.getRoleCode().equals(roleCode))
//                .filter(enumItem -> !"__PLACEHOLDER__".equals(enumItem.getRoleCode()))
//                .collect(Collectors.toList());
//    }
//}
