package com.mars.yoyo.hotspot.common.dps.aspect;

import com.mars.yoyo.hotspot.common.dps.hystrix.HystrixCredentialsContext;
import com.mars.yoyo.hotspot.common.dps.hystrix.MicroContext;
import com.mars.yoyo.hotspot.dto.output.RestError;
import com.mars.yoyo.hotspot.exception.MicroException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Constructor;

/**
 * feign错误切面
 * @author tookbra
 * @date 2018/5/30
 * @description
 */
@Slf4j
@Aspect
public class FeignExceptionHandlerAspect implements Ordered {

    private int order;

    public FeignExceptionHandlerAspect() {
        this(Byte.MAX_VALUE);
    }

    public FeignExceptionHandlerAspect(int order) {
        this.order = order;
    }

    @AfterReturning(pointcut = "execution(* com.mars.yoyo.hotspot..client..*.*(..))", returning = "data")
    public void after(JoinPoint joinPoint, Object data) throws Throwable {
        MethodInvocationProceedingJoinPoint mjp = (MethodInvocationProceedingJoinPoint) joinPoint;
        Class<?> clazz = mjp.getSignature().getDeclaringType();
        if (AnnotationUtils.findAnnotation(clazz, FeignClient.class) == null) {
            log.debug("未找到feign 客户端");
            return;
        }
        MicroContext microContext = HystrixCredentialsContext.getInstance().get();
        RestError restResult = null;
        if (microContext != null && (restResult = microContext.getRestResult()) != null) {
            if (!restResult.success) {
//                Class<?> exceptionClass = Class.forName(restResult.getException());
//                Constructor<?> constructor = exceptionClass.getConstructor(new Class[]{String.class, String.class});
//                throw (MicroException) constructor
//                        .newInstance(new Object[]{restResult.getMsg(), restResult.getCode()});
                throw new MicroException(restResult.getMsg(), restResult.getCode());
            }
        }
        HystrixCredentialsContext.getInstance().remove();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
