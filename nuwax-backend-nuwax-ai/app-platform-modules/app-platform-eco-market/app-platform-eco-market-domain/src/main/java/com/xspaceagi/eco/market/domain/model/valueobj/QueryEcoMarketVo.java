package com.xspaceagi.eco.market.domain.model.valueobj;

import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于列表页,搜索本地数据库中的数据使用
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryEcoMarketVo {
    /**
     * 名称，模糊查询
     */
    private String name;

    /**
     * 细分类型,比如: 插件,智能体,工作流
     * @see Published.TargetType
     */
    private String targetType;

    /**
     * 子类型
     * @see Published.TargetSubType
     */
    private String targetSubType;

    /**
     * 市场类型,1:插件;2:模板;3:MCP
     */
    private Integer dataType;
    /**
     * tab类型: 1: 全部; 2: 启用的;3:我的分享; 默认全部
     */
    private Integer subTabType;
    /**
     * 分类编码
     */
    private String categoryCode;
    /**
     * 分享状态,1:草稿;2:审核中;3:已发布;4:已下线;5:驳回
     */
    private Integer shareStatus;

    /**
     * 使用状态,1:启用;2:禁用;
     */
    private Integer useStatus;

    /**
     * 是否我的分享,0:否(生态市场获取的);1:是(我的分享)
     */
    private Integer ownedFlag;

}
