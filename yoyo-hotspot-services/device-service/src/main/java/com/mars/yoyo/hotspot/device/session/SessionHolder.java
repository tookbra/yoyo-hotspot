package com.mars.yoyo.hotspot.device.session;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tookbra
 * @date 2018/6/12
 * @description
 */
public class SessionHolder {

    private static Map<String, Channel> MAP = new ConcurrentHashMap<>(16);

    public static void put(String id, Channel channel) {
        MAP.put(id, channel);
    }

    public static Channel get(String channelId) {
        return MAP.get(channelId);
    }

    public static Map<String, Channel> getMap() {
        return MAP;
    }

    public static void remove(Channel channel) {
        MAP.entrySet().stream().filter(entry -> entry.getValue() == channel).forEach(entry -> MAP.remove(entry.getKey()));
    }
}
