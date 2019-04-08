package com.mars.yoyo.hotspot.device.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.mars.yoyo.hotspot.device.cache.SessionCache;
import com.mars.yoyo.hotspot.device.client.MifiClient;
import com.mars.yoyo.hotspot.device.constant.ChannelConstant;
import com.mars.yoyo.hotspot.device.domain.Device;
import com.mars.yoyo.hotspot.device.domain.DeviceItem;
import com.mars.yoyo.hotspot.device.dto.input.RentReportInput;
import com.mars.yoyo.hotspot.device.dto.input.ServerInput;
import com.mars.yoyo.hotspot.device.dto.input.UpgradeInput;
import com.mars.yoyo.hotspot.device.packet.protocol.*;
import com.mars.yoyo.hotspot.device.service.BoxService;
import com.mars.yoyo.hotspot.device.service.DeviceItemService;
import com.mars.yoyo.hotspot.device.service.DeviceService;
import com.mars.yoyo.hotspot.device.session.DefaultSessionManager;
import com.mars.yoyo.hotspot.device.session.MifiSession;
import com.mars.yoyo.hotspot.device.session.SessionHolder;
import com.mars.yoyo.hotspot.result.RestResult;
import com.mars.yoyo.hotspot.util.StringUtil;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author tookbra
 * @date 2018/4/17
 * @description
 */
@Slf4j
@Service
@Async
public class BoxServiceImpl implements BoxService {

    private static final Map<String, Channel> channelMap =  new ConcurrentHashMap<>();

    @Autowired
    DefaultSessionManager sessionManager;

    @Autowired
    SessionCache sessionCache;

    @Autowired
    DeviceService deviceService;

    @Autowired
    DeviceItemService deviceItemService;

    @Autowired
    MifiClient mifiClient;

    @Override
    public void login(ChannelHandlerContext ctx, ConnectMessage connectMessage) {
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = socketAddress.getAddress().getHostAddress();

        MifiSession mifiSession = new MifiSession(ctx.channel().id().asShortText());
        SessionHolder.put(ctx.channel().id().asShortText(), ctx.channel());
        if (ctx.channel() == null || !ctx.channel().isActive()) {
            log.info("断开连接={}", clientIp);
            ctx.disconnect();// 断开连接事件(与对方的连接断开)
            log.warn("close client={} as its channel is not active ", clientIp);
        }
        mifiSession.setDeviceId(connectMessage.getBoxID());
        sessionManager.save(mifiSession);
        log.info("channelId:{}", mifiSession.getSessionId());

        Device device = deviceService.findByBoxId(connectMessage.getBoxID());
        if(device == null) {
            device = new Device();
            device.setBoxId(connectMessage.getBoxID());
            device.setToken(DigestUtils.md5Hex(connectMessage.getBoxID()));
            device.setLastHeart(DateTime.now().toDate());
            deviceService.add(device);
        } else {
            deviceService.save(device);
        }

        ConnectResMessage connectResMessage = new ConnectResMessage();
        connectResMessage.setStatus((short) 1);
        SessionHolder.get(mifiSession.getSessionId()).writeAndFlush(connectResMessage);

        queryVersion(connectMessage.getBoxID());
        queryInventory(connectMessage.getBoxID());

        ctx.channel().attr(ChannelConstant.CHANNEL_SESSION).set(mifiSession);
    }

    @Override
    public void versionReport(VersionResMessage versionResMessage, Channel channel) {
        log.info("versionResMessage={}", versionResMessage.toString());
        String channelId = channel.id().asShortText();
        MifiSession mifiSession = sessionCache.getSession(channelId);
        if(mifiSession != null) {
            Device device = deviceService.findByBoxId(mifiSession.getDeviceId());
            device.setLastHeart(DateTime.now().toDate());
            device.setVersion(versionResMessage.getSoftVer());
            deviceService.save(device);
        }
    }

