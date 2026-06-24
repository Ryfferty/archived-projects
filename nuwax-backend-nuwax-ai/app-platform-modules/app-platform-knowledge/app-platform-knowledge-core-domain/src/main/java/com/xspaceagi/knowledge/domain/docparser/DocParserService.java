package com.xspaceagi.knowledge.domain.docparser;

import com.xspaceagi.knowledge.domain.model.KnowledgeDocumentModel;
import com.xspaceagi.system.spec.common.UserContext;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 文档解析服务
 */
@Service
public class DocParserService {

    @Resource
    private DocParserFactory docParserFactory;


    /**
     * 解析文档
     *
     * @param documentDto
     * @param userContext
     */
    public void parse(KnowledgeDocumentModel documentDto, UserContext userContext) {
        var fileParse = docParserFactory.getParser(documentDto.getDataType(), documentDto.getDocUrl());
        fileParse.chunk(documentDto, userContext);
    }


}
