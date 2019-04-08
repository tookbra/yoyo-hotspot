package com.mars.yoyo.hotspot.security.gate.filter;


import com.mars.yoyo.hotspot.util.WebUtil;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tookbra
 * @date 2018/5/25
 * @description
 */
@Slf4j
public class IpFilter extends AbstractFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterOrder.IP_FILTER;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("{} request to {}", request.getMethod(), request.getRequestURL().toString());

        String clientIP = WebUtil.getClientIP(request);
        log.info("clientIp {}", clientIP);
        ctx.addZuulRequestHeader("requestIp", clientIP);
        String lang = request.getHeader("lang");
        if(StringUtils.isBlank(lang)) {
            ctx.addZuulRequestHeader("lang", "zh_CN");
        } else {
            ctx.addZuulRequestHeader("lang", request.getHeader("lang"));
        }
        String channelId = request.getHeader("channelId");
        if(StringUtils.isNotBlank(channelId)) {
            ctx.addZuulRequestHeader("channelId", channelId);
        }
        setCtxSuccess(ctx);
        return null;
    }
}
