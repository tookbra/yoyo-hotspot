package com.mars.yoyo.hotspot.exception;

/**
 * Hibernate Validator 校验异常
 * @author tookbra
 * @date 2018/5/16
 * @description
 */
public class IllegalValidateException extends RuntimeException {
    private static final long serialVersionUID = -2449170868673388537L;

    public IllegalValidateException(String message) {
        super(message);
    }

    public IllegalValidateException(String message, String code) {
        super(message);
    }

    public IllegalValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalValidateException(Throwable cause) {
        super(cause);
    }

    protected IllegalValidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
