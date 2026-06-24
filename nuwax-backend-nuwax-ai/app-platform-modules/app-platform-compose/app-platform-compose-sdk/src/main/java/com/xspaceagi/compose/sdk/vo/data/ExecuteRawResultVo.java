
package com.xspaceagi.compose.sdk.vo.data;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExecuteRawResultVo {
    /**
     * 执行sql返回的数据
     */
    private List<Map<String, Object>> data;
    /**
     * 执行sql影响的行数
     */
    private Long rowNum;
    /**
     * 数据主键id,新增sql会有对应的id
     */
    private Long rowId;
}
