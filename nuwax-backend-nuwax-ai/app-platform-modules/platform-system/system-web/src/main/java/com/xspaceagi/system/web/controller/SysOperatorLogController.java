package com.xspaceagi.system.web.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xspaceagi.system.infra.dao.model.OperatorLogModel;
import com.xspaceagi.system.infra.repository.ISysOperatorLogRepository;
import com.xspaceagi.system.infra.service.QueryVoListDelegateService;
import com.xspaceagi.system.sdk.operate.ActionType;
import com.xspaceagi.system.sdk.operate.OperationLogReporter;
import com.xspaceagi.system.sdk.operate.SystemEnum;
import com.xspaceagi.system.spec.dto.ReqResult;
import com.xspaceagi.system.spec.page.PageQueryParamVo;
import com.xspaceagi.system.spec.page.PageQueryVo;
import com.xspaceagi.system.spec.page.SuperPage;
import com.xspaceagi.system.web.dto.operatorlog.EnumOptionDto;
import com.xspaceagi.system.web.dto.operatorlog.OperatorLogDto;
import com.xspaceagi.system.web.dto.operatorlog.OperatorLogQueryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "系统配置-操作日志")
@RestController
@RequestMapping("/api/sys/operator/log")
@Slf4j
public class SysOperatorLogController {

    @Resource
    private QueryVoListDelegateService queryVoListDelegateService;


    @Resource
    private ISysOperatorLogRepository sysOperatorLogRepository;

    @OperationLogReporter(actionType = ActionType.QUERY,
            action = "操作日志页面列表查询", objectName = "操作日志", systemCode = SystemEnum.SYSTEM)
    @Operation(summary = "操作日志页面列表查询")
    @ApiResponses(
            @ApiResponse(responseCode = "0000", description = "成功")
    )
    @RequestMapping(path = "/list", method = RequestMethod.POST)
    public ReqResult<IPage<OperatorLogDto>> list(@RequestBody PageQueryVo<OperatorLogQueryDto> pageQueryVo) {
        OperatorLogQueryDto queryFilter = pageQueryVo.getQueryFilter();

        // 时间戳转换为 LocalDateTime（复用现有时间范围查询机制）
        if (queryFilter != null) {
            if (queryFilter.getCreateTimeGt() != null || queryFilter.getCreateTimeLt() != null) {
                List<LocalDateTime> created = new ArrayList<>(2);
                if (queryFilter.getCreateTimeGt() != null) {
                    created.add(LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(queryFilter.getCreateTimeGt()),
                            ZoneId.systemDefault()));
                }
                if (queryFilter.getCreateTimeLt() != null) {
                    created.add(LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(queryFilter.getCreateTimeLt()),
                            ZoneId.systemDefault()));
                }
                queryFilter.setCreated(created);
            }
        }

        PageQueryParamVo pageQueryParamVo = new PageQueryParamVo(pageQueryVo);

        SuperPage<OperatorLogModel> superPage = this.queryVoListDelegateService.queryVoList(this.sysOperatorLogRepository,
                pageQueryParamVo, null);

        var userModelList = superPage.getRecords();

        //类型转换
        List<OperatorLogDto> dtoList = userModelList.stream().map(OperatorLogDto::convertToDto).toList();
        SuperPage<OperatorLogDto> iPage = SuperPage.build(superPage, dtoList);
        iPage.setCurrent(pageQueryVo.getCurrent());
        iPage.setSize(pageQueryVo.getPageSize());
        return ReqResult.success(iPage);
    }

    @OperationLogReporter(actionType = ActionType.QUERY,
            action = "操作日志详情查看", objectName = "操作日志", systemCode = SystemEnum.SYSTEM)
    @Operation(summary = "操作日志详情查看")
    @ApiResponses(
            @ApiResponse(responseCode = "0000", description = "成功")
    )
    @RequestMapping(path = "/queryById", method = RequestMethod.GET)
    public ReqResult<OperatorLogDto> queryById(Long id) {

        var model = this.sysOperatorLogRepository.queryOneInfoById(id);
        OperatorLogDto dto = OperatorLogDto.convertToDto(model);
        return ReqResult.success(dto);
    }

    @OperationLogReporter(actionType = ActionType.QUERY,
            action = "获取系统编码选项", objectName = "系统编码", systemCode = SystemEnum.SYSTEM)
    @Operation(summary = "获取系统编码选项列表")
    @ApiResponses(@ApiResponse(responseCode = "0000", description = "成功"))
    @RequestMapping(path = "/systemCode/options", method = RequestMethod.GET)
    public ReqResult<List<EnumOptionDto>> getSystemCodeOptions() {
        List<EnumOptionDto> options = Arrays.stream(SystemEnum.values())
                .map(e -> EnumOptionDto.builder()
                        .label(e.getDesc())
                        .value(e.name())
                        .build())
                .collect(Collectors.toList());
        return ReqResult.success(options);
    }

    @OperationLogReporter(actionType = ActionType.QUERY,
            action = "获取操作类型选项", objectName = "操作类型", systemCode = SystemEnum.SYSTEM)
    @Operation(summary = "获取操作类型选项列表")
    @ApiResponses(@ApiResponse(responseCode = "0000", description = "成功"))
    @RequestMapping(path = "/actionType/options", method = RequestMethod.GET)
    public ReqResult<List<EnumOptionDto>> getActionTypeOptions() {
        List<EnumOptionDto> options = Arrays.stream(ActionType.values())
                .map(e -> EnumOptionDto.builder()
                        .label(e.getName())
                        .value(e.name())
                        .build())
                .collect(Collectors.toList());
        return ReqResult.success(options);
    }

}
