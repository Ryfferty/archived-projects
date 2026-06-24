package com.xspaceagi.knowledge.domain.model.excel;


import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import lombok.*;

/**
 * 知识库问答Excel模型
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KnowledgeQaExcelModel {
    /**
     * 问题
     */
    @ColumnWidth(60)
    @ExcelProperty("问题")
    private String question;
    /**
     * 答案
     */
    @ColumnWidth(80)
    @ExcelProperty("答案")
    private String answer;
}
