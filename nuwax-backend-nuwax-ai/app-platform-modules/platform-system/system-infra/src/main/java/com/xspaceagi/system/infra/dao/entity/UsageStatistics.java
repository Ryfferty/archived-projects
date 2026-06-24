package com.xspaceagi.system.infra.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用量统计表
 * 支持按用户、租户、资源类型、时间等维度统计用量
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "usage_statistics", autoResultMap = true)
public class UsageStatistics {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 租户ID
     */
    @TableField(value = "_tenant_id")
    private Long tenantId;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 资源类型：Agent、PageApp、Component、Mcp等
     */
    @TableField(value = "resource_type")
    private String resourceType;

    /**
     * 资源ID：具体的资源ID（如agent_id、page_app_id等）
     */
    @TableField(value = "resource_id")
    private String resourceId;

    /**
     * 用量类型
     * @see UsageType
     */
    @TableField(value = "usage_type")
    private String usageType;

    /**
     * 用量值
     */
    @TableField(value = "usage_value")
    private Long usageValue;

    /**
     * 统计日期（精确到天）
     */
    @TableField(value = "usage_date")
    private Date usageDate;

    /**
     * 统计年月（格式：YYYYMM，用于快速按月查询）
     */
    @TableField(value = "usage_month")
    private String usageMonth;

    /**
     * 统计年份（用于快速按年查询）
     */
    @TableField(value = "usage_year")
    private Integer usageYear;

    /**
     * 扩展信息（JSON格式，存储额外的维度信息）
     */
    @TableField(value = "extra_info")
    private String extraInfo;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date modified;

    /**
     * 用量类型枚举
     */
    public enum UsageType {

        // === 调用次数类 ===
        API_CALL_COUNT("apiCallCount", "API调用次数"),
        AGENT_CALL_COUNT("agentCallCount", "智能体调用次数"),
        PAGE_APP_CALL_COUNT("pageAppCallCount", "网页应用调用次数"),
        MCP_CALL_COUNT("mcpCallCount", "MCP调用次数"),

        // === Token消耗类 ===
        TOKEN_INPUT("tokenInput", "输入Token数"),
        TOKEN_OUTPUT("tokenOutput", "输出Token数"),
        TOKEN_TOTAL("tokenTotal", "总Token数"),

        // === 时长类 ===
        CALL_DURATION("callDuration", "调用时长(秒)"),
        TOTAL_DURATION("totalDuration", "总时长(秒)"),

        // === 用户行为类 ===
        USER_COUNT("userCount", "用户数"),
        VISIT_COUNT("visitCount", "访问次数"),
        LIKE_COUNT("likeCount", "点赞次数"),
        DISLIKE_COUNT("dislikeCount", "踩次数"),
        COLLECT_COUNT("collectCount", "收藏次数"),
        SHARE_COUNT("shareCount", "分享次数"),

        // === 消息类 ===
        MESSAGE_COUNT("messageCount", "消息数"),
        MESSAGE_USER("messageUser", "用户消息数"),
        MESSAGE_ASSISTANT("messageAssistant", "助手消息数"),

        // === 存储类 ===
        STORAGE_SIZE("storageSize", "存储大小(字节)"),
        FILE_COUNT("fileCount", "文件数量"),

        // === 错误类 ===
        ERROR_COUNT("errorCount", "错误次数"),
        FAIL_COUNT("failCount", "失败次数");

        private final String code;
        private final String description;

        UsageType(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public String getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static UsageType fromCode(String code) {
            for (UsageType type : values()) {
                if (type.getCode().equals(code)) {
                    return type;
                }
            }
            return null;
        }
    }

    /**
     * 资源类型枚举
     */
    public enum ResourceType {

        AGENT("Agent", "智能体"),
        PAGE_APP("PageApp", "网页应用"),
        COMPONENT("Component", "组件"),
        MCP("Mcp", "MCP服务"),
        WORKFLOW("Workflow", "工作流"),
        KNOWLEDGE_BASE("KnowledgeBase", "知识库"),
        DATASET("Dataset", "数据集");

        private final String code;
        private final String description;

        ResourceType(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public String getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static ResourceType fromCode(String code) {
            for (ResourceType type : values()) {
                if (type.getCode().equals(code)) {
                    return type;
                }
            }
            return null;
        }
    }
}