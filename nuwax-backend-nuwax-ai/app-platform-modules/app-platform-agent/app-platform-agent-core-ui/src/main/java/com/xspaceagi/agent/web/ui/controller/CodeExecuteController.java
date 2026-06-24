package com.xspaceagi.agent.web.ui.controller;

import com.xspaceagi.agent.core.infra.code.CodeArgDto;
import com.xspaceagi.agent.core.infra.code.CodeExecuteResultDto;
import com.xspaceagi.agent.core.infra.code.CodeExecuteService;
import com.xspaceagi.system.spec.common.RequestContext;
import com.xspaceagi.system.spec.dto.ReqResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "代码执行接口")
@RestController
@RequestMapping("/api/code")
@Slf4j
public class CodeExecuteController {

    @Resource
    private CodeExecuteService codeExecuteService;

    @Operation(summary = "代码执行测试")
    @RequestMapping(path = "/execute", method = RequestMethod.POST)
    public ReqResult<CodeExecuteResultDto> execute(@RequestBody CodeArgDto codeArgDto) {
        codeArgDto.setUserId(RequestContext.get().getUserId().toString());
        CodeExecuteResultDto executeResultDto = codeExecuteService.execute(codeArgDto);
        return ReqResult.success(executeResultDto);
    }

}
