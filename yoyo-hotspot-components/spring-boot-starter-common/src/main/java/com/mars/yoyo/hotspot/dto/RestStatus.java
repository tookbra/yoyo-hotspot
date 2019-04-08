package com.mars.yoyo.hotspot.dto;

/**
 * @author tookbra
 * @date 2018/5/16
 * @description
 */
public interface RestStatus {

    /**
     * 状态码
     * @return
     */
    int code();

    /**
     * 枚举名称
     * @return
     */
    String name();

    /**
     * 消息
     * @return
     */
    String message();
}
