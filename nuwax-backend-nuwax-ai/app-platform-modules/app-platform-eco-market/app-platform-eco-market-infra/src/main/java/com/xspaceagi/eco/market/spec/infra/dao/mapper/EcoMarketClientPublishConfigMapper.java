package com.xspaceagi.eco.market.spec.infra.dao.mapper;

import com.xspaceagi.eco.market.spec.infra.dao.entity.EcoMarketClientPublishConfig;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;

/**
 * @author soddy
 * @description 针对表【eco_market_client_publish_config(生态市场,客户端,已发布配置)】的数据库操作Mapper
 * @createDate 2025-05-26 17:08:22
 * @Entity com.xspaceagi.infra.dao.entity.EcoMarketClientPublishConfig
 */
public interface EcoMarketClientPublishConfigMapper extends BaseMapper<EcoMarketClientPublishConfig> {

    /**
     * 总条数查询
     *
     * @param queryMap 筛选条件
     * @return 总条数
     */
    Long queryTotal(@Param("queryMap") Map<String, Object> queryMap);

    /**
     * 列表查询
     *
     * @param queryMap     筛选条件
     * @param orderColumns 排序
     * @param startIndex   索引开始位置
     * @param pageSize     业务大小
     * @return 列表
     */
    List<EcoMarketClientPublishConfig> queryList(@Param("queryMap") Map<String, Object> queryMap,
            @Param("orderColumns") List<OrderItem> orderColumns,
            @Param("startIndex") Long startIndex, @Param("pageSize") Long pageSize);

}
