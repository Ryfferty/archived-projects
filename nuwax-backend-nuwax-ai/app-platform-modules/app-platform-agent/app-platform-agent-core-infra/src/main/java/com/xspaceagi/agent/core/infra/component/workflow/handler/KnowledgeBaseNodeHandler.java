package com.xspaceagi.agent.core.infra.component.workflow.handler;

import com.xspaceagi.agent.core.adapter.dto.config.workflow.KnowledgeNodeConfigDto;
import com.xspaceagi.agent.core.adapter.dto.config.workflow.WorkflowNodeDto;
import com.xspaceagi.agent.core.infra.component.knowledge.SearchContext;
import com.xspaceagi.agent.core.infra.component.workflow.WorkflowContext;
import com.xspaceagi.knowledge.sdk.response.KnowledgeQaVo;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KnowledgeBaseNodeHandler extends AbstractNodeHandler {

    @Override
    protected Object executeNode(WorkflowContext workflowContext, WorkflowNodeDto node) {
        KnowledgeNodeConfigDto knowledgeNodeConfigDto = (KnowledgeNodeConfigDto) node.getNodeConfig();
        Map<String, Object> params = extraBindValueMap(workflowContext, node, knowledgeNodeConfigDto.getInputArgs());
        SearchContext searchContext = new SearchContext();
        Object kw = params.get("Query");
        if (kw == null || kw.equals("")) {
            return Map.of();
        }
        if (CollectionUtils.isEmpty(knowledgeNodeConfigDto.getKnowledgeBaseConfigs())) {
            return Map.of();
        }
        List<Long> ids = knowledgeNodeConfigDto.getKnowledgeBaseConfigs().stream().map(KnowledgeNodeConfigDto.KnowledgeBaseConfigDto::getKnowledgeBaseId).collect(Collectors.toList());
        searchContext.setQuery(kw.toString());
        searchContext.setSearchStrategy(knowledgeNodeConfigDto.getSearchStrategy());
        searchContext.setMatchingDegree(knowledgeNodeConfigDto.getMatchingDegree());
        searchContext.setMaxRecallCount(knowledgeNodeConfigDto.getMaxRecallCount());
        searchContext.setKnowledgeBaseIds(ids);
        searchContext.setRequestId(workflowContext.getRequestId());
        searchContext.setAgentContext(workflowContext.getAgentContext());
        List<KnowledgeQaVo> knowledgeQaVos = workflowContext.getWorkflowContextServiceHolder().getKnowledgeBaseSearcher().search(searchContext).block();
        //从knowledgeQaVos中提取answerList
        if (knowledgeQaVos == null) {
            return Map.of();
        }
        List<Map<String, String>> answerList = knowledgeQaVos.stream().map(knowledgeQaVo -> {
            Map<String, String> answer = new HashMap<>();
            answer.put("question", knowledgeQaVo.getQuestion());
            answer.put("output", knowledgeQaVo.getAnswer());
            answer.put("rawText", knowledgeQaVo.getRawTxt());
            return answer;
        }).collect(Collectors.toList());

        Map<String, Object> output = new HashMap<>();
        output.put("outputList", answerList);
        return output;
    }
}
