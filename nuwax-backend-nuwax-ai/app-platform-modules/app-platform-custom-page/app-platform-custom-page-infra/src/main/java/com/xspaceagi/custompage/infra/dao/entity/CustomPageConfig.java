package com.xspaceagi.custompage.infra.dao.entity;

import java.util.Date;
import java.util.List;

import com.xspaceagi.custompage.sdk.dto.*;
import org.apache.ibatis.type.EnumTypeHandler;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xspaceagi.custompage.infra.dao.typehandler.DataSourceListTypeHandler;
import com.xspaceagi.custompage.infra.dao.typehandler.PageArgConfigListTypeHandler;
import com.xspaceagi.custompage.infra.dao.typehandler.ProxyConfigListTypeHandler;
import com.xspaceagi.system.spec.common.JsonTypeHandlerWithoutType;

import lombok.Data;

@TableName(value = "custom_page_config", autoResultMap = true)
@Data
public class CustomPageConfig {

    private Long id;

    private String name;

    private String description;

    private String icon;

    private String coverImg;

    @TableField(value = "cover_img_source_type", typeHandler = EnumTypeHandler.class)
    private SourceTypeEnum coverImgSourceType;

    // 自定义页面唯一标识
    private String basePath;

    // 1:已发布; 0:未发布
    private Integer buildRunning;

    // 发布类型 (数据库中存储为varchar类型，存储枚举名称字符串)
    @TableField(value = "publish_type", typeHandler = EnumTypeHandler.class)
    private PublishTypeEnum publishType;

    // 需要登录,1:需要
    private Integer needLogin;

    // 调试关联智能体
    private Long devAgentId;

    // 页面项目类型 (数据库中存储为varchar类型，存储枚举名称字符串)
    @TableField(value = "project_type", typeHandler = EnumTypeHandler.class)
    private ProjectType projectType;

    @TableField(value = "proxy_config", typeHandler = ProxyConfigListTypeHandler.class)
    private List<ProxyConfig> proxyConfigs;

    @TableField(value = "page_arg_config", typeHandler = PageArgConfigListTypeHandler.class)
    private List<PageArgConfig> pageArgConfigs;

    @TableField(value = "data_sources", typeHandler = DataSourceListTypeHandler.class)
    private List<DataSourceDto> dataSources;

    private Long sandboxId;

    @TableField(value = "ext", typeHandler = JsonTypeHandlerWithoutType.class)
    private Object ext;

    @TableField(value = "_tenant_id")
    private Long tenantId;

    private Long spaceId;

    private Date created;
    private Long creatorId;
    private String creatorName;
    private Date modified;
    private Long modifiedId;
    private String modifiedName;

    private Integer yn;

    // public enum ProjectType {
    // // 纯反向代理
    // REVERSE_PROXY,

    // // 在线开发部署
    // ONLINE_DEPLOY,
    // // 两种类型都有代理配置，只是 ONLINE_DEPLOY 不可配置根目录"/"的代理，根目录由系统自动分配
    // }

    // @Data
    // public static class PageArgConfig implements Serializable {

    // @Schema(description = "页面路径，例如 /view")
    // private String pageUri;

    // @Schema(description = "页面名称")
    // private String name;

    // @Schema(description = "页面描述")
    // private String description;

    // @Schema(description = "参数配置")
    // private List<PageArg> args;
    // }

    // @Data
    // public static class PageArg implements Serializable {
    // @Schema(description = "参数key，唯一标识，不需要前端传递，后台根据配置自动生成")
    // private String key;

    // @Schema(description = "参数名称，符合函数命名规则")
    // private String name;

    // @Schema(description = "参数详细描述信息")
    // private String description;

    // @Schema(description = "数据类型")
    // private DataTypeEnum dataType;

    // @Schema(description = "是否必须")
    // private boolean require;

    // @Schema(description = "下级参数")
    // private List<PageArg> subArgs;
    // }

    // public enum DataTypeEnum {
    // String, // 文本
    // Integer, // 整型数字
    // Number, // 数字
    // Boolean, // 布尔
    // Object, // 对象
    // Array_String, // String数组
    // Array_Integer, // Integer数组
    // Array_Number, // Number数组
    // Array_Boolean, // Boolean数组
    // Array_Object,// Object数组
    // }

    // @Data
    // @Builder
    // @AllArgsConstructor
    // @NoArgsConstructor
    // public static class ProxyConfig {
    // // 环境，比如 dev 为开发环境；prod 为生产环境
    // private ProxyEnv env;
    // // 路径，比如 /education代表/education后的所有请求都会被代理到${backends}
    // private String path;
    // private List<ProxyConfigBackend> backends;
    // private String healthCheckPath;
    // // 是否必须登录，默认true
    // private boolean requireAuth;

    // public enum ProxyEnv {
    // dev, prod;

    // public static ProxyEnv get(String value) {
    // for (ProxyEnv env : values()) {
    // if (env.name().equals(value)) {
    // return env;
    // }
    // }
    // return null;
    // }
    // }
    // }

    // @Data
    // public static class ProxyConfigBackend {
    // // http://192.168.1.34:3001/[xxx]
    // private String backend;
    // private int weight;

    // public ProxyConfigBackend() {
    // }

    // public ProxyConfigBackend(String backend, int weight) {
    // this.backend = backend;
    // this.weight = weight;
    // }
    // }
}
