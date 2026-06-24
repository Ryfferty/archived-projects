package com.xspaceagi.knowledge.core.infra.rpc;

import com.xspaceagi.knowledge.core.infra.rpc.vo.KnowledgeSearchDocument;
import com.xspaceagi.log.sdk.request.DocumentSearchRequest;
import com.xspaceagi.log.sdk.service.ISearchRpcService;
import com.xspaceagi.log.sdk.vo.SearchDocument;
import com.xspaceagi.log.sdk.vo.SearchResult;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchRpcService {

    @Resource
    private ISearchRpcService iSearchRpcService;

    public void bulkIndex(List<KnowledgeSearchDocument> list) {
        iSearchRpcService.bulkIndex(list.stream().map(item -> (SearchDocument) item).collect(Collectors.toList()));
    }

    public void deleteDocument(Class<? extends SearchDocument> searchDocumentClazz, String id) {
        iSearchRpcService.deleteDocument(searchDocumentClazz, id);
    }

    public SearchResult search(DocumentSearchRequest documentSearchRequest) {
        return iSearchRpcService.search(documentSearchRequest);
    }
}
