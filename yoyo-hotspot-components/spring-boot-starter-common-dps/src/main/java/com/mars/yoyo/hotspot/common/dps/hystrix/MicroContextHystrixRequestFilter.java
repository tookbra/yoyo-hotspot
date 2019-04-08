package com.mars.yoyo.hotspot.common.dps.hystrix;


import javax.servlet.*;
import java.io.IOException;

/**
 * @author tookbra
 * @date 2018/5/30
 * @description
 */
public class MicroContextHystrixRequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HystrixCredentialsContext.getInstance().set(MicroContext.getInterface());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
