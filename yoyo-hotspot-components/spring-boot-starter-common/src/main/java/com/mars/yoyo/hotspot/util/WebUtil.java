package com.mars.yoyo.hotspot.util;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author tookbra
 * @date 2018/5/25
 * @description
 */
@Slf4j
public class WebUtil {

    private static final String EMPTY = "";

    /**
     * 获取客户端真实ip地址。
     *
     * @param request HttpServletRequest
     * @return ip地址
     */
    public static String getClientIP(HttpServletRequest request) {
        String UNKNOWN = "unknown";
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                }
            }

            return ip;
        }

        String[] ips = ip.split(",");
        if (ips.length > 1) {
            return ips[0];
        }

        if (Objects.equals("0:0:0:0:0:0:0:1", ip)) {
            return "127.0.0.1";
        }

        return ip;
    }
}
