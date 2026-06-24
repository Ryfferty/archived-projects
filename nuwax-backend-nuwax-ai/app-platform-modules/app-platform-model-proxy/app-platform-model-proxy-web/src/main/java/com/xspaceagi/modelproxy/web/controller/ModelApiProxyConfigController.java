//package com.xspaceagi.modelproxy.web.controller;
//
//import com.xspaceagi.modelproxy.sdk.service.IModelApiProxyConfigService;
//import com.xspaceagi.modelproxy.spec.dto.ModelApiProxyConfigDto;
//import com.xspaceagi.system.spec.common.RequestContext;
//import com.xspaceagi.system.spec.dto.ReqResult;
//import com.xspaceagi.system.spec.enums.ErrorCodeEnum;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.annotation.Resource;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
///**
// * 模型API代理配置管理接口
// */
//@Tag(name = "模型API代理配置管理")
//@Slf4j
//@RestController
//@RequestMapping("/api/model/proxy/config")
//public class ModelApiProxyConfigController {
//
//    @Resource
//    private IModelApiProxyConfigService modelApiProxyConfigService;
//
//    /**
//     * 根据用户API Key查询配置
//     */
//    @Operation(summary = "根据用户API Key查询配置")
//    @GetMapping("/query")
//    public ReqResult<ModelApiProxyConfigDto> queryByUserApiKey(@RequestParam("userApiKey") String userApiKey) {
//        try {
//            ModelApiProxyConfigDto dto = modelApiProxyConfigService.getByUserApiKey(userApiKey);
//            if (dto == null) {
//                return ReqResult.error(ErrorCodeEnum.NOT_FOUND.getCode(), "Configuration not found");
//            }
//            return ReqResult.success(dto);
//        } catch (Exception e) {
//            log.error("Failed to query config by userApiKey", e);
//            return ReqResult.error(ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
//        }
//    }
//
//    /**
//     * 根据ID查询配置
//     */
//    @Operation(summary = "根据ID查询配置")
//    @GetMapping("/get/{id}")
//    public ReqResult<ModelApiProxyConfigDto> getById(@PathVariable("id") Long id) {
//        try {
//            ModelApiProxyConfigDto dto = modelApiProxyConfigService.getById(id);
//            if (dto == null) {
//                return ReqResult.error(ErrorCodeEnum.NOT_FOUND.getCode(), "Configuration not found");
//            }
//            return ReqResult.success(dto);
//        } catch (Exception e) {
//            log.error("Failed to query config by id", e);
//            return ReqResult.error(ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
//        }
//    }
//
//    /**
//     * 添加或更新配置
//     */
//    @Operation(summary = "添加或更新配置")
//    @PostMapping("/save")
//    public ReqResult<Long> saveOrUpdate(@RequestBody ModelApiProxyConfigDto dto) {
//        try {
//            // 设置租户ID
//            if (RequestContext.get() != null && RequestContext.get().getTenantId() != null) {
//                dto.setTenantId(RequestContext.get().getTenantId());
//            }
//            Long id = modelApiProxyConfigService.saveOrUpdate(dto);
//            return ReqResult.success(id);
//        } catch (Exception e) {
//            log.error("Failed to save or update config", e);
//            return ReqResult.error(ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
//        }
//    }
//
//    /**
//     * 删除配置
//     */
//    @Operation(summary = "删除配置")
//    @DeleteMapping("/delete/{id}")
//    public ReqResult<Void> delete(@PathVariable("id") Long id) {
//        try {
//            modelApiProxyConfigService.delete(id);
//            return ReqResult.success(null);
//        } catch (Exception e) {
//            log.error("Failed to delete config", e);
//            return ReqResult.error(ErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
//        }
//    }
//}
