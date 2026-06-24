//package com.xspaceagi.system.spec.enums;
//
//import lombok.Getter;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * 系统内置用户组-菜单关联
// */
//@Getter
//public enum SysBuiltinGroupMenuEnum {
//
//     GROUP_MENU_EXAMPLE("default_group", "root", BindTypeEnum.ALL);
//
//    //PLACEHOLDER("__PLACEHOLDER__", "__PLACEHOLDER__", BindTypeEnum.NONE, BindTypeEnum.NONE);
//
//    /**
//     * 用户组编码
//     */
//    private final String groupCode;
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
//    SysBuiltinGroupMenuEnum(String groupCode, String menuCode, BindTypeEnum menuBindType) {
//        this.groupCode = groupCode;
//        this.menuCode = menuCode;
//        this.menuBindType = menuBindType;
//    }
//
//    /**
//     * 根据用户组编码获取关联的菜单列表
//     */
//    public static List<SysBuiltinGroupMenuEnum> getByGroupCode(String groupCode) {
//        if (groupCode == null) {
//            return List.of();
//        }
//        return Arrays.stream(values())
//                .filter(enumItem -> enumItem.getGroupCode().equals(groupCode))
//                .filter(enumItem -> !"__PLACEHOLDER__".equals(enumItem.getGroupCode()))
//                .collect(Collectors.toList());
//    }
//}
