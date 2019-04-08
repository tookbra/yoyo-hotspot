package com.mars.yoyo.hotspot.device.handler;

import com.mars.yoyo.hotspot.device.packet.State;
import com.mars.yoyo.hotspot.device.packet.protocol.HeartResMessage;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tookbra
 * @date 2018/4/5
 * @description
 */
@Slf4j
public class ActiveIdleStateHandler extends ChannelDuplexHandler {

    public static final AttributeKey<State> STATE = AttributeKey.newInstance("state");

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if(e.state() == IdleState.ALL_IDLE) {
                if(ctx.channel().attr(STATE).get() != State.Connect){
                    log.warn("connect time out. ");
                    ctx.close();
                }else{
                    ctx.channel().writeAndFlush(new HeartResMessage());
                }
            }
        } else {
            ctx.fireUserEventTriggered(evt);
        }
    }
}
