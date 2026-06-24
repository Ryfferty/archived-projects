package com.xspaceagi.system.spec.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * 时间范围对象,承接前端一个范围时间的数值
 *
 * @author soddy
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RangeTimeVo extends ArrayList<String> implements Serializable {

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;


}
