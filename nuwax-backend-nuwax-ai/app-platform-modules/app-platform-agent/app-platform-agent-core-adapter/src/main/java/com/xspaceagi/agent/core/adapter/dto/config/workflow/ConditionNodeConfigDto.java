package com.xspaceagi.agent.core.adapter.dto.config.workflow;

import com.google.common.collect.Lists;
import com.xspaceagi.agent.core.adapter.dto.config.Arg;
import com.xspaceagi.system.sdk.retry.utils.GeneratorUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 插件节点配置
 */
@Getter
@Setter
public class ConditionNodeConfigDto extends NodeConfigDto {

    //条件分支列表
    @Schema(description = "条件分支列表")
    private List<ConditionBranchConfigDto> conditionBranchConfigs;


    @Data
    public static class ConditionBranchConfigDto implements Serializable {

        @Schema(description = "唯一标识")
        private String uuid;

        //分支类型
        @Schema(description = "分支类型")
        private BranchTypeEnum branchType;

        //条件参数关系
        @Schema(description = "条件参数之间关系")
        private ConditionTypeEnum conditionType;

        //关联下级节点
        @Schema(description = "关联下级节点id列表")
        private List<Long> nextNodeIds;

        //参数列表配置
        @Schema(description = "参数列表配置")
        private List<ConditionArgDto> conditionArgs;
    }

    public enum BranchTypeEnum {
        //如果
        IF,
        //否则如果
        ELSE_IF,
        //否则
        ELSE
    }

    public enum ConditionTypeEnum {
        //AND
        AND,
        //OR
        OR
    }

    @Data
    public static class ConditionArgDto implements Serializable {

        @Schema(description = "比较的第一个入参")
        private Arg firstArg;

        //比较类型
        @Schema(description = "比较类型，等于：EQUAL, 不等于：NOT_EQUAL, 大于：GREATER_THAN, 大于等于：GREATER_THAN_OR_EQUAL, 小于：LESS_THAN, 小于等于：LESS_THAN_OR_EQUAL, 包含：CONTAINS, 不包含：NOT_CONTAINS, 匹配正则表达式：MATCH_REGEX, 为空：IS_NULL, 不为空：NOT_NULL")
        private CompareTypeEnum compareType;

        @Schema(description = "比较的第二个入参")
        private Arg secondArg;
    }

    public enum CompareTypeEnum {
        //等于
        EQUAL,
        //不等于
        NOT_EQUAL,
        //大于
        GREATER_THAN,
        //大于等于
        GREATER_THAN_OR_EQUAL,
        //小于
        LESS_THAN,
        //小于等于
        LESS_THAN_OR_EQUAL,
        LENGTH_GREATER_THAN,
        //大于等于
        LENGTH_GREATER_THAN_OR_EQUAL,
        //小于
        LENGTH_LESS_THAN,
        //小于等于
        LENGTH_LESS_THAN_OR_EQUAL,
        //包含
        CONTAINS,
        //不包含
        NOT_CONTAINS,
        //匹配正则表达式
        MATCH_REGEX,
        //为空
        IS_NULL,
        //不为空
        NOT_NULL
    }


    /**
     * 公共节点配置转条件节点配置
     *
     * @param nodeConfigDto 公共节点配置
     * @return 条件节点配置
     */
    public static ConditionNodeConfigDto addFrom(NodeConfigDto nodeConfigDto) {
        ConditionNodeConfigDto conditionNodeConfigDto = new ConditionNodeConfigDto();
        conditionNodeConfigDto.setExtension(nodeConfigDto.getExtension());
        conditionNodeConfigDto.setInputArgs(nodeConfigDto.getInputArgs());
        conditionNodeConfigDto.setOutputArgs(nodeConfigDto.getOutputArgs());

        //设置默认2个条件分支,公共节点没有属性:conditionBranchConfigs,所以这里直接设置2个默认值,但具体参数是空的
        List<ConditionBranchConfigDto> conditionBranchConfigs = obtainDefaultCondition();
        conditionNodeConfigDto.setConditionBranchConfigs(conditionBranchConfigs);

        return conditionNodeConfigDto;

    }


    /**
     * 新增空白条件节点,默认配置两个分支，第一个分支为if，第二个分支为else
     *
     * @return
     */
    public static List<ConditionBranchConfigDto> obtainDefaultCondition() {

        List<ConditionBranchConfigDto> conditionBranchConfigDtos = new ArrayList<>();

        ConditionBranchConfigDto ifConditions = new ConditionBranchConfigDto();
        ifConditions.setUuid(GeneratorUtils.generateUUID());
        ifConditions.setBranchType(BranchTypeEnum.IF);
        ifConditions.setConditionType(ConditionTypeEnum.AND);
        ifConditions.setNextNodeIds(List.of());
        ConditionArgDto conditionArgDto = new ConditionArgDto();
        conditionArgDto.setCompareType(CompareTypeEnum.EQUAL);
        ifConditions.setConditionArgs(Lists.newArrayList(conditionArgDto));

        conditionBranchConfigDtos.add(ifConditions);

        ConditionBranchConfigDto elseConditions = new ConditionBranchConfigDto();
        elseConditions.setUuid(GeneratorUtils.generateUUID());
        elseConditions.setBranchType(BranchTypeEnum.ELSE);
        elseConditions.setConditionType(ConditionTypeEnum.AND);
        elseConditions.setNextNodeIds(List.of());
        //否则条件,没有参数列表,设置为空
        elseConditions.setConditionArgs(Lists.newArrayList());

        conditionBranchConfigDtos.add(elseConditions);

        return conditionBranchConfigDtos;
    }
}
