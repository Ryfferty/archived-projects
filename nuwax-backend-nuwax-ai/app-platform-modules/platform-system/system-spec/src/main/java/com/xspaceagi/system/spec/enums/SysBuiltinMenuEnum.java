//package com.xspaceagi.system.spec.enums;
//
//import lombok.Getter;
//
///**
// * 系统内置菜单枚举
// */
//@Getter
//public enum SysBuiltinMenuEnum {
//
//    ROOT("root", "根菜单", "根菜单", null, null, "https://avatars.githubusercontent.com/u/41285659", 0),
//
//    // 系统管理
//    SYS_MANAGE("sys_manage", "系统管理", "系统管理", "root", null, "https://avatars.githubusercontent.com/u/41285659", 0),
//
//    // 用户管理
//    SYS_USER_MANAGE("sys_user_manage", "用户管理", "用户管理", "sys_manage", null, "https://avatars.githubusercontent.com/u/41285659", 0),
//
//    // 发布管理
//    SYS_PUBLISH_MANAGE("sys_publish_manage", "发布管理", "发布管理", "sys_manage", null, "https://avatars.githubusercontent.com/u/41285659", 0),
//    SYS_PUBLISH_APPROVE("sys_publish_approve", "发布审核", "发布审核", "sys_publish_manage", null, "https://avatars.githubusercontent.com/u/41285659", 0),
//    SYS_PUBLISHED("sys_published", "已发布管理", "已发布管理", "sys_publish_manage", null, "https://avatars.githubusercontent.com/u/41285659", 0),
//
//    // 模型管理
//    SYS_MODEL_MANAGE("sys_model_manage", "模型管理", "模型管理", "sys_manage", null, "https://avatars.githubusercontent.com/u/41285659", 0),
//
//    // 系统配置
//
//    // 主题配置
//
//    // 系统概览
//
//    // 菜单权限
//    SYS_MENU_MANAGE("sys_menu_manage", "菜单权限", "菜单权限", "sys_manage", null, "https://avatars.githubusercontent.com/u/41285659", 0),
//    SYS_RESOURCE("sys_resource", "权限资源", "权限资源", "sys_manage", null, "https://avatars.githubusercontent.com/u/41285659", 0),
//
//    // 日志查询
//
//    // 内容管理
//
//    // 首页
//    HOMEPAGE("homepage", "首页", "首页", "root", null, "https://avatars.githubusercontent.com/u/41285659", 0),
//
//    // 工作空间
//    WORKSPACE("workspace", "工作空间", "工作空间", "root", null, "https://avatars.githubusercontent.com/u/41285659", 0),
//
//    AGENT_DEV("agent_dev", "智能体开发", "智能体开发", "workspace", null, "https://avatars.githubusercontent.com/u/41285659", 0),
//    PAGE_APP_DEV("page_app_dev", "网页应用开发", "网页应用开发", "workspace", null, "https://avatars.githubusercontent.com/u/41285659", 0),
//    COMPONENT_DEV("component_dev", "组件库", "组件库", "workspace", null, "https://avatars.githubusercontent.com/u/41285659", 0),
//    SKILL_DEV("skill_dev", "技能管理", "技能管理", "workspace", null, "https://avatars.githubusercontent.com/u/41285659", 0),
//    MCP_DEV("mcp_dev", "MCP管理", "MCP管理", "workspace", null, "https://avatars.githubusercontent.com/u/41285659", 0),
//    TASK_MANAGE("task_manage", "任务中心", "任务中心", "workspace", null, "https://avatars.githubusercontent.com/u/41285659", 0),
//    LOG_MANAGE("log_manage", "日志查询", "日志查询", "workspace", null, "https://avatars.githubusercontent.com/u/41285659", 0),
//    SPACE_SQUARE("space_square", "空间广场", "空间广场", "workspace", null, "https://avatars.githubusercontent.com/u/41285659", 0),
//
//    SQUARE("square", "广场", "广场", "root", null, "https://avatars.githubusercontent.com/u/41285659", 0),
//
//    ECO_MARKET("eco_market", "生态市场", "生态市场", "root", null, "https://avatars.githubusercontent.com/u/41285659", 0);
//
//    //PLACEHOLDER("__PLACEHOLDER__", "__占位符__", "__占位符__", null, "", "", 0);
//
//    /**
//     * 菜单编码
//     */
//    private final String code;
//
//    /**
//     * 菜单名称
//     */
//    private final String name;
//
//    /**
//     * 菜单描述
//     */
//    private final String description;
//
//    /**
//     * 父级菜单编码（如果为 null 或空字符串，则表示根菜单）
//     */
//    private final String parentCode;
//
//    /**
//     * 访问路径/路由地址
//     */
//    private final String path;
//
//    /**
//     * 图标
//     */
//    private final String icon;
//
//    /**
//     * 排序索引
//     */
//    private final Integer sortIndex;
//
//    SysBuiltinMenuEnum(String code, String name, String description, String parentCode, String path, String icon, Integer sortIndex) {
//        this.code = code;
//        this.name = name;
//        this.description = description;
//        this.parentCode = parentCode;
//        this.path = path;
//        this.icon = icon;
//        this.sortIndex = sortIndex;
//    }
//
//    /**
//     * 根据code获取枚举
//     */
//    public static SysBuiltinMenuEnum getByCode(String code) {
//        if (code == null) {
//            return null;
//        }
//        for (SysBuiltinMenuEnum menuEnum : values()) {
//            if (menuEnum.getCode().equals(code)) {
//                return menuEnum;
//            }
//        }
//        return null;
//    }
//}
