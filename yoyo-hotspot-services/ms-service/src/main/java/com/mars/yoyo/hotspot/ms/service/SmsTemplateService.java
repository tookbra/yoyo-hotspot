package com.mars.yoyo.hotspot.ms.service;

import com.mars.yoyo.hotspot.ms.domain.SmsTemplate;

import java.util.List;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
public interface SmsTemplateService {

    /**
     * 获取短信模板
     * @param code 短信模板code
     * @return {@see SmsTemplate}
     */
    SmsTemplate getByCode(String code);

    /**
     * 获取启用的短信模板
     * @return
     */
    List<SmsTemplate> getEanbled();
}
