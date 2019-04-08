package com.mars.yoyo.hotspot.mifi.service;

import java.math.BigDecimal;

/**
 * @author tookbra
 * @date 2018/7/8
 * @description
 */
public interface RedEnvelopeService {

    /**
     * 领取红包
     * @param userId 用户id
     * @param lang 用户环境
     * @return 红包金额
     */
    BigDecimal receiveRed(int userId, String lang);
}
