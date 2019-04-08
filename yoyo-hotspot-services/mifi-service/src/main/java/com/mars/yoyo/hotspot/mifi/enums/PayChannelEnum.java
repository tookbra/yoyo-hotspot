package com.mars.yoyo.hotspot.mifi.enums;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author tookbra
 * @date 2018/6/4
 * @description
 */
public enum  PayChannelEnum {
    BALANCE("balance", 1), WECHAT_PAY("wepay", 2), ALIPAY("alipay",3), PAYPAL("paypal", 4);

    String channel;
    int code;

    PayChannelEnum(String channel, int code) {
        this.channel = channel;
        this.code = code;
    }

    /**
     *
     * @param channel
     * @return
     */
    public static PayChannelEnum getChannel(String channel) {
        Optional<PayChannelEnum> payChannelEnum = Arrays.stream(PayChannelEnum.values())
                .filter(v -> Objects.equals(v.channel, channel))
                .findFirst();
        return payChannelEnum.orElseThrow(() -> new EntityNotFoundException(""));
    }

    public int getCode() {
        return code;
    }

    public String getChannel() {
        return channel;
    }
}
