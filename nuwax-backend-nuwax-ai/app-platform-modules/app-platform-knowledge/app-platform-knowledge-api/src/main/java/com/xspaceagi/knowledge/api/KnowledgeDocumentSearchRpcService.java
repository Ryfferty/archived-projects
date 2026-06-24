package com.xspaceagi.knowledge.api;

import com.xspaceagi.knowledge.domain.model.KnowledgeDocumentModel;
import com.xspaceagi.knowledge.domain.service.IKnowledgeDocumentDomainService;
import com.xspaceagi.knowledge.sdk.request.KnowledgeDocumentRequestVo;
import com.xspaceagi.knowledge.sdk.response.KnowledgeDocumentResponseVo;
import com.xspaceagi.knowledge.sdk.sevice.IKnowledgeDocumentSearchRpcService;

import com.xspaceagi.system.domain.log.LogRecordPrint;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KnowledgeDocumentSearchRpcService implements IKnowledgeDocumentSearchRpcService {

    @Resource
    private IKnowledgeDocumentDomainService knowledgeDocumentDomainService;


    @LogRecordPrint(content = "[知识库文档]-根据知识库id查询文档")
    @Override
    public KnowledgeDocumentResponseVo documentSearch(KnowledgeDocumentRequestVo requestVo) {
        var docList = this.knowledgeDocumentDomainService.queryDocByKbId(requestVo.getKbId());
        var ans = docList.stream()
                .map(KnowledgeDocumentModel::convertFromModel)
                .toList();
        KnowledgeDocumentResponseVo responseVo = new KnowledgeDocumentResponseVo();
        responseVo.setDocumentVoList(ans);
        return responseVo;
    }
}
