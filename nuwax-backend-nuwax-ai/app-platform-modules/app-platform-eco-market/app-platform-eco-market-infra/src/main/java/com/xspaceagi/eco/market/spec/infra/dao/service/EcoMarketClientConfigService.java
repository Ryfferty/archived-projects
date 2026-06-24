package com.xspaceagi.eco.market.spec.infra.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xspaceagi.eco.market.spec.enums.EcoMarketDataTypeEnum;
import com.xspaceagi.eco.market.spec.infra.dao.entity.EcoMarketClientConfig;

import java.util.List;

/**
 * @author soddy
 * @description 针对表【eco_market_client_config(生态市场配置)】的数据库操作Service
 * @createDate 2025-05-26 17:08:22
 */
public interface EcoMarketClientConfigService extends IService<EcoMarketClientConfig> {

    /**
     * 根据ID集合查询列表
     *
     * @param ids id集合
     * @return 列表
     */
    List<EcoMarketClientConfig> queryListByIds(List<Long> ids);

    /**
     * 根据主键查询 单条记录
     * 
     * @param id id
     * @return 单条记录
     */
    EcoMarketClientConfig queryOneInfoById(Long id);

    /**
     * 更新
     *
     * @param entity entity
     * @return id
     */
    Long updateInfo(EcoMarketClientConfig entity);

    /**
     * 新增
     */
    Long addInfo(EcoMarketClientConfig entity);

    /**
     * 删除根据主键id
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 根据uid查询单条记录
     *
     * @param uid uid
     * @return 单条记录
     */
    EcoMarketClientConfig queryOneByUid(String uid);

    /**
     * 根据uid集合查询列表
     *
     * @param uids uid集合
     * @return 列表
     */
    List<EcoMarketClientConfig> queryListByUids(List<String> uids);

    /**
     * 根据uid更新分享状态
     *
     * @param uid         uid
     * @param shareStatus 分享状态
     * @param modifiedId  修改人ID
     * @return 更新结果
     */
    boolean updateShareStatusByUid(String uid, Integer shareStatus, String approveMessage, Long modifiedId);


    /**
     * 根据uid删除配置
     * 
     * @param uid 配置唯一标识
     */
    void deleteByUid(String uid);

    /**
     * 查询我的分享总数
     * 
     * @return 总数
     */
    Long queryTotalMyShare();


    /**
     * 检查配置是否重复
     * 
     * @param targetId        目标id
     * @param targetType      目标类型
     * @param dataTypeEnum    数据类型
     * @param uid             配置唯一标识(可选,用于排除自身)
     * @return 是否重复
     */
    boolean checkConfigRepeat(Long targetId, String targetType, EcoMarketDataTypeEnum dataTypeEnum, String uid);

    /**
     * 查询我的分享和审核中的配置
     * 
     * @return 配置列表
     */
    List<EcoMarketClientConfig> queryMyShareAndReviewing();

}
