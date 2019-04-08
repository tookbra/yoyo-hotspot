package com.mars.yoyo.hotspot.constant;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
public class RegexConst {

    /**
     * 正则：手机号（精准）
     * 13 14 15 16 17 18 19 开头都可以
     */
    public static final Pattern REGEX_MOBILE_EXACT = compile("^1[3|4|5|6|7|8|9][0-9]\\d{8}$");
}
