package com.mars.yoyo.hotspot.device.constant;

import com.mars.yoyo.hotspot.device.session.MifiSession;
import io.netty.util.AttributeKey;

/**
 * @author tookbra
 * @date 2018/6/13
 * @description
 */
public class ChannelConstant {

    public static final AttributeKey<MifiSession> CHANNEL_SESSION = AttributeKey.valueOf("channelSession");
}
