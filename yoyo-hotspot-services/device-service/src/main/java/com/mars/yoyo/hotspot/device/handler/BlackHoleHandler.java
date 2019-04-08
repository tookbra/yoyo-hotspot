package com.mars.yoyo.hotspot.device.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tookbra
 * @date 2018/4/5
 * @description
 */
@Slf4j
@Sharable
public class BlackHoleHandler extends ChannelDuplexHandler {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.warn("not found handler to msg={}", msg.toString());
    	ReferenceCountUtil.safeRelease(msg);
    }
}
