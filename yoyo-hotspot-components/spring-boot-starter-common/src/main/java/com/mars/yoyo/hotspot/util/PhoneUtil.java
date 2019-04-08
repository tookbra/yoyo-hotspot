package com.mars.yoyo.hotspot.util;


import java.util.regex.Pattern;

import static com.mars.yoyo.hotspot.constant.RegexConst.REGEX_MOBILE_EXACT;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
public class PhoneUtil {

    /**
     * 验证手机号（精确）
     *
     * @param input
     *         待验证文本
     *
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileExact(CharSequence input) {
        return isMatch(REGEX_MOBILE_EXACT, input);
    }

    /**
     * 判断是否匹配正则
     *
     * @param pattern
     *         已经提前编译好的Pattern
     * @param input
     *         要匹配的字符串
     *
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    private static boolean isMatch(Pattern pattern, CharSequence input) {
        return input != null && input.length() > 0 && pattern.matcher(input).matches();
    }
}
