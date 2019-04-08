package com.mars.yoyo.hotspot.ms.cache;

import com.mars.yoyo.hotspot.ms.constant.SmsCacheConstant;
import com.mars.yoyo.hotspot.ms.domain.SmsTemplate;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author tookbra
 * @date 2018/5/16
 * @description
 */
public class SmsTemplateCache {

    /**
     * 存储短信模板
     * @return
     */
    @CachePut(cacheNames = SmsCacheConstant.SMS_TEMPLATE_PREFIX, key = "#p0.code")
    public SmsTemplate putSmsTemp(SmsTemplate smsTemplate) {
        return smsTemplate;
    }

    /**
     * 获取短信模板
     * @param code 模版编码
     * @return
     */
    @Cacheable(cacheNames = SmsCacheConstant.SMS_TEMPLATE_PREFIX, key = "#p0")
    public SmsTemplate getSmsTemp(String code) {
        return null;
    }
}
