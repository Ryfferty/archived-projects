package com.xspaceagi.system.domain.track.reporter;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OperationLogContext {

    private String objectId;

    private String objectName;

    private String preValue;

    private String laterValue;

    private String orgCode;

    /**
     * 机构ID
     */
    private Long orgId;

}
