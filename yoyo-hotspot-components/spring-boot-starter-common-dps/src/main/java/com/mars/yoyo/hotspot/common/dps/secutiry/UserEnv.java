package com.mars.yoyo.hotspot.common.dps.secutiry;

import lombok.Data;
import lombok.ToString;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tookbra
 * @date 2018/6/4
 * @description
 */
@Data
@ToString
public class UserEnv implements Writable {

    private Integer userId;

    private String requestIp;

    private String lang;

    private String clientType;

    private String channelId;

    @Override
    public void read(HttpServletRequest request) {
        userId = Integer.parseInt(request.getHeader("userId"));
        requestIp = request.getHeader("requestIp");
        lang = request.getHeader("lang");
        clientType = request.getHeader("clientType");
        channelId = request.getHeader("channelId");
    }
}
