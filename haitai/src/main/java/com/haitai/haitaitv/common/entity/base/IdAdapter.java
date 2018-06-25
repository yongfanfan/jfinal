package com.haitai.haitaitv.common.entity.base;

import org.beetl.sql.core.Tail;

/**
 * 用于标记实体拥有Integer类型id字段的setter和getter
 * Created by liuzhou on 2017/4/17.
 */
public interface IdAdapter<T> extends Tail {

    T getId();

    void setId(T id);

}
