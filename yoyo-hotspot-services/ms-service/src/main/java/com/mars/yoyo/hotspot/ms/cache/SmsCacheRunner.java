package com.mars.yoyo.hotspot.ms.cache;

import com.mars.yoyo.hotspot.ms.domain.SmsTemplate;
import com.mars.yoyo.hotspot.ms.service.SmsTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author tookbra
 * @date 2018/5/16
 * @description
 */
@Slf4j
@Component
public class SmsCacheRunner implements CommandLineRunner {

    @Autowired
    SmsTemplateService smsTemplateService;

    @Autowired
    SmsTemplateCache smsTemplateCache;

    @Override
    public void run(String... args) throws Exception {
        List<SmsTemplate> smsTemplateList = smsTemplateService.getEanbled();
        if (CollectionUtils.isEmpty(smsTemplateList)) {
            log.warn("config sms template init over  because the configDictionaries is Empty.....");
            return;
        }
        smsTemplateList.forEach(smsTemplate ->
                smsTemplateCache.putSmsTemp(smsTemplate)
        );
    }
}
