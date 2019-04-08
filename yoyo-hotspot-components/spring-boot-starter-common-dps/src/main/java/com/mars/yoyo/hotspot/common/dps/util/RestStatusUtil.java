package com.mars.yoyo.hotspot.common.dps.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mars.yoyo.hotspot.dto.output.RestError;
import com.mars.yoyo.hotspot.exception.AuthorException;
import com.mars.yoyo.hotspot.exception.MicroException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

/**
 * @author tookbra
 * @date 2018/5/30
 * @description
 */
@Slf4j
public class RestStatusUtil {

    public static RestError decode(String body) {
        try {
            if (StringUtils.isBlank(body)) {
                return null;
            }
            Object pBody = tryParseObject(body);
            if (pBody instanceof String) {
                return null;
            }
            RestError restResult = JSON.parseObject(body, RestError.class);
            if(!restResult.success && StringUtils.isNotBlank(restResult.getException())) {
                return restResult;
            }
            return null;
        } catch (Exception e) {
            log.warn("解析Feign【body:{} 】事件", body, e);
        }
        return null;
    }

    public static MicroException decode(HttpStatus status, String body) {
        try {
            if (status == HttpStatus.INTERNAL_SERVER_ERROR && StringUtils.isNotBlank(body)) {
                Object pBody = tryParseObject(body);
                if (pBody instanceof String) {
                    return new MicroException(pBody.toString());
                }
                JSONObject jsonObj = JSON.parseObject(body);
                String message = jsonObj.get("message").toString();
                String code = jsonObj.get("code").toString();
                return new MicroException(message, Integer.parseInt(code));
            } else if (status == HttpStatus.UNAUTHORIZED) {
                return new AuthorException("无权访问");
            } else {
                return new MicroException("服务异常");
            }
        } catch (Exception e) {
            log.warn("解析异常【body:{} ,status:{}】事件", status, body, e);
        }
        return new MicroException("服务异常");
    }

    private static Object tryParseObject(String body) {
        try {
            return JSON.parseObject(body);
        } catch (Exception e) {
            return body;
        }

    }
}
