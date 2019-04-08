package com.mars.yoyo.hotspot.common.dps.interceptor;

import com.mars.yoyo.hotspot.common.dps.annotation.Security;
import com.mars.yoyo.hotspot.exception.UserInvalidException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tookbra
 * @date 2018/6/4
 * @description
 */
public abstract class SecurityHandlerInterceptor extends HandlerInterceptorAdapter {

    Map<String, String> noSecurityUris = new ConcurrentHashMap();
    Map<String, String> securityUris = new ConcurrentHashMap();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ((handler instanceof HandlerMethod)) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            boolean isSecurity = this.requireSecurity(handlerMethod, request.getRequestURI());
            if(!isSecurity) {
                return true;
            }
            String token = request.getHeader("userId");
            checkAuthorization(token);
            return true;
        }
        return super.preHandle(request, response, handler);
    }

    protected boolean requireSecurity(HandlerMethod handlerMethod, String uri) {
        //反射有耗性能，第一次判断路由是否需要认证放入内存，后面同样的路由直接从内存那就行
        if (this.noSecurityUris.containsKey(uri)) {
            return false;
        }
        if (this.securityUris.containsKey(uri)) {
            return true;
        }
        Security methodSecurity = (Security)AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Security.class);
        Security classSecurity = (Security)AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Security.class);
        if (methodSecurity != null) {
            this.securityUris.put(uri, "");
            return true;
        }
        if (classSecurity != null) {
            this.securityUris.put(uri, "");
            return true;
        }
        this.noSecurityUris.put(uri, "");
        return false;
    }

    protected void checkAuthorization(String token) throws Exception {
        if(StringUtils.isEmpty(token)) {
            throw new UserInvalidException("not login");
        }
    }
}
