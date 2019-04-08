//package com.mars.yoyo.hotspot.common.dps.interceptor;
//
//import lombok.extern.slf4j.Slf4j;
//import org.joda.time.DateTime;
//import org.springframework.core.MethodParameter;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//import org.springframework.web.util.UrlPathHelper;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Date;
//
///**
// * @author tookbra
// * @date 2018/6/11
// * @description
// */
//@Slf4j
//public class LogHandlerInterceptor extends HandlerInterceptorAdapter {
//
//    /**
//     * 请求进入前
//     * @param request
//     * @param response
//     * @param handler
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // 如果不符合拦截条件
//        if (!isHandled(handler)) {
//            return true;
//        }
//        String requsetUrl = request.getRequestURL().toString();
//        Date date = DateTime.now().toDate();
//        UrlPathHelper urlPathHelper = new UrlPathHelper();
//        String path = urlPathHelper.getLookupPathForRequest(request);
//        // 请求处理开始时间
//        long startTime = System.currentTimeMillis();
//        request.setAttribute("startTime", startTime);
//        log.info("http request log begin requestUrl={}, date={}, path={}, startTime={}", requsetUrl, date, path, startTime);
//        return super.preHandle(request, response, handler);
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        // 如果不符合拦截条件
//        if (!isHandled(handler)) {
//            return;
//        }
//        // 请求开始开始时间
//        long start = (long) request.getAttribute("startTime");
//        // 请求结束时间
//        long end = System.currentTimeMillis();
//        // 请求花费时间
//        int time = (int) (end - start);
//        // 记录请求结束时间
//        Date date = DateTime.now().toDate();
//
//        log.info("http request log begin requestUrl={}, date={}, path={}, startTime={}", requsetUrl, date, path, startTime);
//        super.afterCompletion(request, response, handler, ex);
//    }
//
//    /**
//     * 判断是否要拦截
//     * @param handler 控制器
//     * @return 是否要拦截，true：拦截 / false：不拦截
//     */
//    private Boolean isHandled(Object handler) {
//
//        // 增加支持spring普通方式访问
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//
//        MethodParameter parameter = handlerMethod.getMethodParameters()[0];
//
//        // 获取RestController注解
//        // 从该方法所属类中取得注解
//        RestController annotation = parameter.getDeclaringClass().getAnnotation(RestController.class);
//        if (annotation != null) {
//            return true;
//        }
//        // 从该方法中取得注解
//        annotation = parameter.getMethodAnnotation(RestController.class);
//        if (annotation != null) {
//            return true;
//        }
//        // 从该方法的参数中取得注解
//        annotation = parameter.getParameterAnnotation(RestController.class);
//        if (annotation != null) {
//            return true;
//        }
//
//        return false;
//    }
//}
