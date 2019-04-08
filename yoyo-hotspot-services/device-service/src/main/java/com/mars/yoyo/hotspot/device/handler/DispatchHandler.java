package com.mars.yoyo.hotspot.device.handler;

import com.mars.yoyo.hotspot.device.constant.ChannelConstant;
import com.mars.yoyo.hotspot.device.packet.protocol.*;
import com.mars.yoyo.hotspot.device.service.BoxService;
import com.mars.yoyo.hotspot.device.cache.SessionCache;
import com.mars.yoyo.hotspot.device.packet.Message;
import com.mars.yoyo.hotspot.device.session.MifiSession;
import com.mars.yoyo.hotspot.device.session.SessionHolder;
import com.mars.yoyo.hotspot.util.DateUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;


/**
 * @author tookbra
 * @date 2018/4/6
 * @description
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class DispatchHandler extends ChannelDuplexHandler {

    @Autowired
    SessionCache sessionCache;

    @Autowired
    BoxService boxService;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof Message) {
            Message message = (Message) msg;

            switch ((short)message.getHeader().getCommandId()) {
                case 96:
                    //登陆
                    ConnectMessage connectMessage = (ConnectMessage)msg;
                    loggin(ctx, connectMessage);
                    break;
                case 97:
                    //心跳
                    HeartMessage heartResMessage = (HeartMessage)msg;
                    heart(ctx, heartResMessage);
                    break;
                case 98:
                    //查询机柜软件版本号
                    VersionResMessage versionResMessage = (VersionResMessage)msg;
                    boxService.versionReport(versionResMessage, ctx.channel());
                    break;
                case 100:
                    //查询库存
                    InventoryResMessage inventoryResMessage = (InventoryResMessage)msg;
                    inventory(ctx, inventoryResMessage);
                    break;
                case 101:
                    //借设备
                    RentResMessage rentResMessage = (RentResMessage)msg;
                    rent(ctx, rentResMessage);
                    break;
                case 102:
                    //还设备
                    ReturnMessage returnMessage = (ReturnMessage)msg;
                    returnPowner(ctx, returnMessage);
                    break;
                case 103:
                    //重启设备
                    RebootResMessage rebootResMessage = (RebootResMessage)msg;
                    reboot(ctx, rebootResMessage);
                    break;
                case 105:
                    //查询iccid
                    IccidResMessage iccidResMessage = (IccidResMessage) msg;
                    iccid(ctx, iccidResMessage);
                    break;
                case 128:
                    //强制弹出
                    PopUpResMessage popUpResMessage = (PopUpResMessage)msg;
                    popUp(ctx, popUpResMessage);
                    break;
                default:
                    super.channelRead(ctx, msg);
                    break;
            }
        }
        super.channelRead(ctx, msg);
    }


    /**
     * 登陆
     * @param ctx
     * @param connectMessage
     */
    private void loggin(ChannelHandlerContext ctx, ConnectMessage connectMessage) {
//        if(DateUtil.diffTimeStamp(System.currentTimeMillis() / DateUtil.TIMESTAMP,
//                    connectMessage.getHeader().getToken()) > 3) {
//            log.info("时间错误:{}", connectMessage.toString());
//            //时间错误
//            ConnectResMessage connectResMessage = new ConnectResMessage();
//            connectResMessage.setStatus((short) 2);
//            ctx.channel().writeAndFlush(connectResMessage);
//        } else {
            boxService.login(ctx, connectMessage);
//        }
    }

    private void heart(ChannelHandlerContext ctx, HeartMessage heartMessage) {
        log.info("heart: {}", heartMessage.toString());
        boxService.heart(ctx.channel());
    }

    private void version(ChannelHandlerContext ctx, VersionResMessage versionResMessage) {
        log.info("version: {}", versionResMessage.toString());
    }

    private void inventory(ChannelHandlerContext ctx, InventoryResMessage inventoryResMessage) {
        log.info("inventory: {}", inventoryResMessage.toString());
        boxService.inventoryReport(inventoryResMessage, ctx.channel());
    }

    private void rent(ChannelHandlerContext ctx, RentResMessage rentResMessage) {
        log.info("rent report: {}", rentResMessage.toString());
        boxService.rentReport(rentResMessage, ctx.channel());
    }

    private void returnPowner(ChannelHandlerContext ctx, ReturnMessage returnMessage) {
        log.info("return powner report: {}", returnMessage.toString());
        boxService.returnReport(returnMessage, ctx.channel());
    }

    private void reboot(ChannelHandlerContext ctx, RebootResMessage rebootResMessage) {
        log.info("reboot: {}", rebootResMessage.toString());
    }

    private void iccid(ChannelHandlerContext ctx, IccidResMessage iccidResMessage) {
        log.info("iccic: {}", iccidResMessage.toString());
    }

    private void popUp(ChannelHandlerContext ctx, PopUpResMessage popUpResMessage) {
        log.info("popUp: {}", popUpResMessage.toString());
        boxService.popUpReport(ctx.channel());
    }

    /**
     * 断开连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionHolder.remove(ctx.channel());
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = socketAddress.getAddress().getHostAddress();
        log.debug("lient={} close connection...", clientIp);
    }

    /**
     * 连接上来了
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = socketAddress.getAddress().getHostAddress();
        ctx.channel().attr(ChannelConstant.CHANNEL_SESSION).set(new MifiSession(ctx.channel().id().asShortText()));
        log.debug("client={} connect to Netty Server...", clientIp);
    }
}
