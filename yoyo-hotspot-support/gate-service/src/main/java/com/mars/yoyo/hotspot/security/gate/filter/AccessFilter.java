package com.mars.yoyo.hotspot.security.gate.filter;

import com.mars.yoyo.hotspot.util.DateUtil;
import com.mars.yoyo.hotspot.util.StringUtil;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tookbra
 * @date 2018/5/25
 * @description
 */
@Slf4j
public class AccessFilter extends AbstractFilter {



    @Value("${zuul.prefix}")
    private String zuulPrefix;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterOrder.ACCESS_FILTER;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        final String requestUri = request.getRequestURI().substring(zuulPrefix.length());
        if (isStartWith(requestUri)) {
            log.info("requestUri={} 默认通过，不校验", requestUri);
            setCtxSuccess(ctx);
            return null;
        }

        return null;
    }





//    /**
//     * 返回session中的用户信息
//     *
//     * @param request
//     * @param ctx
//     * @return
//     */
//    private IJWTInfo getJWTUser(HttpServletRequest request, RequestContext ctx) throws Exception {
//        String authToken = request.getHeader("Authorization");
//        if (StringUtils.isBlank(authToken)) {
//            authToken = request.getParameter("token");
//        }
//        ctx.addZuulRequestHeader("Authorization", authToken);
//        BaseContextHandler.setToken(authToken);
//        return userAuthUtil.getInfoFromToken(authToken);
//    }
}