    @Override
    public void inventoryReport(InventoryResMessage inventoryResMessage, Channel channel) {
        String channelId = channel.id().asShortText();
        MifiSession mifiSession = sessionCache.getSession(channelId);
        if(mifiSession != null) {
            List<DeviceItem> list = deviceItemService.findByBoxId(mifiSession.getDeviceId());
            List<Inventory> inventoryList = inventoryResMessage.getInventoryList();
            Date date = DateTime.now().toDate();
            if(!CollectionUtils.isEmpty(inventoryList)) {
                if(CollectionUtils.isEmpty(list)) {
                    List<DeviceItem> finalList = list;
                    inventoryList.forEach(inventory -> {
                        DeviceItem deviceItem = new DeviceItem();
                        deviceItem.setPowerBankId(inventory.getPowerBandId());
                        deviceItem.setSlot(inventory.getSlot());
                        deviceItem.setLevel(inventory.getLevel());
                        deviceItem.setDeviceId(mifiSession.getDeviceId());
                        deviceItem.setItemType(1);
                        deviceItem.setLeased(false);
                        deviceItem.setDeleted(0);
                        deviceItem.setModifyTime(date);
                        finalList.add(deviceItem);
                    });
                    log.info("add device item. item={}", finalList.toString());
                    deviceItemService.add(finalList);
                } else  {

                    List<DeviceItem> updateList = new ArrayList<>();
                    List<DeviceItem> addList = new ArrayList<>();
                    Map<String, DeviceItem> map =  Maps.uniqueIndex(list.iterator(), new Function<DeviceItem, String>() {
                        @Override
                        public String apply(DeviceItem deviceItem) {
                            return String.valueOf(deviceItem.getPowerBankId());
                        }
                    });

                    inventoryList.forEach(inventory -> {
                        DeviceItem deviceItem = map.get(inventory.getPowerBandId());
                        if(deviceItem != null) {
                            deviceItem.setModifyTime(date);
                            deviceItem.setSlot(inventory.getSlot());
                            deviceItem.setDeviceId(mifiSession.getDeviceId());
                            deviceItem.setLeased(false);
                            deviceItem.setLevel(inventory.getLevel());
                            updateList.add(deviceItem);
                        } else {
                            deviceItem = new DeviceItem();
                            deviceItem.setPowerBankId(inventory.getPowerBandId());
                            deviceItem.setSlot(inventory.getSlot());
                            deviceItem.setLevel(inventory.getLevel());
                            deviceItem.setDeviceId(mifiSession.getDeviceId());
                            deviceItem.setItemType(1);
                            deviceItem.setLeased(false);
                            deviceItem.setDeleted(0);
                            deviceItem.setModifyTime(date);
                            addList.add(deviceItem);
                        }
                    });


                    if(!CollectionUtils.isEmpty(updateList)) {
                        log.info("save device item. item={}", updateList.toString());
                        deviceItemService.save(updateList);
                    }

                    if(!CollectionUtils.isEmpty(addList)) {
                        log.info("add device item. item={}", addList.toString());
                        deviceItemService.add(addList);
                    }
                }
            } else {
                // 更新所有设备为已租借
                log.info("device item is empty.");
                deviceItemService.updateLeasedByDeviceId(mifiSession.getDeviceId());
            }
        }
    }

    @Override
    public void rentReport(RentResMessage rentResMessage, Channel channel) {
        String channelId = channel.id().asShortText();
        MifiSession mifiSession = sessionCache.getSession(channelId);
        if(mifiSession != null) {
            if (!HystrixRequestContext.isCurrentThreadInitialized()) {
                HystrixRequestContext.initializeContext();
            }
            RentReportInput rentReportInput = new RentReportInput();
            rentReportInput.setDeviceId(mifiSession.getDeviceId());
            rentReportInput.setPowerBankId(rentResMessage.getPowerBankId());
            rentReportInput.setSlot(rentResMessage.getSlot());
            rentReportInput.setSuccess(rentResMessage.getResult() == 1);
            mifiClient.rentReport(rentReportInput);

            this.queryInventory(channel);
        }
    }

