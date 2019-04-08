package com.mars.yoyo.hotspot.admin.resutls.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 结果
 *
 * @author admin
 * @Date 2018/8/31 10:54
 */
@Data
public class Result implements Serializable {

    public static final long serialVersionUID = 1L;

    public Integer retCode;

    public String retMsg;

    public Object data;

    public Result() {

    }
}
