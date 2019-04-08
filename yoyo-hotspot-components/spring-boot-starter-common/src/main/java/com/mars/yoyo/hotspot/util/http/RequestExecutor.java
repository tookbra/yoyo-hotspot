package com.mars.yoyo.hotspot.util.http;

/**
 * @author tookbra
 * @date 2018/4/8
 * @description
 */
public interface RequestExecutor<R, T> {

    /**
     * http 执行
     * @param uri url
     * @param data 数据
     * @return
     */
    R execute(String uri, T data);
}
