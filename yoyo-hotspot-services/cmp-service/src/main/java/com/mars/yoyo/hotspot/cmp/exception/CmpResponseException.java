package com.mars.yoyo.hotspot.cmp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;


/**
 * @author tookbra
 * @date 2018/4/8
 * @description
 */
public class CmpResponseException extends CmpException {

    private static final long serialVersionUID = -936823630757473684L;

    private HttpStatus statusCode;

    private ResponseEntity responseEntity;

    private ClientHttpResponse clientHttpResponse;

    public CmpResponseException(String message) {
        super(message);
    }

    public CmpResponseException(String message, ClientHttpResponse clientHttpResponse, HttpStatus statusCode) {
        super(message);
        this.clientHttpResponse = clientHttpResponse;
        this.statusCode = statusCode;
    }

}
