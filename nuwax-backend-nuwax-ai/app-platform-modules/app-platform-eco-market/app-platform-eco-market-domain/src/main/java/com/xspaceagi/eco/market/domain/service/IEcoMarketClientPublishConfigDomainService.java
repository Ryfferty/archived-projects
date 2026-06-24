package com.xspaceagi.eco.market.domain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xspaceagi.eco.market.domain.dto.req.UpdateAndEnableConfigReqDTO;
import com.xspaceagi.eco.market.domain.model.EcoMarketClientPublishConfigModel;
import com.xspaceagi.eco.market.domain.model.valueobj.QueryEcoMarketVo;
import com.xspaceagi.eco.market.spec.enums.EcoMarketDataTypeEnum;
import com.xspaceagi.system.spec.common.UserContext;

import java.util.List;

public interface IEcoMarketClientPublishConfigDomainService {

    /**
     * 已发布配置详情
     *
     * @param id 配置id
     */
    EcoMarketClientPublishConfigModel queryOneInfoById(Long id);

    /**
     * 根据uid查询已发布配置详情
     *
     * @param uid 配置唯一标识
     * @return 已发布配置详情
     */
    EcoMarketClientPublishConfigModel queryOneByUid(String uid);

    /**
     * 批量根据ids查询已发布配置
     * 
     * @param ids id列表
     * @return 已发布配置列表
     */
    List<EcoMarketClientPublishConfigModel> queryListByIds(List<Long> ids);
    /**
     * 更新
     *
     * @param model 模型
     * @return id
     */
    Long updateInfo(EcoMarketClientPublishConfigModel model, UserContext userContext);

    /**
     * 新增
     */
    Long addInfo(EcoMarketClientPublishConfigModel model, UserContext userContext);


    /**
     * 保存或更新发布配置记录
     * 如果存在相同UID的记录先删除再新增
     *
     * @param model 配置模型
     * @param userContext 用户上下文
     * @return 记录ID
     */
    Long saveOrUpdateByUid(EcoMarketClientPublishConfigModel model, UserContext userContext);

    /**
     * 启用配置
     *
     * @param uid 配置唯一标识
     * @param userContext 用户上下文
     * @return 启用后的配置模型
     */
    EcoMarketClientPublishConfigModel enableConfig(String uid, UserContext userContext);

    /**
     * 禁用配置
     *
     * @param uid 配置唯一标识
     * @param userContext 用户上下文
     * @return 禁用后的配置模型
     */
    EcoMarketClientPublishConfigModel disableConfig(String uid, UserContext userContext);

    /**
     * 更新并启用配置
     *
     * @param request 更新配置参数
     * @param userContext 用户上下文
     * @return 启用后的配置模型
     */
    EcoMarketClientPublishConfigModel updateAndEnableConfig(UpdateAndEnableConfigReqDTO request, UserContext userContext);



        /**
     * 分页查询启用状态的配置
     *
     * @param current 当前页
     * @param size 每页大小
     * @param queryEcoMarketVo 参数搜索配置
     * @return 启用状态的配置分页结果
     */
    IPage<EcoMarketClientPublishConfigModel> pageQueryEnabled(QueryEcoMarketVo queryEcoMarketVo, long current, long size);


    /**
     * 根据uids查询启用状态的配置
     *
     * @param uids 配置唯一标识列表
     * @return 启用状态的配置列表
     */
    List<EcoMarketClientPublishConfigModel> queryListByUids(List<String> uids);


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
