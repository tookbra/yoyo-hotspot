package com.mars.yoyo.hotspot.common.dps.advice;

import com.mars.yoyo.hotspot.dto.output.RestError;
import com.mars.yoyo.hotspot.exception.BizFeignException;
import com.mars.yoyo.hotspot.exception.IllegalValidateException;
import com.mars.yoyo.hotspot.exception.MicroException;
import com.mars.yoyo.hotspot.exception.UserInvalidException;
import com.mars.yoyo.hotspot.result.RestResult;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.RequestContext;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author tookbra
 * @date 2018/5/30
 * @description
 */
@ControllerAdvice
public class RestAdvice extends ResponseEntityExceptionHandler {

    /**
     * <strong>Request域取出对应错误信息</strong>, 封装成实体ErrorEntity后转换成JSON输出
     *
     * @param e  {@code IllegalValidateException}异常
     * @param request HttpServletRequest
     * @return {@link RestError}
     */
    @ResponseBody
    @ExceptionHandler(IllegalValidateException.class)
    public Object illegalValidateException(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        logger.error(e.getMessage());
        // 取出存储在Request域中的Map
        return request.getAttribute(e.getMessage());
    }

    /**
     * 微服务异常
     * @param e   {@code MicroException}异常
     * @param request HttpServletRequest
     * @return {@link RestResult}
     */
    @ResponseBody
    @ExceptionHandler(MicroException.class)
    public RestResult microException(MicroException e, HttpServletRequest request) {
        e.printStackTrace();
        logger.error(e.getMessage());
        RestResult restResult = RestResult.error(e.getMessage(), e.getErrorCode());
        return restResult;
    }

    /**
     * 业务异常
     * @param e
     * @param request
     * @return
     */
    @ResponseBody
    @ExceptionHandler(BizFeignException.class)
    public RestError bizFeignException(BizFeignException e, HttpServletRequest request) {
        e.printStackTrace();
        logger.error("biz error=" + e.getMessage());
        RestError restError = new RestError();
        restError.setException(BizFeignException.class.getName());
        if(e.getCode() != null) {
            restError.setCode(e.getCode());
        } else {
            restError.setCode(11000);
        }
        String message = getLocaleMessage(e.getMessage());
        if(StringUtils.isEmpty(message)) {
            restError.setMsg(e.getMessage());
        } else {
            restError.setMsg(getLocaleMessage(e.getMessage()));
        }
        return restError;
    }

    /**
     * 授权异常
     * @param e
     * @param request
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserInvalidException.class)
    public RestError userInvalidException(UserInvalidException e, HttpServletRequest request) {
        e.printStackTrace();
        logger.error("user invalid error=" + e.getMessage());
        RestError restError = new RestError();
        restError.setException(BizFeignException.class.getName());
        restError.setCode(15000);
        String message = getLocaleMessage(e.getMessage());
        if(StringUtils.isEmpty(message)) {
            restError.setMsg(e.getMessage());
        } else {
            restError.setMsg(getLocaleMessage(e.getMessage()));
        }
        return restError;
    }

    /**
     *
     * @param throwable
     * @param request
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    RestResult handleException(Throwable throwable, HttpServletRequest request) {
        throwable.printStackTrace();
        logger.error("biz error=" + throwable.getMessage());
        if (throwable instanceof HystrixBadRequestException && throwable.getCause() instanceof MicroException) {
            return microException((MicroException) throwable.getCause(), request);
        } else {
            return microException(new MicroException("未知错误", throwable), request);
        }
    }

    public static String getLocaleMessage(String key){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            RequestContext requestContext = new RequestContext(request);
            return requestContext.getMessage(key);
        } catch (Exception e) {
            return "";
        }
    }

}
