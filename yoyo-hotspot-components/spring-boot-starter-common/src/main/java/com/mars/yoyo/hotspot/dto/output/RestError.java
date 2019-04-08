package com.mars.yoyo.hotspot.dto.output;

import com.mars.yoyo.hotspot.dto.RestStatus;
import lombok.Data;

/**
 * @author tookbra
 * @date 2018/5/29
 * @description
 */
@Data
public class RestError implements Response {
    private static final long serialVersionUID = -6098133666673665198L;

    /**
     * 状态码
     */
    private int code;
    /**
     * 是否成功
     */
    public boolean success = false;
    /**
     * 错误信息
     */
    private String msg;
    /**
     * 异常信息
     */
    private String exception;

    public RestError() {

    }

    public RestError(RestStatus statusCodes) {
        this(statusCodes, null);
    }

    public RestError(RestStatus statusCodes, String msg) {
        this.code = statusCodes.code();
        this.msg = msg;
    }


}
