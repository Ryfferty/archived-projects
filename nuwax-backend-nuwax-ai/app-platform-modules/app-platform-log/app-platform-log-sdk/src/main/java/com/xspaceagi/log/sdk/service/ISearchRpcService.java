package com.xspaceagi.log.sdk.service;

import com.xspaceagi.log.sdk.request.DocumentSearchRequest;
import com.xspaceagi.log.sdk.vo.SearchDocument;
import com.xspaceagi.log.sdk.vo.SearchResult;

import java.util.List;

public interface ISearchRpcService {

    void bulkIndex(List<SearchDocument> list);

    void deleteDocument(Class<? extends SearchDocument> searchDocumentClazz, String id);

    SearchResult search(DocumentSearchRequest documentSearchRequest);
}
