package com.mars.yoyo.hotspot.cmp.config.rest;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tookbra
 * @date 2018/4/9
 * @description
 */
@Data
public class CmpResult<T> implements Serializable {

    private static final long serialVersionUID = 1945315606871423108L;

    private int code;

    private String msg;

    private T data;

}
