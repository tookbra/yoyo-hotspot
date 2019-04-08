package com.mars.yoyo.hotspot.exception;

import javax.persistence.PersistenceException;

/**
 * @author tookbra
 * @date 2018/5/16
 * @description
 */
public class BizFeignException extends PersistenceException {

    private Integer code;
    public BizFeignException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BizFeignException(String message) {
        super(message);
    }

    public BizFeignException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizFeignException(Throwable cause) {
        super(cause);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
