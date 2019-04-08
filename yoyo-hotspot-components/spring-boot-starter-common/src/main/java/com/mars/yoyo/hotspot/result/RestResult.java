package com.mars.yoyo.hotspot.result;

import com.mars.yoyo.hotspot.dto.RestStatus;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * rest返回对象
 * @author tookbra
 * @date 2018/4/18
 * @description
 */
@Data
@ToString
public class RestResult<T> implements Serializable {

    /**
     * 状态码
     */
    public int code = 1;
    /**
     * 是否成功
     */
    public boolean success = true;
    /**
     * 信息
     */
    public String msg = "";
    /**
     * 内容
     */
    public T data;

    public RestResult() {

    }

    public RestResult(T data) {
        this.data = data;
    }

    public RestResult(String msg) {
        this.msg = msg;
        this.code = 1000;
    }

    public RestResult(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public RestResult(RestStatus statusCodes, String msg) {
        this.code = statusCodes.code();
        this.msg = msg;
        this.success = false;
    }

    public static <T> RestResult<T> success(T data) {
        return new RestResult(data);
    }

    public static <T> RestResult<T> success(String msg) {
        return new RestResult(msg, 1);
    }

    public static <T> RestResult<T> error(String msg) {
        RestResult restResult = new RestResult(msg);
        restResult.setSuccess(false);
        return restResult;
    }

    public static <T> RestResult<T> error(String msg, int code) {
        RestResult restResult = new RestResult(msg, code);
        restResult.setSuccess(false);
        return restResult;
    }
}
