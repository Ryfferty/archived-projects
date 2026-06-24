package com.xspaceagi.eco.market.domain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xspaceagi.eco.market.domain.model.EcoMarketClientConfigModel;
import com.xspaceagi.eco.market.domain.model.valueobj.QueryEcoMarketVo;
import com.xspaceagi.eco.market.spec.enums.EcoMarketDataTypeEnum;
import com.xspaceagi.system.spec.common.UserContext;

import java.util.List;
import java.util.Map;

public interface IEcoMarketClientConfigDomainService {

        /**
         * 配置详情
         *
         * @param id 配置id
         */
        EcoMarketClientConfigModel queryOneInfoById(Long id);

        /**
         * 批量根据ids查询配置
         * 
         * @param ids id列表
         * @return 配置列表
         */
        List<EcoMarketClientConfigModel> queryListByIds(List<Long> ids);

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
         * 分页查询
         * 
         * @param queryParams 查询参数
         * @param current     当前页
         * @param size        每页大小
         * @return 分页结果
         */
        IPage<EcoMarketClientConfigModel> pageQuery(Map<String, Object> queryParams, long current, long size);

        /**
         * 根据uid查询客户端配置
         * 
         * @param uid 唯一ID
         * @return 客户端配置
         */
        EcoMarketClientConfigModel queryOneByUid(String uid);

        /**
         * 根据UID列表查询配置列表
         *
         * @param uids UID列表
         * @return 配置列表
         */
        List<EcoMarketClientConfigModel> queryListByUids(List<String> uids);

        /**
         * 下线配置
         * 
         * @param uid          配置UID
         * @param clientId     客户端ID
         * @param clientSecret 客户端密钥
         * @param userContext  用户上下文
         * @return 更新后的配置
         */
        void offlineConfig(String uid, String clientId, String clientSecret, UserContext userContext);


        /**
         * 撤销发布
         * 
         * @param uid 配置UID
         * @param clientId 客户端ID
         * @param clientSecret 客户端密钥
         * @param userContext 用户上下文
         */
        void unpublishConfig(String uid, String clientId, String clientSecret, UserContext userContext);

        /**
         * 更新草稿配置
         * 
         * @param model       配置模型
         * @param userContext 用户上下文
         * @return 配置ID
         */
        Long updateDraft(EcoMarketClientConfigModel model, UserContext userContext);

        /**
         * 根据uid更新分享状态
         * 
         * @param uid            配置唯一标识
         * @param shareStatus    分享状态,1:草稿;2:审核中;3:已发布;4:已下线;5:驳回; @see
         *                       EcoMarketShareStatusEnum
         * @param approveMessage 审批消息
         * @param userContext    用户上下文
         * @return 是否更新成功
         */
        boolean updateShareStatusByUid(String uid, Integer shareStatus, String approveMessage, UserContext userContext);

        /**
         * 根据目标id和目标类型获取配置json
         * 
         * @param dataTypeEnum    数据类型
         * @param targetId        目标id
         * @param targetType      目标类型
         * @param configParamJson 配置参数json
         * @return 配置json
         * 
         */
        String obtainConfigJson(EcoMarketDataTypeEnum dataTypeEnum, Long targetId, String targetType,
                        String configParamJson);

        /**
         * 分页查询启用状态的配置
         *
         * @param current  当前页
         * @param size     每页大小
         * @param dataType 市场类型,1:插件;2:模板;3:MCP
         * @return 启用状态的配置分页结果
         */
        IPage<EcoMarketClientConfigModel> pageQueryEnabled(QueryEcoMarketVo queryEcoMarketVo, long current, long size);

        /**
         * 分页查询我的分享
         *
         * @param queryEcoMarketVo 查询条件
         * @param current          当前页
         * @param size             每页大小
         * @return 分页结果
         */
        IPage<EcoMarketClientConfigModel> pageQueryMyShare(QueryEcoMarketVo queryEcoMarketVo, long current, long size);

        /**
         * 查询我的分享总数
         * 
         * @return 总数
         */
        Long queryTotalMyShare();

        /**
         * 根据uid删除配置
         * 
         * @param uid 配置唯一标识
         */
        void deleteByUid(String uid);

        /**
         * 检查配置是否重复
         * 
         * @param targetId     目标id
         * @param targetType   目标类型
         * @param dataTypeEnum 数据类型
         * @param uid          配置唯一标识(可选,用于排除自身)
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
