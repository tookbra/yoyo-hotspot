package com.mars.yoyo.hotspot.common.dps.hystrix;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import org.springframework.context.annotation.Configuration;


/**
 * @author tookbra
 * @date 2018/5/30
 * @description
 */
@Configuration
public class HystrixCredentialsContext {
    private static final HystrixRequestVariableDefault<MicroContext> securityContextVariable = new HystrixRequestVariableDefault<>();

    private HystrixCredentialsContext() {
    }

    public static HystrixRequestVariableDefault<MicroContext> getInstance() {
        return securityContextVariable;
    }


}
