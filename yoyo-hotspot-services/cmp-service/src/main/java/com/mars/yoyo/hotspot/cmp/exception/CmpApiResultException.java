package com.mars.yoyo.hotspot.cmp.exception;

import com.mars.yoyo.hotspot.cmp.config.rest.CmpRestCode;
import org.springframework.util.StringUtils;

/**
 * @author tookbra
 * @date 2018/4/8
 * @description
 */
public class CmpApiResultException extends CmpException {


    private static final long serialVersionUID = 3486996710280375310L;

    public static final String API_RESULT_SUCCESS = "\"code\":0,";
    public static final String API_RESULT_STATUS = "\"code\":";

    /**
     * api错误状态码
     */
    private int code;
    /**
     * 状态枚举
     */
    private CmpRestCode cmpRestCode;


    public CmpApiResultException(int code, String message) {
        super(message);
        this.cmpRestCode = CmpRestCode.of(code);
    }

    public CmpApiResultException(String message) {
        super(message);
        parseCode(message);
        this.cmpRestCode = CmpRestCode.of(code);
    }

    private void parseCode(String message) {
        int index = message.indexOf(API_RESULT_STATUS);
        this.code = Integer.parseInt(message.substring(index));
    }

    public static boolean hasException(String result) {
        if (StringUtils.hasLength(result)) {
            // 包含错误码但是错误码不是0
            if (!result.contains(API_RESULT_SUCCESS)) {
                return true;
            }
        }
        return false;
    }
}
