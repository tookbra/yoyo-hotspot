package com.mars.yoyo.hotspot.common.dps.hystrix;

import com.netflix.hystrix.HystrixInvokable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;

/**
 * @author tookbra
 * @date 2018/5/30
 * @description
 */
public class SecurityContextRegistratorCommandHook extends HystrixCommandExecutionHook {

    @Override
    public <T> void onStart(HystrixInvokable<T> commandInstance) {
        if (!HystrixRequestContext.isCurrentThreadInitialized()) {
            HystrixRequestContext.initializeContext();
        }
    }
}
