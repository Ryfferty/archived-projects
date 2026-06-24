package com.xspaceagi.knowledge.api;

import com.xspaceagi.knowledge.core.application.service.IKnowledgeQaSegmentApplicationService;
import com.xspaceagi.knowledge.domain.dto.qa.QAQueryDto;
import com.xspaceagi.knowledge.domain.dto.qa.QAResDto;
import com.xspaceagi.knowledge.sdk.request.KnowledgeQaRequestVo;
import com.xspaceagi.knowledge.sdk.response.KnowledgeQaResponseVo;
import com.xspaceagi.knowledge.sdk.sevice.IKnowledgeQaSearchRpcService;
import com.xspaceagi.system.domain.log.LogRecordPrint;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class KnowledgeQaSearchRpcService implements IKnowledgeQaSearchRpcService {


    @Resource
    private IKnowledgeQaSegmentApplicationService knowledgeQaSegmentApplicationService;


    @LogRecordPrint(content = "[知识库文档]-知识库问答查询搜索")
    @Override
    public KnowledgeQaResponseVo search(KnowledgeQaRequestVo requestVo) {

        QAQueryDto qaQueryDto = QAQueryDto.convertToDto(requestVo);

        List<QAResDto> dataList = this.knowledgeQaSegmentApplicationService.search(qaQueryDto, requestVo.isIgnoreDocStatus());

        var response = dataList.stream()
                .map(QAResDto::convertToVo)
                .toList();
        KnowledgeQaResponseVo knowledgeQaResponseVo = new KnowledgeQaResponseVo();
        knowledgeQaResponseVo.setQaVoList(response);
        return knowledgeQaResponseVo;
    }
}
