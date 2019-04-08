package com.mars.yoyo.hotspot.db.aspect;

import com.mars.yoyo.hotspot.db.datasource.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author tookbra
 * @date 2018/5/17
 * @description
 */
@Slf4j
@Aspect
@Component
@Order(-1)
public class DataSourceAspect {

    @Pointcut("execution(* com.mars.yoyo.hotspot..*Mapper.*(..))")
    public void dataSourcePointCut() {
    }

    @Around("dataSourcePointCut()")
    public Object doBefore(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        try {
            if (methodName.startsWith("insert") || methodName.startsWith("update") || methodName.startsWith("delete")) {
                DataSourceContextHolder.setDbType(DataSourceContextHolder.DbType.MASTER);
                log.debug("set database connection to write only");
            } else {
                DataSourceContextHolder.DbType dbType = DataSourceContextHolder.getSlaveRandom();
                DataSourceContextHolder.setDbType(dbType);
                log.debug("set database connection to read only. dbType:{}", dbType.name());
            }
            return proceedingJoinPoint.proceed();
        } finally {
            DataSourceContextHolder.clearDbType();
            log.debug("restore database connection");
        }
    }


}
