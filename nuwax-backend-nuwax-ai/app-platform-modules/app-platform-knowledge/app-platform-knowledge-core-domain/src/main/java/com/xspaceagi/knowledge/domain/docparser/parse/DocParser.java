package com.xspaceagi.knowledge.domain.docparser.parse;

import com.xspaceagi.knowledge.core.spec.enums.KnowledgeDataTypeEnum;
import com.xspaceagi.knowledge.domain.model.KnowledgeDocumentModel;
import com.xspaceagi.system.spec.common.UserContext;

public interface DocParser {

    /**
     * 对文档进行分块，直接保存到数据库。
     * 按字符分词的逻辑是面向中文的，不适用英文。
     *
     * @param documentDto
     */
    void chunk(KnowledgeDocumentModel documentDto, UserContext userContext);

    /**
     * 是否支持该类型的文档解析
     *
     * @param dataType 文件类型 ;
     * @param docUrl   文档地址
     * @return true:此解析方式支持
     * @see KnowledgeDataTypeEnum 文件类型,判断是否自定义文本内容
     */
    Boolean isSupport(Integer dataType, String docUrl);


}