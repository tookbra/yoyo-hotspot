package com.mars.yoyo.hotspot.device.task;

import com.mars.yoyo.hotspot.device.cache.SessionCache;
import com.mars.yoyo.hotspot.device.packet.protocol.InventoryMessage;
import com.mars.yoyo.hotspot.device.service.DeviceService;
import com.mars.yoyo.hotspot.device.session.MifiSession;
import com.mars.yoyo.hotspot.device.session.SessionHolder;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author tookbra
 * @date 2018/7/31
 * @description
 */
@Slf4j
@Component
public class DeviceTask {

    @Autowired
    DeviceService deviceService;

    @Autowired
    SessionCache sessionCache;

    @Scheduled(cron="0 0/30 * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void loopDeviceItem() {
        Map<String, Channel> map = SessionHolder.getMap();
        map.forEach((k, v) -> {
            MifiSession mifiSession = sessionCache.getSession(k);
            if(mifiSession != null ) {
                if (v != null && v.isActive() && v.isWritable()) {
                    log.info("job query inventory. channelId={}", k);
                    v.writeAndFlush(new InventoryMessage());
                }
            }
        });
    }
}
