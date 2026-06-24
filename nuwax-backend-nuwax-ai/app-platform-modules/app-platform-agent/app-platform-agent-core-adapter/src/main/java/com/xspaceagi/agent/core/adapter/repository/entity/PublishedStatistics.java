package com.xspaceagi.agent.core.adapter.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("published_statistics")
public class PublishedStatistics {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "_tenant_id")
    private Long tenantId; // 商户ID

    @TableField("target_type")
    private Published.TargetType targetType;

    @TableField("target_id")
    private Long targetId;

    private String name;
    private Long value;
    private Date modified; // 更新时间
    private Date created; // 创建时间

    /**
     * userCount, // 用户人数
     * convCount, // 会话次数
     * collectCount, // 收藏次数
     * likeCount // 点赞次数
     */
    public enum Key {

        USER_COUNT("userCount"),
        CONV_COUNT("convCount"),
        COLLECT_COUNT("collectCount"),
        LIKE_COUNT("likeCount"),
        //引用次数
        REFERENCE_COUNT("referenceCount"),

        //调用总次数
        CALL_COUNT("callCount"),

        //失败调用次数
        FAIL_CALL_COUNT("failCallCount"),

        //调用总时长
        TOTAL_CALL_DURATION("totalCallDuration");

        private String key;

        Key(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
}
