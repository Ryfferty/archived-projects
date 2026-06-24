package com.xspaceagi.system.sdk.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Builder
@Schema(name = "任务信息")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScheduleTaskDto {

    @Schema(description = "任务自增ID，用于更新删除等操作")
    private Long id;

    @Schema(description = "租户ID", hidden = true)
    private Long tenantId;

    private Long spaceId;

    @Schema(description = "创建者ID", hidden = true)
    private Long creatorId;

    @Schema(description = "任务目标对象名称")
    private String targetName;

    @Schema(description = "任务目标图标")
    private String targetIcon;

    @Schema(description = "任务目标类型")
    private String targetType;

    @Schema(description = "任务目标ID")
    private String targetId;

    @Schema(description = "任务名称")
    private String taskName;

    @Schema(description = "任务ID", hidden = true)
    private String taskId;

    @Schema(description = "任务BeanID", hidden = true)
    private String beanId;

    @Schema(description = "任务执行周期")
    private String cron;

    @Schema(description = "任务参数")
    private Object params;

    @Schema(description = "任务状态，EXECUTING 执行中, CREATE 任务创建，等待执行、CONTINUE 执行成功，待下次执行, FAIL 执行失败，待下次执行；CANCEL、COMPLETE 均为“已结束，不再执行”")
    private Status status;

    @Schema(description = "任务最近一次执行时间")
    private Date latestExecTime;

    @Schema(description = "任务下次执行时间")
    private Date lockTime;

    @Schema(description = "任务执行次数")
    private Long execTimes;

    @Schema(description = "任务最大执行次数", hidden = true)
    private Long maxExecTimes;

    @Schema(description = "任务最近一次执行异常信息")
    private String error;

    @Schema(description = "任务修改时间")
    private Date modified;

    @Schema(description = "任务创建时间")
    private Date created;

    @Schema(description = "任务创建者")
    private Creator creator;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Creator {
        private Long userId;
        private String userName;
        private String avatar;
    }

    //状态包括 创建、执行中、执行完成、取消
    public enum Status {
        CREATE,
        EXECUTING,
        CONTINUE,
        OVERFLOW_MAX_EXEC_TIMES,
        COMPLETE,
        FAIL,
        CANCEL
    }

    // 执行周期
    public enum Cron {
        // 每秒钟执行
        EVERY_SECOND("* * * * * ?"),
        EVERY_2_SECOND("0/2 * * * * ?"),
        EVERY_5_SECOND("0/5 * * * * ?"),
        // 每10秒执行
        EVERY_10_SECOND("0/10 * * * * ?"),

        EVERY_20_SECOND("0/20 * * * * ?"),

        EVERY_30_SECOND("0/30 * * * * ?"),
        // 每分钟执行
        EVERY_MINUTE("0 * * * * ?"),
        // 每5分钟执行
        EVERY_5_MINUTE("0 0/5 * * * ?"),
        // 每10分钟执行
        EVERY_10_MINUTE("0 0/10 * * * ?"),
        // 每10分钟执行
        EVERY_30_MINUTE("0 0/30 * * * ?"),
        // 每小时执行
        EVERY_HOUR("0 0 * * * ?"),
        //每15天
        EVERY_15_DAY("0 0 0 1/15 * ?"),
        // 每天0点执行
        EVERYDAY_0_2("0 0 0 * * ?");
        private String cron;

        Cron(String cron) {
            this.cron = cron;
        }

        public String getCron() {
            return cron;
        }

        public static boolean isValid(String cron) {
            if (cron == null || cron.isEmpty()) {
                return false;
            }
            String[] parts = cron.split(" ");
            if (parts.length != 6) {
                return false;
            }
            return true;
        }
    }

}
