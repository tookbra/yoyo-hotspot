package com.mars.yoyo.hotspot.cache;

import lombok.Data;
import org.redisson.config.ClusterServersConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author tookbra
 * @date 2018/4/8
 * @description
 */
@Data
@ConfigurationProperties(prefix = "redisson.config")
public class RedissonProperties extends ClusterServersConfig {

    /**
     * 节点地址
     */
    private String address;
    /**
     * 连接池大小
     * 默认32
     */
    private int connectionPoolSize = 64;
    /**
     * 最小空闲连接数
     * 默认50
     */
    private int connectionMinimumIdleSize = 50;
    /**
     * 默认单机模式
     */
    private RedissonType type = RedissonType.SINGLE;
}
