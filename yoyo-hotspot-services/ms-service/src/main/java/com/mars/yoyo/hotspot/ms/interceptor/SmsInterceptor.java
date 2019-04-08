package com.mars.yoyo.hotspot.ms.interceptor;

import com.mars.yoyo.hotspot.exception.BizFeignException;
import com.mars.yoyo.hotspot.ms.cache.SmsCache;
import com.mars.yoyo.hotspot.ms.cache.SmsCaptchaCache;
import com.mars.yoyo.hotspot.ms.config.SmsProperties;
import com.mars.yoyo.hotspot.ms.constant.SmsCacheConstant;
import com.mars.yoyo.hotspot.ms.dto.input.SmsInputDto;
import com.mars.yoyo.hotspot.ms.enums.SmsTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 短信拦截器
 * @author tookbra
 * @date 2018/5/16
 * @description
 */
@Slf4j
@Aspect
@Component
@Order(2)
public class SmsInterceptor {

    @Resource
    SmsProperties smsProperties;

    @Autowired
    SmsCache smsCache;

    @Autowired
    RedissonClient redissonClient;

    @Before("within(com.mars.yoyo.hotspot.ms.controller.SmsController)")
    public void round(JoinPoint joinPoint) {
        if(!smsProperties.getEnable()) {
            log.error("短信通道未启动");
            throw new BizFeignException("短信未启动");
        }

        Object[] args = joinPoint.getArgs();
        if(args[0] instanceof SmsInputDto) {
            SmsInputDto smsInputDto = (SmsInputDto) args[0];

            //验证码
            String phone = smsInputDto.getPhone();
            if (smsInputDto.getType().equals(SmsTypeEnum.REG.name())) {
                String cachePhone = smsCache.getCaptcha(phone);
                if (StringUtils.isEmpty(cachePhone)) {
                    smsCache.putCaptcha(phone);
                } else {
                    log.info("发短信太过频繁，每个手机60秒以内只能发一次...");
                    throw new BizFeignException("sms.send.single.limit");
                }
            }

            RAtomicLong smsCount = redissonClient.getAtomicLong(SmsCacheConstant.getSmsCount(phone));
            smsCount.expire(1, TimeUnit.DAYS);
            if(smsInputDto.getType().equals(SmsTypeEnum.REG.toString())
                    || smsInputDto.getType().equals(SmsTypeEnum.REG_EN.toString())) {
                if (smsCount.get() <= smsProperties.getTopNumberLimit()) {
                    smsCount.incrementAndGet();
                } else {
                    log.info("每天同一手机最多发短信次数:{}", smsProperties.getTopNumberLimit());
                    throw new BizFeignException("sms.send.day.limit");
                }
            }


        }
    }
}
