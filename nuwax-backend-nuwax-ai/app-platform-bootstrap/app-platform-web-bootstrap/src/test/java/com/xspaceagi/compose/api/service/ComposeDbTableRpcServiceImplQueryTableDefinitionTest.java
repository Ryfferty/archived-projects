package com.xspaceagi.compose.api.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson2.JSON;
import com.xspaceagi.PlatformApiApplication;
import com.xspaceagi.compose.sdk.DorisDataPage;
import com.xspaceagi.compose.sdk.request.DorisTableDefineRequest;
import com.xspaceagi.compose.sdk.request.QueryDorisTableDefinePageRequest;
import com.xspaceagi.compose.sdk.service.IComposeDbTableRpcService;
import com.xspaceagi.compose.sdk.vo.define.TableDefineVo;
import com.xspaceagi.system.spec.common.RequestContext;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(classes = PlatformApiApplication.class)
@Slf4j
public class ComposeDbTableRpcServiceImplQueryTableDefinitionTest {

    @Autowired
    private IComposeDbTableRpcService composeDbTableRpcService;

    @Test
    void testQueryTableDefinition() {

        // 设置租户上下文，防止 MyBatis Plus 多租户插件报错
        try {
            RequestContext.setThreadTenantId(1L);

            // RequestContext.get().setUserId(6L);

            DorisTableDefineRequest request = new DorisTableDefineRequest();
            request.setTableId(28L); // 请根据实际测试表ID设置
            TableDefineVo definitionVo = composeDbTableRpcService.queryTableDefinition(request);
            log.info("definitionVo:{}", JSON.toJSONString(definitionVo));
            assertNotNull(definitionVo);
            // 可根据实际业务补充更多断言
        } finally {
            RequestContext.remove();
        }
    }

    @Test
    void testQueryTableDefineBySpaceId() {
        try {
            RequestContext.setThreadTenantId(1L);

            QueryDorisTableDefinePageRequest request = QueryDorisTableDefinePageRequest.builder()
                    .spaceId(37L) // 请根据实际测试空间ID设置
                    .pageNo(1)
                    .pageSize(10)
                    .build();

            DorisDataPage<TableDefineVo> pageResult = composeDbTableRpcService.queryTableDefineBySpaceId(request);
            log.info("pageResult:{}", JSON.toJSONString(pageResult));
            assertNotNull(pageResult, "分页结果不应为null");
            assertNotNull(pageResult.getRecords(), "记录列表不应为null");
            // 可根据实际业务补充更多断言，比如校验返回数量、内容等
        } finally {
            RequestContext.remove();
        }
    }
}