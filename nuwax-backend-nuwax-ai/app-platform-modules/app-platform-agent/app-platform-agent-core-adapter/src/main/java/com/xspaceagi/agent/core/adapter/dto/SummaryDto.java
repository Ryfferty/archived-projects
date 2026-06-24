package com.xspaceagi.agent.core.adapter.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

import java.io.Serializable;

@Data
public class SummaryDto implements Serializable {

    @JsonPropertyDescription("内容压缩，压缩率控制在80%以上，但最大不能超过2000token")
    private String content;

    @JsonPropertyDescription("符合上下文交流的标题，控制在5到15字，禁止生成与主题意义不想干的标题，对于毫无意义的上下文，例如用户输入错误或者随意输入的内容不用总结，返回空即可")
    private String topic;

}
