package com.mars.yoyo.hotspot.cmp.exception;

/**
 * @author tookbra
 * @date 2018/4/8
 * @description
 */
public class CmpException extends RuntimeException {

    private static final long serialVersionUID = -1135296508515619035L;

    public CmpException(String message) {
        super(message);
    }

    public CmpException(String message, Throwable cause) {
        super(message, cause);
    }

    public CmpException(Throwable cause) {
        super(cause);
    }
}
