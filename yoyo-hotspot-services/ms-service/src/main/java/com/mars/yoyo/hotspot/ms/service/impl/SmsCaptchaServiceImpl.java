package com.mars.yoyo.hotspot.ms.service.impl;

import com.mars.yoyo.hotspot.ms.dao.SmsCaptchaMapper;
import com.mars.yoyo.hotspot.ms.domain.SmsCaptcha;
import com.mars.yoyo.hotspot.ms.service.SmsCaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author tookbra
 * @date 2018/6/3
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = false)
public class SmsCaptchaServiceImpl implements SmsCaptchaService {


    @Resource
    SmsCaptchaMapper smsCaptchaMapper;

    @Override
    @Cacheable(value="test", key="#p0.phone")
    public SmsCaptcha addCaptcha(SmsCaptcha smsCaptcha) {
        smsCaptchaMapper.insertSelective(smsCaptcha);
        return smsCaptcha;
    }
}
