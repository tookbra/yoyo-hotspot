package com.mars.yoyo.hotspot.admin.session;

import com.alibaba.fastjson.JSON;
import com.mars.yoyo.hotspot.admin.common.Constant;
import com.mars.yoyo.hotspot.admin.config.RedisHelper;
import com.mars.yoyo.hotspot.admin.utils.CookieUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 用户处理用户登录信息
 * Created by zhoubing on 2016/4/12.
 */
public class SessionHandler<T> {

    public static final String SESSION_CACHE_KEY_PREFIX = "yoyo.session.";

    // 会话缓存时间毫秒数
    public static final long SESSION_EXPIRE_MILLIS = 24 * 60 * 60 * 1000L;

    // 授权cookie前缀
    public static final String AUTHORIZATION_COOKIE_VALUE_PREFIX = "Bearer ";

    // 删除授权cookie
    public static final String DELETE_AUTHORIZATION_COOKIE_VALUE = "Bearer deleteMe";

    /**
     * 从请求中获取accessToken
     * @param request
     * @return
     */
    public static String getAccessToken(HttpServletRequest request) {
        String authorization = CookieUtils.getCookie(request, Constant.AUTHORIZATION);
        if (StringUtils.isNotBlank(authorization)) {
            return authorization.substring(AUTHORIZATION_COOKIE_VALUE_PREFIX.length());
        }
        authorization = request.getHeader(Constant.AUTHORIZATION);
        if (StringUtils.isNotBlank(authorization)) {
            return authorization.substring(AUTHORIZATION_COOKIE_VALUE_PREFIX.length());
        }
        return request.getParameter(Constant.ACCESS_TOKEN);
    }

    /**
     * 组装accessToken的cookie值
     * @param accessToken
     * @return
     */
    public static String processAccessTokenCookie(String accessToken) {
        return new StringBuilder(AUTHORIZATION_COOKIE_VALUE_PREFIX).append(accessToken).toString();
    }

    /**
     * 缓存token对应的用户信息
     * @param token
     * @param SessionIdentity
     */
    public void setSessionIdentity(String token, T SessionIdentity) {
        setSessionIdentity(token, SessionIdentity, SESSION_EXPIRE_MILLIS);
    }

    /**
     * 缓存token对应的用户信息
     * @param token
     * @param SessionIdentity
     * @param expire 毫秒数
     */
    public void setSessionIdentity(String token, T SessionIdentity ,Long expire) {
        String key = processSessionIdentityCacheKey(token);
        String data = JSON.toJSONString(SessionIdentity);
        RedisHelper.set(key, data, expire);
    }

    public static String processSessionIdentityCacheKey(String accessToken) {
        return new StringBuilder(SESSION_CACHE_KEY_PREFIX).append(accessToken).toString();
    }

    /**
     * 根据请求获取用户会话
     * @param token
     * @return
     */
    public static String getSessionIdentity(String token) {
        String key = processSessionIdentityCacheKey(token);
        Object data = RedisHelper.get(key);
        if (data != null) {
            return JSON.toJSONString(data);
        }
        return null;
    }

    /**
     * 设置用户会话信息
     * @param sessionIdentity
     * @return 返回token
     */
    public String setSessionIdentity(T sessionIdentity) {
        return setSessionIdentity(sessionIdentity,SESSION_EXPIRE_MILLIS);
    }

    /**
     * 设置一个过期时间
     * @param sessionIdentity
     * @param expire 毫秒数
     * @return
     */
    public String setSessionIdentity(T sessionIdentity, Long expire) {
        if (sessionIdentity != null) {
            String token = UUID.randomUUID().toString().replace("-", "");
            setSessionIdentity(token, sessionIdentity, expire);
            return token;
        }
        return null;
    }

    /**
     * 删除用户会话信息
     * @param accessToken
     * @return
     */
    public static boolean removeSessionIdentity(String accessToken) {
        if (StringUtils.isNotBlank(accessToken)) {
            String key = processSessionIdentityCacheKey(accessToken);
            RedisHelper.del(key);
            return true;
        }
        return false;
    }

    /**
     * 登录
     * @param sessionIdentity
     * @param response
     */
    public String login(T sessionIdentity, HttpServletResponse response) {
        return login(sessionIdentity,response,SESSION_EXPIRE_MILLIS);
    }

    /**
     * 设置用户的accesToken的过期时间
     * @param sessionIdentity
     * @param response
     * @param timeout  毫秒数
     * @return
     */
    public String login(T sessionIdentity, HttpServletResponse response, Long timeout) {
        if (sessionIdentity != null) {
            String accessToken = setSessionIdentity(sessionIdentity, timeout);
            String cookie = processAccessTokenCookie(accessToken);
            int expiry = ((Long) (timeout / 1000)).intValue();
            CookieUtils.setCookieHttpOnly(response, Constant.AUTHORIZATION, cookie, null, "/", expiry);
            return accessToken;
        }
        return "";
    }

    /**
     * 设置用户的accesToken的过期时间
     * @param accessToken
     * @param response
     * @param timeout  毫秒数
     * @return
     */
    public void login(String accessToken, HttpServletResponse response, Long timeout) {
        String cookie = processAccessTokenCookie(accessToken);
        int expiry = ((Long) (timeout / 1000)).intValue();
        CookieUtils.setCookieHttpOnly(response, Constant.AUTHORIZATION, cookie, null, "/", expiry);
    }

    /**
     * 登出
     * @param response
     */
    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = getAccessToken(request);
        if (StringUtils.isNotBlank(accessToken)) {
            removeSessionIdentity(accessToken);
            CookieUtils.setCookie(response, Constant.AUTHORIZATION, DELETE_AUTHORIZATION_COOKIE_VALUE, null, "/", 0);
        }
    }


}
