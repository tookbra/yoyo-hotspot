package com.mars.yoyo.hotspot.ms.service;

import com.mars.yoyo.hotspot.ms.domain.SmsCaptcha;

/**
 * @author tookbra
 * @date 2018/5/28
 * @description
 */
public interface SmsCaptchaService {

    /**
     * 添加验证码
     * @param smsCaptcha
     * @return
     */
    SmsCaptcha addCaptcha(SmsCaptcha smsCaptcha);
}
