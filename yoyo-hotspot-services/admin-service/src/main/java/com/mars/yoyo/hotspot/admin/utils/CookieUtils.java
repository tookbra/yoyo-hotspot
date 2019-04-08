package com.mars.yoyo.hotspot.admin.utils;


import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhoubing on 2016/4/11.
 */
public class CookieUtils {

    public static String getCookie(HttpServletRequest request, String cookieKey) {
        if(null != request && StringUtils.isNotBlank(cookieKey)) {
            Cookie[] cookies = request.getCookies();
            if(null != cookies) {
                Cookie[] arr = cookies;
                int len = cookies.length;

                for(int i = 0; i < len; ++i) {
                    Cookie cookie = arr[i];
                    if(cookieKey.equals(cookie.getName())) {
                        return cookie.getValue();
                    }
                }
            }
        }

        return null;
    }

    public static String[] getCookies(HttpServletRequest request, String... cookieKeys) {
        if(null != request && null != cookieKeys && 0 != cookieKeys.length) {
            List cookieKeyList = Arrays.asList(cookieKeys);
            String[] result = new String[cookieKeys.length];
            Cookie[] cookies = request.getCookies();
            if(null != cookies) {
                Cookie[] arr = cookies;
                int len = cookies.length;

                for(int i = 0; i < len; ++i) {
                    Cookie cookie = arr[i];
                    int idx = cookieKeyList.indexOf(cookie.getName());
                    if(-1 != idx) {
                        result[idx] = cookie.getValue();
                    }
                }
            }

            return result;
        } else {
            return null;
        }
    }

    public static void setCookie(HttpServletResponse response, String cookieKey, String cookieValue) {
        setCookie(response, cookieKey, cookieValue, (String)null, (String)null, (Integer)null, (Boolean)null);
    }

    public static void setCookieHttpOnly(HttpServletResponse response, String cookieKey, String cookieValue) {
        setCookie(response, cookieKey, cookieValue, (String)null, (String)null, (Integer)null, Boolean.valueOf(true));
    }

    public static void setCookie(HttpServletResponse response, String cookieKey, String cookieValue, int expiry) {
        setCookie(response, cookieKey, cookieValue, (String)null, (String)null, Integer.valueOf(expiry), (Boolean)null);
    }

    public static void setCookieHttpOnly(HttpServletResponse response, String cookieKey, String cookieValue, int expiry) {
        setCookie(response, cookieKey, cookieValue, (String)null, (String)null, Integer.valueOf(expiry), Boolean.valueOf(true));
    }

    public static void setCookie(HttpServletResponse response, String cookieKey, String cookieValue, String path) {
        setCookie(response, cookieKey, cookieValue, (String)null, path, (Integer)null, (Boolean)null);
    }

    public static void setCookieHttpOnly(HttpServletResponse response, String cookieKey, String cookieValue, String path) {
        setCookie(response, cookieKey, cookieValue, (String)null, path, (Integer)null, Boolean.valueOf(true));
    }

    public static void setCookie(HttpServletResponse response, String cookieKey, String cookieValue, String domain, String path) {
        setCookie(response, cookieKey, cookieValue, domain, path, (Integer)null, (Boolean)null);
    }

    public static void setCookieHttpOnly(HttpServletResponse response, String cookieKey, String cookieValue, String domain, String path) {
        setCookie(response, cookieKey, cookieValue, domain, path, (Integer)null, Boolean.valueOf(true));
    }

    public static void setCookie(HttpServletResponse response, String cookieKey, String cookieValue, String domain, String path, Integer expiry) {
        setCookie(response, cookieKey, cookieValue, domain, path, expiry, (Boolean)null);
    }

    public static void setCookieHttpOnly(HttpServletResponse response, String cookieKey, String cookieValue, String domain, String path, Integer expiry) {
        setCookie(response, cookieKey, cookieValue, domain, path, expiry, Boolean.valueOf(true));
    }

    public static void setCookie(HttpServletResponse response, String cookieKey, String cookieValue, String domain, String path, Integer expiry, Boolean isHttpOnly) {
        if(null != response && StringUtils.isNotBlank(cookieKey)) {
            Cookie cookie = new Cookie(cookieKey, cookieValue);
            if(null != domain) {
                cookie.setDomain(domain);
            }

            if(null != path) {
                cookie.setPath(path);
            }

            if(null != expiry) {
                cookie.setMaxAge(expiry.intValue());
            }

            if(null != isHttpOnly) {
                cookie.setHttpOnly(isHttpOnly.booleanValue());
            }

            response.addCookie(cookie);
        }

    }
}
