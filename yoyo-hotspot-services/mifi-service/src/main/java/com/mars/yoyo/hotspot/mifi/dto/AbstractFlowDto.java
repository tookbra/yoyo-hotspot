package com.mars.yoyo.hotspot.mifi.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author tookbra
 * @date 2018/9/10
 * @description
 */
@Data
@ToString
public class AbstractFlowDto<T> implements Serializable {

    private static final long serialVersionUID = -209949353882808055L;
    /**
     *
     */
    private String code;

    private String error;

    private T data;

    public boolean isSuccess() {
        return this.code.endsWith("200");
    }
}