    @Override
    public void returnReport(ReturnMessage returnMessage, Channel channel) {
        log.info("returnMessage={}", returnMessage);
        String channelId = channel.id().asShortText();
        MifiSession mifiSession = sessionCache.getSession(channelId);
        if(mifiSession != null) {
            if (!HystrixRequestContext.isCurrentThreadInitialized()) {
                HystrixRequestContext.initializeContext();
            }
            RestResult<Boolean> restResult = mifiClient.returnReport(mifiSession.getDeviceId(), returnMessage.getPowerBankId());
            log.info("request mifi returnReport={}", restResult.toString());
            if(restResult.isSuccess()) {
                ReturnResMessage returnResMessage = new ReturnResMessage();
                DeviceItem deviceItem = deviceItemService.findByPowerId(mifiSession.getDeviceId(), returnMessage.getPowerBankId());
                if(deviceItem == null) {
                    deviceItem = new DeviceItem();

                    deviceItem.setPowerBankId(returnMessage.getPowerBankId());
                    deviceItem.setSlot((short) returnMessage.getSlot());
                    deviceItem.setLevel((short)4);
                    deviceItem.setDeviceId(mifiSession.getDeviceId());
                    deviceItem.setItemType(1);
                    deviceItem.setLeased(false);
                    deviceItem.setDeleted(0);
                    deviceItem.setModifyTime(DateTime.now().toDate());
                    deviceItemService.add(deviceItem);
                } else {
                    deviceItem.setDeviceId(mifiSession.getDeviceId());
                    deviceItem.setLeased(false);
                    deviceItem.setModifyTime(DateTime.now().toDate());
                    deviceItem.setSlot((short) returnMessage.getSlot());
                    deviceItemService.save(deviceItem);
                }

                if(deviceItem == null) {
                    log.info("nof found deviceItem; powerBankId={}", returnMessage.getPowerBankId());
                    returnResMessage.setSlot(returnMessage.getSlot());
                    returnResMessage.setResult(1);
                    channel.writeAndFlush(returnResMessage);
                } else {
                    returnResMessage.setSlot(returnMessage.getSlot());
                    returnResMessage.setResult(1);
                    channel.writeAndFlush(returnResMessage);
                    log.info("return mifi deviceItem success={}", deviceItem.toString());
                }
            } else {
                ReturnResMessage returnResMessage = new ReturnResMessage();
                log.info("return deviceItem fail; powerBankId={}", returnMessage.getPowerBankId());
                returnResMessage.setSlot(returnMessage.getSlot());
                returnResMessage.setResult(0);
                channel.writeAndFlush(returnResMessage);
            }
            this.queryInventory(channel);
        }
    }

    @Override
    public void queryVersion(String boxId) {
        MifiSession mifiSession = sessionManager.get(boxId);
        if (mifiSession != null) {
            Channel channel = SessionHolder.get(mifiSession.getSessionId());

            if(channel != null && channel.isActive() && channel.isWritable()) {

                this.queryBoxVerion(boxId, channel);
            }
        }

    }

    @Override
    public void setServer(ServerInput serverInput) {
        MifiSession mifiSession = sessionCache.getSession(serverInput.getBoxId());
        if(mifiSession != null) {
            Channel channel = SessionHolder.get(mifiSession.getSessionId());
            if (channel != null && channel.isActive() && channel.isWritable()) {
                ServerMessage serverMessage = new ServerMessage();
                serverMessage.setAddress(serverInput.getAddress());
                serverMessage.setPort(serverInput.getPort());
                serverMessage.setHeartbeat(serverInput.getHeartbeat());
                channel.writeAndFlush(serverMessage);
            }
        }
    }

    @Override
    public void queryInventory(String boxId) {
        MifiSession mifiSession = sessionCache.getSession(boxId);
        if(mifiSession != null) {
            Channel channel = SessionHolder.get(mifiSession.getSessionId());
            if (channel != null && channel.isActive() && channel.isWritable()) {
                this.queryInventory(channel);
            }
        }
    }

    @Override
    public void reboot(String boxId) {
        MifiSession mifiSession = sessionCache.getSession(boxId);
        if(mifiSession != null) {
            Channel channel = SessionHolder.get(mifiSession.getSessionId());
            if (channel != null && channel.isActive() && channel.isWritable()) {
                RebootMessage rebootMessage = new RebootMessage();
                channel.writeAndFlush(rebootMessage);
            }
        }
    }

