package com.xspaceagi.system.spec.id;

/**
 * ID生成器
 */
public interface IdGenerator {

    /**
     * 生成唯一ID
     */
    long nextId();

    /**
     * 生成唯一ID,指定位数
     */
    long nextId(int digits);

    /**
     * 生成唯一ID（字符串）
     */
    String nextIdStr();

    /**
     * 生成唯一ID（字符串）,指定位数
     */
    String nextIdStr(int digits);
}