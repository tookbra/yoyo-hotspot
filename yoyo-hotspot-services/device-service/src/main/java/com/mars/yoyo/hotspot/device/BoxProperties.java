package com.mars.yoyo.hotspot.device;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
@Data
@Component
@ConfigurationProperties("wifi.iot.server")
public class BoxProperties {
    /**
     * 端口号
     */
    private int port;

    private int bossLoopGroupCount;

    private int workerLoopGroupCount;
}
