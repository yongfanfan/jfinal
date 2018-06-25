package com.haitai.haitaitv.component.beetl.ext;

import com.haitai.haitaitv.component.beetl.annotation.Customize;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.db.KeyHolder;

import java.util.List;

/**
 * 为BaseMapper所有方法提供默认空实现
 * 推荐将原本继承BaseMapper的dao改为继承此接口，因为此接口给非批量的insert方法添加了返回值
 * 此接口配合@Customize注解可实现自定义方法
 *
 * @see Customize
 * Created by liuzhou on 2017/3/27.
 */
public interface BaseCustom<T> {

    default int insert(T entity) {
        return 0;
    }

    default int insert(T entity, boolean assignKey) {
        return 0;
    }

    default int insertTemplate(T entity) {
        return 0;
    }

    default int insertTemplate(T entity, boolean assignKey) {
        return 0;
    }

    default void insertBatch(List<T> list) {
    }

    default KeyHolder insertReturnKey(T entity) {
        return null;
    }

    default int updateById(T entity) {
        return 0;
    }

    default int updateTemplateById(T entity) {
        return 0;
    }

    default int deleteById(Object key) {
        return 0;
    }

    default T unique(Object key) {
        return null;
    }

    default T single(Object key) {
        return null;
    }

    default List<T> all() {
        return null;
    }

    default List<T> all(int start, int size) {
        return null;
    }

    default long allCount() {
        return 0;
    }

    default List<T> template(T entity) {
        return null;
    }

    default <T> T templateOne(T entity) {
        return null;
    }

    default List<T> template(T entity, int start, int size) {
        return null;
    }

    default long templateCount(T entity) {
        return 0;
    }

    default List<T> execute(String sql, Object... args) {
        return null;
    }

    default int executeUpdate(String sql, Object... args) {
        return 0;
    }

    default SQLManager getSQLManager() {
        return null;
    }

}