    @Override
    public void upgrade(UpgradeInput upgradeInput) {
        MifiSession mifiSession = sessionCache.getSession(upgradeInput.getBoxId());
        if(mifiSession != null) {
            Channel channel = SessionHolder.get(mifiSession.getSessionId());
            if (channel != null && channel.isActive() && channel.isWritable()) {
                UpgradeMessage upgradeMessage = new UpgradeMessage();
                upgradeMessage.setFtpAddress(upgradeInput.getFtpAddress());
                upgradeMessage.setFtpPort(upgradeInput.getFtpPort());
                upgradeMessage.setFileName(upgradeMessage.getFileName());
                upgradeMessage.setUsername(upgradeInput.getUsername());
                upgradeMessage.setPassword(upgradeInput.getPassword());
                channel.writeAndFlush(upgradeMessage);
            }
        }
    }

    @Override
    public void queryIccid(String boxId) {
        MifiSession mifiSession = sessionCache.getSession(boxId);
        if(mifiSession != null) {
            Channel channel = SessionHolder.get(mifiSession.getSessionId());
            if (channel != null && channel.isActive() && channel.isWritable()) {
                IccidMessage iccidMessage = new IccidMessage();
                channel.writeAndFlush(iccidMessage);
            }
        }
    }

    @Override
    public void popUp(String boxId, byte slot) {
        MifiSession mifiSession = sessionCache.getSession(boxId);
        if(mifiSession != null) {
            Channel channel = SessionHolder.get(mifiSession.getSessionId());
            if (channel != null && channel.isActive() && channel.isWritable()) {
                PopUpMessage popUpMessage = new PopUpMessage();
                popUpMessage.setSlot(slot);
                log.info("popUpMessage={}", popUpMessage.toString());
                channel.writeAndFlush(popUpMessage);
            }
        }
    }

    @Override
    public void popUpReport(Channel channel) {
        this.queryInventory(channel);
    }

    @Override
    public void rent(Channel channel, short slot) {
        RentMessage rentMessage = new RentMessage();
        rentMessage.setSlot(slot);
        if(channel.isActive() && channel.isWritable()) {
            log.info("write rent code");
            channel.writeAndFlush(rentMessage);
        } else {
            log.info("write rent code channel is not write");
        }
    }

    @Override
    public void rent(String boxId) {
        MifiSession mifiSession = sessionCache.getSession(boxId);
        if(mifiSession != null) {
            Channel channel = SessionHolder.get(mifiSession.getSessionId());
            if (channel != null && channel.isActive() && channel.isWritable()) {
                DeviceItem deviceItem = deviceItemService.findTop1ByBoxId(boxId);
                if(deviceItem != null) {
                    log.info("begin to rent device channelId={}, slot={}", channel.id().asShortText(), deviceItem.getSlot());
                    this.rent(channel, deviceItem.getSlot());
                }
            }
        }
    }

    @Override
    public void heart(Channel channel) {
        String channelId = channel.id().asShortText();
        MifiSession mifiSession = sessionCache.getSession(channelId);
        if(mifiSession != null) {
            deviceService.updateHearTime(mifiSession.getDeviceId());
            if (channel != null && channel.isActive() && channel.isWritable()) {
                HeartResMessage heartResMessage = new HeartResMessage();
                channel.writeAndFlush(heartResMessage);
            }
        }
    }


    /**
     * 查询机柜版本号
     * @param channel
     */
    private void queryBoxVerion(String boxId, Channel channel) {
        VersionMessage versionMessage = new VersionMessage();
        if(channel.isActive() && channel.isWritable()) {
            log.info("query box version. channelId={}", channel.id().asShortText());
            channel.writeAndFlush(versionMessage);
        }
    }

    /**
     * 查询库存
     * @param channel
     */
    private void queryInventory(Channel channel) {
        InventoryMessage inventoryMessage = new InventoryMessage();
        if(channel.isActive() && channel.isWritable()) {
            channel.writeAndFlush(inventoryMessage);
            log.info("query inventory. channelId={}", channel.id().asShortText());
        }
    }

}
