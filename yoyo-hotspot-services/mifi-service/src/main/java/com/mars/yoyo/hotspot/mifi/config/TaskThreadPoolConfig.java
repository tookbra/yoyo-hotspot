package com.mars.yoyo.hotspot.mifi.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author tookbra
 * @date 2018/4/16
 * @description
 */
@Data
@Configurable
@ConfigurationProperties(prefix = "spring.task.pool")
public class TaskThreadPoolConfig {

    /**
     * 核心线程数
     */
    private int corePoolSize = 5;

    /**
     * 最大线程数
     */
    private int maxPoolSize = 50;

    /**
     * 线程池维护线程所允许的空闲时间
     */
    private int keepAliveSeconds = 60;

    /**
     * 队列长度
     */
    private int queueCapacity = 10000;

    /**
     * 线程名称前缀
     */
    private String threadNamePrefix = "Box-AsyncTask-";
}
