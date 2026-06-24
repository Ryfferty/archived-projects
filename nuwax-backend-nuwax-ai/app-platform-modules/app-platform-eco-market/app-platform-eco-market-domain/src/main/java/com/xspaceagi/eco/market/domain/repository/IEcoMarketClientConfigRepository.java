package com.xspaceagi.eco.market.domain.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xspaceagi.eco.market.domain.model.EcoMarketClientConfigModel;
import com.xspaceagi.eco.market.domain.model.valueobj.QueryEcoMarketVo;
import com.xspaceagi.eco.market.spec.enums.EcoMarketDataTypeEnum;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.infra.service.IQueryViewService;

import java.util.List;

public interface IEcoMarketClientConfigRepository extends IQueryViewService<EcoMarketClientConfigModel> {

    /**
     * 模板配置详情
     *
     * @param id 模板id
     */
    EcoMarketClientConfigModel queryOneInfoById(Long id);

    /**
     * 批量根据kbIds查询知识库配置
     * 
     * @param kbIds 知识库id列表
     * @return 知识库配置列表
     */
    List<EcoMarketClientConfigModel> queryListByIds(List<Long> kbIds);

    /**
     * 删除根据主键id
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 更新
     *
     * @param model 模型
     * @return id
     */
    Long updateInfo(EcoMarketClientConfigModel model, UserContext userContext);

    /**
     * 根据uid更新
     *
     * @param model 模型
     * @return id
     */
    Long updateInfoByUid(EcoMarketClientConfigModel model, UserContext userContext);
    /**
     * 新增
     */
    Long addInfo(EcoMarketClientConfigModel model, UserContext userContext);

    /**
     * 根据uid查询客户端配置
     * 
     * @param uid 唯一ID
     * @return 客户端配置
     */
    EcoMarketClientConfigModel queryOneByUid(String uid);

    /**
     * 查询多个uid的客户端配置
     * 
     * @param uids uid列表
     * @return 客户端配置列表
     */
    List<EcoMarketClientConfigModel> queryListByUids(List<String> uids);

    /**
     * 根据uid更新分享状态
     * 
     * @param uid         唯一ID
     * @param shareStatus 分享状态
     * @param userContext 用户上下文
     * @return 是否更新成功
     */
    boolean updateShareStatusByUid(String uid, Integer shareStatus, String approveMessage, UserContext userContext);

    /**
     * 生态市场,用户分页查询结果
     * 
     * @param queryEcoMarketVo 查询条件
     * @param current          当前页
     * @param size             每页大小
     * @return 分页结果
     */
    IPage<EcoMarketClientConfigModel> pageQuery(QueryEcoMarketVo queryEcoMarketVo, long current, long size);

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
    List<EcoMarketClientConfigModel> queryMyShareAndReviewing();
}
