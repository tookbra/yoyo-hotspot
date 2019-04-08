package com.mars.yoyo.hotspot.exception;

/**
 * @author tookbra
 * @date 2018/5/30
 * @description
 */
public class AuthorException extends MicroException {

    public AuthorException(String message) {
        super(message);
    }

    public AuthorException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorException(Throwable cause) {
        super(cause);
    }

    protected AuthorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
