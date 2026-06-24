package com.xspaceagi.agent.core.adapter.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("card")
public class Card {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id; // 卡片ID

    private String cardKey; // 卡片唯一标识，与前端组件做关联

    private String name; // 卡片名称

    private String imageUrl; // 卡片示例图片地址

    private String args; // 卡片参数

    private Date modified; // 更新时间

    private Date created; // 创建时间
}
