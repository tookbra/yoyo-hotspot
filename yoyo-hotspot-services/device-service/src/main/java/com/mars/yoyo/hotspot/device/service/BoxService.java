package com.mars.yoyo.hotspot.device.service;

import com.mars.yoyo.hotspot.device.dto.input.ServerInput;
import com.mars.yoyo.hotspot.device.dto.input.UpgradeInput;
import com.mars.yoyo.hotspot.device.packet.protocol.*;
import com.mars.yoyo.hotspot.device.session.MifiSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author tookbra
 * @date 2018/4/17
 * @description
 */
public interface BoxService {

    /**
     * 登陆
     * @param ctx
     * @param connectMessage
     */
    void login(ChannelHandlerContext ctx, ConnectMessage connectMessage);

    /**
     * 机柜软件版本号响应
     * @param versionResMessage
     * @param channel
     */
    void versionReport(VersionResMessage versionResMessage, Channel channel);

    /**
     * 查询库存响应
     * @param inventoryResMessage
     * @param channel
     */
    void inventoryReport(InventoryResMessage inventoryResMessage, Channel channel);

    /**
     * 借充电宝响应
     * @param rentResMessage
     * @param channel
     */
    void rentReport(RentResMessage rentResMessage, Channel channel);

    /**
     * 还充电宝响应
     * @param returnMessage
     * @param channel
     */
    void returnReport(ReturnMessage returnMessage, Channel channel);

    /**
     * 查询版本号
     * @param boxId
     */
    void queryVersion(String boxId);

    /**
     * 设置服务器地址
     * @param serverInput
     */
    void setServer(ServerInput serverInput);

    /**
     * 查询库存
     * @param boxId
     */
    void queryInventory(String boxId);

    /**
     * 重启设备
     * @param boxId
     */
    void reboot(String boxId);

    /**
     * 远程升级
     * @param upgradeInput
     */
    void upgrade(UpgradeInput upgradeInput);

    /**
     * 查询iccid
     * @param boxId
     */
    void queryIccid(String boxId);

    /**
     * 强制弹出充电宝
     * @param boxId
     * @param slot
     */
    void popUp(String boxId, byte slot);

    /**
     *
     */
    void popUpReport(Channel channel);

    /**
     * 借充电宝
     * @param slot 槽位编号
     */
    void rent(Channel channel, short slot);

    /**
     * 借充电宝
     * @param boxId 设备id
     */
    void rent(String boxId);

    /**
     * 心跳
     * @param channel 通道
     */
    void heart(Channel channel);
}
