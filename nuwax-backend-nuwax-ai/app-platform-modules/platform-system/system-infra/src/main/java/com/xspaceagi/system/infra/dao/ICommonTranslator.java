package com.xspaceagi.system.infra.dao;

/**
 * model 和 entity 之间转换
 *
 * @param <T> model
 * @param <R> entity
 */
public interface ICommonTranslator<T, R> {

    /**
     * 将Entity转换为Model
     */
    T convertToModel(R entity);

    /**
     * 将Model转换为Entity
     */
    R convertToEntity(T model);

}
