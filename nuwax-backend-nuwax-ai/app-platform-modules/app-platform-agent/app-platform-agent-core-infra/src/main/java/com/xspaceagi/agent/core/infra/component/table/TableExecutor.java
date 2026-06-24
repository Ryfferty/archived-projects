package com.xspaceagi.agent.core.infra.component.table;

import com.xspaceagi.agent.core.adapter.dto.config.Arg;
import com.xspaceagi.agent.core.adapter.dto.config.workflow.TableNodeConfigDto;
import com.xspaceagi.agent.core.infra.component.BaseComponent;
import com.xspaceagi.agent.core.infra.component.table.dto.TableExecutorContext;
import com.xspaceagi.agent.core.infra.component.table.dto.TableResponseDto;
import com.xspaceagi.compose.sdk.request.DorisTableDataRequest;
import com.xspaceagi.compose.sdk.response.DorisTableDataResponse;
import com.xspaceagi.compose.sdk.service.IComposeDbTableRpcService;
import com.xspaceagi.system.spec.tenant.thread.TenantFunctions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TableExecutor extends BaseComponent {

    private IComposeDbTableRpcService iComposeDbTableRpcService;

    @Autowired
    public void setIComposeDbTableRpcService(IComposeDbTableRpcService iComposeDbTableRpcService) {
        this.iComposeDbTableRpcService = iComposeDbTableRpcService;
    }

    public Mono<TableResponseDto> execute(TableExecutorContext executorContext) {
        if (executorContext.getArgs() == null) {
            executorContext.setArgs(new java.util.HashMap<>());
        }
        return Mono.create(sink -> submit(() -> {
            DorisTableDataRequest request = new DorisTableDataRequest(executorContext.getTableId(), executorContext.getSql(), executorContext.getArgs(), executorContext.getExtArgs());
            try {
                DorisTableDataResponse response = TenantFunctions.callWithIgnoreCheck(() -> iComposeDbTableRpcService.queryTableData(request));
                TableResponseDto tableResponseDto = new TableResponseDto();
                tableResponseDto.setOutputList(response.getData());
                tableResponseDto.setRomNum(response.getRowNum() == null ? 0 : response.getRowNum().intValue());
                tableResponseDto.setSuccess(true);
                tableResponseDto.setId(response.getRowId());
                sink.success(tableResponseDto);
            } catch (Exception e) {
                log.error("execute error", e);
                sink.error(e);
            }
        }));
    }

    // 生成插入SQL
    public static String generateInsertSql(List<String> usedNames, List<Arg> tableFields) {
        Set<String> filedNames = tableFields.stream().map(Arg::getName).collect(Collectors.toSet());
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for (String name : usedNames) {
            if (!filedNames.contains(name)) {
                continue;
            }
            if (!columns.isEmpty()) {
                columns.append(", ");
                values.append(", ");
            }
            columns.append(name);
            values.append("{{").append(name).append("}}");
        }

        return String.format("INSERT INTO %s (%s) VALUES (%s);", "custom_table_name", columns, values);
    }

    //生成更新SQL
    public static String generateUpdateSql(List<String> usedSetNames, List<Arg> tableFields, TableNodeConfigDto.ConditionTypeEnum conditionType, List<TableNodeConfigDto.ConditionArgDto> conditionArgs) {
        Set<String> filedNames = tableFields.stream().map(Arg::getName).collect(Collectors.toSet());
        StringBuilder set = new StringBuilder();
        for (String name : usedSetNames) {
            if (!filedNames.contains(name)) {
                continue;
            }
            if (!set.isEmpty()) {
                set.append(", ");
            }
            set.append(name).append(" = {{_").append(name).append("}}");
        }
        String condition = generateConditionSql(filedNames, conditionType, conditionArgs);
        if (StringUtils.isBlank(condition)) {
            // 没有条件，不执行实际更新
            return String.format("UPDATE %s SET %s WHERE 1=0;", "custom_table_name", set);
        }
        return String.format("UPDATE %s SET %s WHERE %s;", "custom_table_name", set, condition);
    }

    // 生成删除SQL
    public static String generateDeleteSql(List<Arg> tableFields, TableNodeConfigDto.ConditionTypeEnum conditionType, List<TableNodeConfigDto.ConditionArgDto> conditionArgs) {
        Set<String> filedNames = tableFields.stream().map(Arg::getName).collect(Collectors.toSet());
        String condition = generateConditionSql(filedNames, conditionType, conditionArgs);
        if (StringUtils.isBlank(condition)) {
            // 没有条件，不执行实际删除
            return String.format("DELETE FROM %s WHERE 1=0;", "custom_table_name");
        }
        return String.format("DELETE FROM %s WHERE %s;", "custom_table_name", condition);
    }

    // 生成查询SQL
    public static String generateQuerySql(List<Arg> tableFields, TableNodeConfigDto.ConditionTypeEnum conditionType, List<TableNodeConfigDto.ConditionArgDto> conditionArgs, Integer limit) {
        if (limit == null || limit > 10000) {
            limit = 100;
        }
        Set<String> filedNames = tableFields.stream().map(Arg::getName).collect(Collectors.toSet());
        String condition = generateConditionSql(filedNames, conditionType, conditionArgs);
        if (StringUtils.isBlank(condition)) {
            // 没有条件，不执行实际查询
            return String.format("SELECT * FROM %s ORDER BY id DESC LIMIT %d;", "custom_table_name", limit);
        }
        return String.format("SELECT * FROM %s WHERE %s ORDER BY id DESC LIMIT %d;", "custom_table_name", condition, limit);
    }

    //生成查询条件
    private static String generateConditionSql(Set<String> filedNames, TableNodeConfigDto.ConditionTypeEnum conditionType, List<TableNodeConfigDto.ConditionArgDto> conditionArgs) {
        StringBuilder condition = new StringBuilder();
        Set<String> placeholders = new HashSet<>();
        AtomicInteger index = new AtomicInteger(0);
        for (TableNodeConfigDto.ConditionArgDto conditionArgDto : conditionArgs) {
            if (conditionArgDto.getFirstArg().getName() == null) {
                conditionArgDto.getFirstArg().setName(conditionArgDto.getFirstArg().getBindValue());
            }
            if (!filedNames.contains(conditionArgDto.getFirstArg().getName())) {
                continue;
            }
            String placeholder = conditionArgDto.getFirstArg().getName();
            if (placeholders.contains(placeholder)) {
                placeholder = placeholder + "_" + index.incrementAndGet();
            } else {
                placeholders.add(placeholder);
            }
            if (!condition.isEmpty()) {
                condition.append(" ");
                condition.append(conditionType.name());
                condition.append(" ");
            }
            if (conditionArgDto.getCompareType() == TableNodeConfigDto.CompareTypeEnum.IN || conditionArgDto.getCompareType() == TableNodeConfigDto.CompareTypeEnum.NOT_IN) {
                condition.append(conditionArgDto.getFirstArg().getName())
                        .append(convertCompareType(conditionArgDto.getCompareType()))
                        .append("(")
                        .append("{{")
                        .append(placeholder)
                        .append("}}")
                        .append(")");
            } else if (conditionArgDto.getCompareType() == TableNodeConfigDto.CompareTypeEnum.NOT_NULL || conditionArgDto.getCompareType() == TableNodeConfigDto.CompareTypeEnum.IS_NULL) {
                condition.append("(")
                        .append(conditionArgDto.getFirstArg().getName())
                        .append(" ")
                        .append(convertCompareType(conditionArgDto.getCompareType()))
                        .append("OR ").append(conditionArgDto.getFirstArg().getName())
                        .append(conditionArgDto.getCompareType() == TableNodeConfigDto.CompareTypeEnum.NOT_NULL ? " != " : " = ")
                        .append("''")
                        .append(")");
            } else {
                condition.append(conditionArgDto.getFirstArg().getName())
                        .append(convertCompareType(conditionArgDto.getCompareType()))
                        .append("{{").append(placeholder).append("}}");
            }
        }
        return condition.toString();
    }

    private static String convertCompareType(TableNodeConfigDto.CompareTypeEnum compareType) {
        switch (compareType) {
            case EQUAL -> {
                return " = ";
            }
            case NOT_EQUAL -> {
                return " != ";
            }
            case GREATER_THAN -> {
                return " > ";
            }
            case GREATER_THAN_OR_EQUAL -> {
                return " >= ";
            }
            case LESS_THAN -> {
                return " < ";
            }
            case LESS_THAN_OR_EQUAL -> {
                return " <= ";
            }
            case IN -> {
                return " IN ";
            }
            case NOT_IN -> {
                return " NOT IN ";
            }
            case IS_NULL -> {
                return " IS NULL ";
            }
            case NOT_NULL -> {
                return " IS NOT NULL ";
            }
        }
        return "";
    }
}
