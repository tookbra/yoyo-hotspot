package com.mars.yoyo.hotspot.mifi.event;

import com.mars.yoyo.hotspot.mifi.dto.LeaseDto;
import lombok.Data;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * @author tookbra
 * @date 2018/8/29
 * @description
 */
@Data
@ToString
public class ReturnEvent extends ApplicationEvent {

    private LeaseDto lease;

    public ReturnEvent( Object source, LeaseDto lease) {
        super(source);
        this.lease = lease;
    }
}
