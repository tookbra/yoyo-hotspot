package com.mars.yoyo.hotspot.exception;

/**
 * @author tookbra
 * @date 2018/5/16
 * @description
 */
public class DateException extends RuntimeException {
    private static final long serialVersionUID = -1457151746381831191L;

    public DateException(String message) {
        super(message);
    }

    public DateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateException(Throwable cause) {
        super(cause);
    }

    protected DateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
