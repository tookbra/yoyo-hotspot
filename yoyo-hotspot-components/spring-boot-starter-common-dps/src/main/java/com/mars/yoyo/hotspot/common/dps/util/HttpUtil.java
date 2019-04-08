package com.mars.yoyo.hotspot.common.dps.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author tookbra
 * @date 2018/5/30
 * @description
 */
public class HttpUtil {

    /**
     * 获取当前请求HttpServletRequest
     * @return null | HttpServletRequest 实例
     */
    public static HttpServletRequest getRequest(){
        return Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes::getRequest)
                .orElse(null);
    }

    /**
     * 获取当前请求HttpServletResponse
     * @return null | HttpServletResponse 实例
     */
    public static HttpServletResponse getResponse(){
        return Optional.ofNullable((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes::getResponse)
                .orElse(null);
    }

}
