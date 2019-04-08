package com.mars.yoyo.hotspot.common.dps.hystrix;

import com.mars.yoyo.hotspot.dto.output.RestError;
import lombok.Data;

/**
 * @author tookbra
 * @date 2018/5/30
 * @description
 */
@Data
public class MicroContext {

    public RestError restResult;

    public static MicroContext getInterface() {
        MicroContext microSecurityContext = new MicroContext();
        return microSecurityContext;
    }
}
