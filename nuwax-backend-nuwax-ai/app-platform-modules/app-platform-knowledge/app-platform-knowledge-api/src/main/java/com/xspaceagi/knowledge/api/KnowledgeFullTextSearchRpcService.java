package com.xspaceagi.knowledge.api;

import com.xspaceagi.knowledge.core.infra.rpc.SearchRpcService;
import com.xspaceagi.knowledge.core.infra.rpc.vo.KnowledgeSearchDocument;
import com.xspaceagi.knowledge.sdk.request.KnowledgeFullTextSearchRequestVo;
import com.xspaceagi.knowledge.sdk.response.KnowledgeFullTextSearchResponseVo;
import com.xspaceagi.knowledge.sdk.response.KnowledgeFullTextSearchResultVo;
import com.xspaceagi.knowledge.sdk.sevice.IKnowledgeFullTextSearchRpcService;
import com.xspaceagi.log.sdk.request.DocumentSearchRequest;
import com.xspaceagi.log.sdk.vo.SearchResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 知识库全文检索 RPC 服务实现
 *
 * @author system
 * @date 2025-03-31
 */
@Slf4j
@Service
public class KnowledgeFullTextSearchRpcService implements IKnowledgeFullTextSearchRpcService {

    @Resource
    private SearchRpcService searchRpcService;

    @Override
    public KnowledgeFullTextSearchResponseVo search(KnowledgeFullTextSearchRequestVo requestVo) {
        long startTime = System.currentTimeMillis();

        log.info("全文检索RPC请求: tenantId={}, kbIds={}, queryText={}, topK={}",
                requestVo.getTenantId(), requestVo.getKbIds(), requestVo.getQueryText(), requestVo.getTopK());


        List<Map<String, Object>> filterFieldsAndValues = new ArrayList<>();
        filterFieldsAndValues.add(Map.of("tenantId", requestVo.getTenantId()));
        if (requestVo.getDocIds() != null && requestVo.getDocIds().size() > 0) {
            filterFieldsAndValues.add(Map.of("docId", requestVo.getDocIds()));
        }
        if (requestVo.getKbIds() != null && requestVo.getKbIds().size() > 0) {
            filterFieldsAndValues.add(Map.of("kbId", requestVo.getKbIds()));
        }
        DocumentSearchRequest documentSearchRequest = DocumentSearchRequest.builder()
                .searchDocumentClazz(KnowledgeSearchDocument.class)
                .searchFields(List.of("rawText"))
                .keyword(requestVo.getQueryText()).filterFieldsAndValues(filterFieldsAndValues).build();

        SearchResult search = searchRpcService.search(documentSearchRequest);

        // 3. 转换返回结果
        KnowledgeFullTextSearchResponseVo response = new KnowledgeFullTextSearchResponseVo();
        response.setResults(search.getItems().stream()
                .map(this::convertToVo)
                .collect(Collectors.toList()));
        response.setCostTimeMs(System.currentTimeMillis() - startTime);

        log.info("全文检索RPC完成: kbIds={}, resultCount={}, costTimeMs={}",
                requestVo.getKbIds(), response.getResults().size(), response.getCostTimeMs());

        return response;
    }

    /**
     * 转换 Domain Model 为 SDK VO
     */
    private KnowledgeFullTextSearchResultVo convertToVo(SearchResult.SearchResultItem item) {
        KnowledgeSearchDocument knowledgeSearchDocument = (KnowledgeSearchDocument) item.getDocument();
        KnowledgeFullTextSearchResultVo vo = new KnowledgeFullTextSearchResultVo();
        vo.setRawSegmentId(Long.parseLong(knowledgeSearchDocument.getId()));
        vo.setDocId(knowledgeSearchDocument.getDocId());
        vo.setKbId(knowledgeSearchDocument.getKbId());
        vo.setRawText(knowledgeSearchDocument.getRawText());
        vo.setScore(item.getScore().floatValue());
        vo.setDocumentName(knowledgeSearchDocument.getDocumentName());
        return vo;
    }

}

