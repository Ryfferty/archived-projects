package com.xspaceagi.knowledge.core.adapter.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Rust 服务统一响应格式
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
public class RustApiResponse<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 响应码
     */
    @JsonProperty("code")
    private String code;
    
    /**
     * 响应消息
     */
    @JsonProperty("message")
    private String message;
    
    /**
     * 响应数据
     */
    @JsonProperty("data")
    private T data;
    
    /**
     * 追踪ID
     */
    @JsonProperty("tid")
    private String tid;
    
    /**
     * 判断是否成功
     */
    public boolean isSuccess() {
        return "0000".equals(code);
    }
}
