package com.mars.yoyo.hotspot.exception;

/**
 * @author tookbra
 * @date 2018/5/24
 * @description
 */
public class UserInvalidException extends RuntimeException {

    private static final long serialVersionUID = -951589844138722039L;

    public UserInvalidException(String message) {
        super(message);
    }

    public UserInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserInvalidException(Throwable cause) {
        super(cause);
    }

    protected UserInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
