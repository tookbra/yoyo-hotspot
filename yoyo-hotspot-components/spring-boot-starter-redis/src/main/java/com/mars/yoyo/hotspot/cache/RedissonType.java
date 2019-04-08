package com.mars.yoyo.hotspot.cache;

/**
 * @author tookbra
 * @date 2018/4/17
 * @description
 */
public enum RedissonType {

    /**
     * 集群模式
     */
    CLUSTER,
    /**
     * 云托管模式
     */
    REPLICATED,
    /**
     * 单节点模式
     */
    SINGLE,
    /**
     * 哨兵模式
     */
    SENTINEL,
    /**
     * 主从模式
     */
    MASTERSLAVE


}
