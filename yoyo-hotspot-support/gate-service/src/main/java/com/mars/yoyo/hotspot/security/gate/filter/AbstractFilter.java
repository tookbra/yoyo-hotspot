package com.mars.yoyo.hotspot.security.gate.filter;

import com.mars.yoyo.hotspot.result.RestResult;
import com.mars.yoyo.hotspot.util.JacksonUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;

/**
 * @author tookbra
 * @date 2018/5/25
 * @description
 */
public abstract class AbstractFilter extends ZuulFilter {

    protected static final String SUCCESS = "SUCCESS";

    protected static final String FAILED = "FAILED";

    protected static final int HTTP_SUCCESS_CODE = 200;

    @Value("${gate.ignore.startWith}")
    private String startWith;

    @Value("${zuul.prefix}")
    private String zuulPrefix;

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = getCurCxt();
        Object object = ctx.get(SUCCESS);
        if(null == object || object.equals(SUCCESS)) {
            return true;
        }
        return false;
    }

    /**
     * 当前上下文
     *
     * @return
     */
    protected RequestContext getCurCxt() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        return currentContext;
    }

    protected void setCtxSuccess(RequestContext ctx) {
        ctx.setSendZuulResponse(true);
        ctx.setResponseStatusCode(HTTP_SUCCESS_CODE);
        ctx.set(SUCCESS, SUCCESS);
    }

    protected void setCtxFailed(RequestContext ctx, String msg, int code) {
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(code);
        ctx.set(SUCCESS, FAILED);

        ctx.setResponseBody(JacksonUtil.stringify(RestResult.error(msg)));
        HttpServletResponse response = ctx.getResponse();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        ctx.setResponse(ctx.getResponse());
    }

    /**
     * URI是否以什么打头
     *
     * @param requestUri
     * @return
     */
    protected boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : startWith.split(",")) {
            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return flag;
    }
}
