package com.xspaceagi.eco.market.web.controller;

import com.xspaceagi.eco.market.spec.app.service.IEcoMarketClientSecretApplicationService;
import com.xspaceagi.eco.market.spec.constant.EcoMarketApiConstant;
import com.xspaceagi.eco.market.web.controller.base.BaseController;
import com.xspaceagi.system.infra.service.QueryVoListDelegateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "生态市场-客户端-密钥配置")
@RestController
@RequestMapping(EcoMarketApiConstant.ClientSecret.BASE)
@Slf4j
public class EcoMarketClientSecretConfigController extends BaseController {

    @Resource
    private QueryVoListDelegateService queryVoListDelegateService;

    @Resource
    private IEcoMarketClientSecretApplicationService ecoMarketClientSecretApplicationService;

}
