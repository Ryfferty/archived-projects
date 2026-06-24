package com.xspaceagi.system.sdk.server;

import java.math.BigDecimal;

/**
 * 用户计量服务 RPC 接口
 *
 * <p>使用示例：
 * <pre>{@code
 * // 注入服务
 * @Resource
 * private IUserMetricRpcService userMetricRpcService;
 *
 * // 增加当前天的 Token 用量
 * userMetricRpcService.incrementMetricCurrent(userId, BizType.TOKEN_USAGE.getCode(), PeriodType.DAY, new BigDecimal("100.5"));
 *
 * // 增加通用智能体对话次数（所有时段）
 * userMetricRpcService.incrementMetricAllPeriods(userId, BizType.GENERAL_AGENT_CHAT.getCode(), BigDecimal.ONE);
 *
 * // 增加应用开发对话次数（当前小时）
 * userMetricRpcService.incrementMetricCurrent(userId, BizType.APP_DEV_CHAT.getCode(), PeriodType.HOUR, BigDecimal.ONE);
 *
 * // 查询当前天的 Token 用量
 * BigDecimal tokenUsage = userMetricRpcService.queryMetricCurrent(userId, BizType.TOKEN_USAGE.getCode(), PeriodType.DAY);
 * }</pre>
 */
public interface IUserMetricRpcService {


    /**
     * 增加用户计量值（自动对所有时段类型增加对应的值）
     *
     * @param userId  用户ID
     * @param bizType 业务类型
     * @param delta   增量值
     */
    void incrementMetricAllPeriods(Long tenantId, Long userId, String bizType, BigDecimal delta);

    /**
     * 查询用户指定业务类型和时段类型的当前时段计量值
     *
     * @param userId     用户ID
     * @param bizType    业务类型
     * @param periodType 时段类型（YEAR/MONTH/DAY/HOUR）
     * @return 计量值，如果不存在返回 null
     */
    BigDecimal queryMetricCurrent(Long tenantId, Long userId, String bizType, String periodType);
}
