package com.xspaceagi.system.infra.health;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 容器健康检查
 */
@Tag(name = "健康检查")
@RestController
@Slf4j
@ResponseBody
public class ContainerCheckController {

    /**
     * 健康检查 ready
     *
     * @return success
     */
    @Operation(summary = "容器健康检查 ready")
    @GetMapping(value = "/ready")
    public String ready() {
        return "success";
    }

    /**
     * 健康检查 health
     *
     * @return success
     */
    @Operation(summary = "容器健康检查 health")
    @GetMapping(value = "/health")
    public String health() {
        return "success";
    }

}
