package com.xspaceagi.domain.model.valueobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Rust 服务响应包装类
 * 用于包装 Rust 服务返回的标准响应格式
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RustServiceResponse<T> {

    /**
     * 响应代码
     */
    private String code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 事务ID
     */
    private String tid;

    /**
     * 判断响应是否成功
     */
    public boolean isSuccess() {
        return "0000".equals(code);
    }
} 