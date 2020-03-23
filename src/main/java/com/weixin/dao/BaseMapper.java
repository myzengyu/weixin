package com.weixin.dao;

import java.io.Serializable;

public interface BaseMapper<ids extends Serializable, T extends Object> {
    /**
     * 做添加操作
     *
     * @param t
     */
    void insert(T t);
}
