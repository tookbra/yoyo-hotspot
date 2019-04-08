package com.mars.yoyo.hotspot.mifi.event;

import lombok.Data;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * rent spring application event
 * @author tookbra
 * @date 2018/6/14
 * @description
 */
@Data
@ToString
public class RentEvent extends ApplicationEvent {

    private String deviceId;

    private Integer leaseId;

    public RentEvent( Object source, String deviceId, Integer leaseId) {
        super(source);
        this.deviceId = deviceId;
        this.leaseId = leaseId;
    }
}
