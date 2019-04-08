package com.mars.yoyo.hotspot.common.dps.secutiry;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tookbra
 * @date 2018/6/3
 * @description
 */
public class UserEnvArgumentResolver<T> implements HandlerMethodArgumentResolver {

    private Class<T> clazz;

    public UserEnvArgumentResolver() {

    }

    public UserEnvArgumentResolver(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class type = parameter.getParameterType();
        return type.equals(this.clazz);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        Writable object = (Writable)clazz.newInstance();
        object.read(request);
        return object;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }
}
