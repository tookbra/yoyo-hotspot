package com.mars.yoyo.hotspot.exception;

/**
 * 微服务异常
 * @author tookbra
 * @date 2018/5/30
 * @description
 */
public class MicroException extends RuntimeException  {

    private int errorCode;

    public MicroException(String message) {
        super(message);
    }

    public MicroException(String message, Throwable cause) {
        super(message, cause);
    }

    public MicroException(Throwable cause) {
        super(cause);
    }

    public MicroException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    protected MicroException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
