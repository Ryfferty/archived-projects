package com.xspaceagi.ctl;


import com.xspaceagi.system.spec.dto.ReqResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Slf4j
public class HealthController {

    @RequestMapping(path = "/health")
    public ReqResult<Void> health() {
        return ReqResult.success();
    }
}
