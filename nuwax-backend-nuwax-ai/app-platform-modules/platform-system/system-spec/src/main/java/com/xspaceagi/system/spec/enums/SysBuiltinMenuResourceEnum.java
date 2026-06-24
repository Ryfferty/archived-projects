//package com.xspaceagi.system.spec.enums;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import lombok.Getter;
//
///**
// * 系统内置菜单-资源关联
// */
//@Getter
//public enum SysBuiltinMenuResourceEnum {
//
//    ROOT(SysBuiltinMenuEnum.ROOT.getCode(), SysBuiltinResourceEnum.ROOT.getCode(), BindTypeEnum.ALL),
//
//    // 系统管理
//    //SYS_MANAGE_RESOURCE("sys_manage", "sys_manage", BindTypeEnum.ALL),
//
//    // 用户管理
//    SYS_USER_MANAGE_RESOURCE(SysBuiltinMenuEnum.SYS_USER_MANAGE.getCode(), SysBuiltinResourceEnum.SYS_USER_MANAGE.getCode(), BindTypeEnum.ALL),
////    SYS_USER_MANAGE_QUERY(SysBuiltinMenuEnum.SYS_USER_MANAGE.getCode(), SysBuiltinResourceEnum.SYS_USER_QUERY.getCode(), BindTypeEnum.ALL),
////    SYS_USER_MANAGE_ADD(SysBuiltinMenuEnum.SYS_USER_MANAGE.getCode(), SysBuiltinResourceEnum.SYS_USER_ADD.getCode(), BindTypeEnum.ALL),
////    SYS_USER_MANAGE_EDIT(SysBuiltinMenuEnum.SYS_USER_MANAGE.getCode(), SysBuiltinResourceEnum.SYS_USER_EDIT.getCode(), BindTypeEnum.ALL),
////    SYS_USER_MANAGE_DELETE(SysBuiltinMenuEnum.SYS_USER_MANAGE.getCode(), SysBuiltinResourceEnum.SYS_USER_DELETE.getCode(), BindTypeEnum.ALL),
////    SYS_USER_MANAGE_ENABLE(SysBuiltinMenuEnum.SYS_USER_MANAGE.getCode(), SysBuiltinResourceEnum.SYS_USER_ENABLE.getCode(), BindTypeEnum.ALL),
//
//    // 发布管理
//    SYS_PUBLISH_MANAGE(SysBuiltinMenuEnum.SYS_PUBLISH_MANAGE.getCode(), SysBuiltinResourceEnum.SYS_PUBLISH_MANAGE.getCode(), BindTypeEnum.ALL),
//
//    // 发布审核
////    SYS_PUBLISH_APPROVE_RESOURCE(SysBuiltinMenuEnum.SYS_PUBLISH_APPROVE.getCode(), SysBuiltinResourceEnum.SYS_PUBLISH_APPROVE.getCode(), BindTypeEnum.ALL),
////    SYS_PUBLISH_APPROVE_QUERY(SysBuiltinMenuEnum.SYS_PUBLISH_APPROVE.getCode(), SysBuiltinResourceEnum.SYS_PUBLISH_APPROVE_QUERY.getCode(), BindTypeEnum.ALL),
////    SYS_PUBLISH_APPROVE_SUBMIT(SysBuiltinMenuEnum.SYS_PUBLISH_APPROVE.getCode(), SysBuiltinResourceEnum.SYS_PUBLISH_APPROVE_SUBMIT.getCode(), BindTypeEnum.ALL),
//
//    // 已发布管理
////    SYS_PUBLISHED_RESOURCE(SysBuiltinMenuEnum.SYS_PUBLISHED.getCode(), SysBuiltinResourceEnum.SYS_PUBLISHED.getCode(), BindTypeEnum.ALL),
//
//    // 模型管理
//    SYS_MODEL_MANAGE_RESOURCE(SysBuiltinMenuEnum.SYS_MODEL_MANAGE.getCode(), SysBuiltinResourceEnum.SYS_MODEL_MANAGE.getCode(), BindTypeEnum.ALL),
//
//    // 首页
//    HOMEPAGE_RESOURCE(SysBuiltinMenuEnum.HOMEPAGE.getCode(), SysBuiltinResourceEnum.HOMEPAGE.getCode(), BindTypeEnum.ALL),
//
//    // 工作空间
//    WORKSPACE_RESOURCE(SysBuiltinMenuEnum.WORKSPACE.getCode(), SysBuiltinResourceEnum.WORKSPACE.getCode(), BindTypeEnum.ALL),
//
//    // 智能体开发
//    AGENT_DEV_RESOURCE(SysBuiltinMenuEnum.AGENT_DEV.getCode(), SysBuiltinResourceEnum.AGENT_DEV.getCode(), BindTypeEnum.ALL),
////    AGENT_DEV_ADD(SysBuiltinMenuEnum.AGENT_DEV.getCode(), SysBuiltinResourceEnum.AGENT_ADD.getCode(), BindTypeEnum.ALL),
////    AGENT_DEV_EDIT(SysBuiltinMenuEnum.AGENT_DEV.getCode(), SysBuiltinResourceEnum.AGENT_EDIT.getCode(), BindTypeEnum.ALL),
////    AGENT_DEV_DELETE(SysBuiltinMenuEnum.AGENT_DEV.getCode(), SysBuiltinResourceEnum.AGENT_DELETE.getCode(), BindTypeEnum.ALL),
////    AGENT_DEV_IMPORT(SysBuiltinMenuEnum.AGENT_DEV.getCode(), SysBuiltinResourceEnum.AGENT_IMPORT.getCode(), BindTypeEnum.ALL),
////    AGENT_DEV_EXPORT(SysBuiltinMenuEnum.AGENT_DEV.getCode(), SysBuiltinResourceEnum.AGENT_EXPORT.getCode(), BindTypeEnum.ALL),
////    AGENT_DEV_PUBLISH(SysBuiltinMenuEnum.AGENT_DEV.getCode(), SysBuiltinResourceEnum.AGENT_PUBLISH.getCode(), BindTypeEnum.ALL),
////    AGENT_DEV_COPY(SysBuiltinMenuEnum.AGENT_DEV.getCode(), SysBuiltinResourceEnum.AGENT_COPY.getCode(), BindTypeEnum.ALL),
////    AGENT_DEV_MIGRATE(SysBuiltinMenuEnum.AGENT_DEV.getCode(), SysBuiltinResourceEnum.AGENT_MIGRATE.getCode(), BindTypeEnum.ALL),
//
//    // 网页应用开发
//    PAGE_APP_DEV_RESOURCE(SysBuiltinMenuEnum.PAGE_APP_DEV.getCode(), SysBuiltinResourceEnum.PAGE_APP_DEV.getCode(), BindTypeEnum.ALL),
////    PAGE_APP_DEV_ADD(SysBuiltinMenuEnum.PAGE_APP_DEV.getCode(), SysBuiltinResourceEnum.PAGE_APP_ADD.getCode(), BindTypeEnum.ALL),
////    PAGE_APP_DEV_EDIT(SysBuiltinMenuEnum.PAGE_APP_DEV.getCode(), SysBuiltinResourceEnum.PAGE_APP_EDIT.getCode(), BindTypeEnum.ALL),
////    PAGE_APP_DEV_DELETE(SysBuiltinMenuEnum.PAGE_APP_DEV.getCode(), SysBuiltinResourceEnum.PAGE_APP_DELETE.getCode(), BindTypeEnum.ALL),
////    PAGE_APP_DEV_IMPORT(SysBuiltinMenuEnum.PAGE_APP_DEV.getCode(), SysBuiltinResourceEnum.PAGE_APP_IMPORT.getCode(), BindTypeEnum.ALL),
////    PAGE_APP_DEV_EXPORT(SysBuiltinMenuEnum.PAGE_APP_DEV.getCode(), SysBuiltinResourceEnum.PAGE_APP_EXPORT.getCode(), BindTypeEnum.ALL),
////    PAGE_APP_DEV_PUBLISH(SysBuiltinMenuEnum.PAGE_APP_DEV.getCode(), SysBuiltinResourceEnum.PAGE_APP_PUBLISH.getCode(), BindTypeEnum.ALL),
////    PAGE_APP_DEV_COPY(SysBuiltinMenuEnum.PAGE_APP_DEV.getCode(), SysBuiltinResourceEnum.PAGE_APP_COPY.getCode(), BindTypeEnum.ALL),
////    PAGE_APP_DEV_CONFIG(SysBuiltinMenuEnum.PAGE_APP_DEV.getCode(), SysBuiltinResourceEnum.PAGE_APP_CONFIG.getCode(), BindTypeEnum.ALL),
//
//    // 组件库
//    COMPONENT_DEV_RESOURCE(SysBuiltinMenuEnum.COMPONENT_DEV.getCode(), SysBuiltinResourceEnum.COMPONENT_DEV.getCode(), BindTypeEnum.ALL),
////    COMPONENT_DEV_ADD(SysBuiltinMenuEnum.COMPONENT_DEV.getCode(), SysBuiltinResourceEnum.COMPONENT_ADD.getCode(), BindTypeEnum.ALL),
////    COMPONENT_DEV_EDIT(SysBuiltinMenuEnum.COMPONENT_DEV.getCode(), SysBuiltinResourceEnum.COMPONENT_EDIT.getCode(), BindTypeEnum.ALL),
////    COMPONENT_DEV_DELETE(SysBuiltinMenuEnum.COMPONENT_DEV.getCode(), SysBuiltinResourceEnum.COMPONENT_DELETE.getCode(), BindTypeEnum.ALL),
////    COMPONENT_DEV_IMPORT(SysBuiltinMenuEnum.COMPONENT_DEV.getCode(), SysBuiltinResourceEnum.COMPONENT_IMPORT.getCode(), BindTypeEnum.ALL),
////    COMPONENT_DEV_EXPORT(SysBuiltinMenuEnum.COMPONENT_DEV.getCode(), SysBuiltinResourceEnum.COMPONENT_EXPORT.getCode(), BindTypeEnum.ALL),
////    COMPONENT_DEV_PUBLISH(SysBuiltinMenuEnum.COMPONENT_DEV.getCode(), SysBuiltinResourceEnum.COMPONENT_PUBLISH.getCode(), BindTypeEnum.ALL),
////    COMPONENT_DEV_COPY(SysBuiltinMenuEnum.COMPONENT_DEV.getCode(), SysBuiltinResourceEnum.COMPONENT_COPY.getCode(), BindTypeEnum.ALL),
//
//    // 技能管理
//    SKILL_DEV_RESOURCE(SysBuiltinMenuEnum.SKILL_DEV.getCode(), SysBuiltinResourceEnum.SKILL_DEV.getCode(), BindTypeEnum.ALL),
////    SKILL_DEV_ADD(SysBuiltinMenuEnum.SKILL_DEV.getCode(), SysBuiltinResourceEnum.SKILL_ADD.getCode(), BindTypeEnum.ALL),
////    SKILL_DEV_EDIT(SysBuiltinMenuEnum.SKILL_DEV.getCode(), SysBuiltinResourceEnum.SKILL_EDIT.getCode(), BindTypeEnum.ALL),
////    SKILL_DEV_DELETE(SysBuiltinMenuEnum.SKILL_DEV.getCode(), SysBuiltinResourceEnum.SKILL_DELETE.getCode(), BindTypeEnum.ALL),
////    SKILL_DEV_IMPORT(SysBuiltinMenuEnum.SKILL_DEV.getCode(), SysBuiltinResourceEnum.SKILL_IMPORT.getCode(), BindTypeEnum.ALL),
////    SKILL_DEV_EXPORT(SysBuiltinMenuEnum.SKILL_DEV.getCode(), SysBuiltinResourceEnum.SKILL_EXPORT.getCode(), BindTypeEnum.ALL),
////    SKILL_DEV_PUBLISH(SysBuiltinMenuEnum.SKILL_DEV.getCode(), SysBuiltinResourceEnum.SKILL_PUBLISH.getCode(), BindTypeEnum.ALL),
////    SKILL_DEV_COPY(SysBuiltinMenuEnum.SKILL_DEV.getCode(), SysBuiltinResourceEnum.SKILL_COPY.getCode(), BindTypeEnum.ALL),
//
//    // MCP管理
//    MCP_DEV_RESOURCE(SysBuiltinMenuEnum.MCP_DEV.getCode(), SysBuiltinResourceEnum.MCP_DEV.getCode(), BindTypeEnum.ALL),
//
//    // 任务中心
//    TASK_MANAGE_RESOURCE(SysBuiltinMenuEnum.TASK_MANAGE.getCode(), SysBuiltinResourceEnum.TASK_MANAGE.getCode(), BindTypeEnum.ALL),
//
//    // 日志查询
//    LOG_MANAGE_RESOURCE(SysBuiltinMenuEnum.LOG_MANAGE.getCode(), SysBuiltinResourceEnum.LOG_MANAGE.getCode(), BindTypeEnum.ALL),
//
//    // 空间广场
//    SPACE_SQUARE_RESOURCE(SysBuiltinMenuEnum.SPACE_SQUARE.getCode(), SysBuiltinResourceEnum.SPACE_SQUARE.getCode(), BindTypeEnum.ALL),
//
//    // 广场
//    SQUARE_RESOURCE(SysBuiltinMenuEnum.SQUARE.getCode(), SysBuiltinResourceEnum.SQUARE.getCode(), BindTypeEnum.ALL),
//
//    // 生态市场
//    ECO_MARKET_RESOURCE(SysBuiltinMenuEnum.ECO_MARKET.getCode(), SysBuiltinResourceEnum.ECO_MARKET.getCode(), BindTypeEnum.ALL);
//
//    //PLACEHOLDER("__PLACEHOLDER__", "__PLACEHOLDER__", BindTypeEnum.NONE);
//
//    /**
//     * 菜单编码
//     */
//    private final String menuCode;
//
//    /**
//     * 资源编码
//     */
//    private final String resourceCode;
//
//    /**
//     * 资源绑定类型
//     */
//    private final BindTypeEnum resourceBindType;
//
//    SysBuiltinMenuResourceEnum(String menuCode, String resourceCode, BindTypeEnum resourceBindType) {
//        this.menuCode = menuCode;
//        this.resourceCode = resourceCode;
//        this.resourceBindType = resourceBindType;
//    }
//
//    /**
//     * 根据菜单编码获取关联的资源列表
//     */
//    public static List<SysBuiltinMenuResourceEnum> getByMenuCode(String menuCode) {
//        if (menuCode == null) {
//            return List.of();
//        }
//        return Arrays.stream(values())
//                .filter(enumItem -> enumItem.getMenuCode().equals(menuCode))
//                .filter(enumItem -> !"__PLACEHOLDER__".equals(enumItem.getMenuCode()))
//                .collect(Collectors.toList());
//    }
//}
