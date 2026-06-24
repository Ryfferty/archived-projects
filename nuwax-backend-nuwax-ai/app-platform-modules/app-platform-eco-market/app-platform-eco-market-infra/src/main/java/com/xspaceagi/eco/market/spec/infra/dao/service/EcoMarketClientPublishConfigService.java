package com.xspaceagi.eco.market.spec.infra.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xspaceagi.eco.market.spec.enums.EcoMarketDataTypeEnum;
import com.xspaceagi.eco.market.spec.infra.dao.entity.EcoMarketClientPublishConfig;

import java.util.List;

/**
 * @author soddy
 * @description 针对表【eco_market_client_publish_config(生态市场,客户端,已发布配置)】的数据库操作Service
 * @createDate 2025-05-26 17:08:22
 */
public interface EcoMarketClientPublishConfigService extends IService<EcoMarketClientPublishConfig> {

    /**
     * 根据ID集合查询列表
     *
     * @param ids id集合
     * @return 列表
     */
    List<EcoMarketClientPublishConfig> queryListByIds(List<Long> ids);


    /**
     * 根据主键查询 单条记录
     * 
     * @param id id
     * @return 单条记录
     */
    EcoMarketClientPublishConfig queryOneInfoById(Long id);

    /**
     * 根据uid查询单条记录
     *
     * @param uid 配置唯一标识
     * @return 单条记录
     */
    EcoMarketClientPublishConfig queryOneByUid(String uid);

    /**
     * 更新
     *
     * @param entity entity
     * @return id
     */
    Long updateInfo(EcoMarketClientPublishConfig entity);

    /**
     * 新增
     */
    Long addInfo(EcoMarketClientPublishConfig entity);

    /**
     * 删除根据主键id
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 根据uid删除
     *
     * @param uid uid
     */
    void deleteByUid(String uid);

    /**
     * 根据uids查询启用状态的配置
     *
     * @param uids 配置唯一标识列表
     * @return 启用状态的配置列表
     */
    List<EcoMarketClientPublishConfig> queryListByUids(List<String> uids);


    /**
     * 检查配置是否重复
     *
     * @param targetId     目标id
     * @param targetType   目标类型
     * @param dataTypeEnum 数据类型
     * @return 是否重复
     */
    boolean checkConfigRepeat(Long targetId, String targetType, EcoMarketDataTypeEnum dataTypeEnum);


}
