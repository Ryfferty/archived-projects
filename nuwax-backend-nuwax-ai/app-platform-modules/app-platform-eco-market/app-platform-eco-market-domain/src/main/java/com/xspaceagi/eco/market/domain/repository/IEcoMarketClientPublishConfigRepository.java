package com.xspaceagi.eco.market.domain.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xspaceagi.eco.market.domain.model.EcoMarketClientPublishConfigModel;
import com.xspaceagi.eco.market.domain.model.valueobj.QueryEcoMarketVo;
import com.xspaceagi.eco.market.spec.enums.EcoMarketDataTypeEnum;
import com.xspaceagi.system.spec.common.UserContext;

import java.util.List;

public interface IEcoMarketClientPublishConfigRepository {

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
     * 生态市场,用户分页查询结果
     * 
     * @param queryEcoMarketVo 查询条件
     * @param current 当前页
     * @param size 每页大小
     * @return 分页结果
     */
    IPage<EcoMarketClientPublishConfigModel> pageQuery(QueryEcoMarketVo queryEcoMarketVo, long current, long size);

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
